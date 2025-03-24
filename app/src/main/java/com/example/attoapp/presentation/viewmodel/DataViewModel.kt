package com.example.attoapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attoapp.data.Brands
import com.example.attoapp.data.DataRepository
import com.example.attoapp.data.FavoriteUpdate
import com.example.attoapp.data.MergedProductsBrands
import com.example.attoapp.data.MergedPromotions
import com.example.attoapp.data.Products
import com.example.attoapp.data.Promotion
import com.example.attoapp.data.Promotions
import com.example.attoapp.data.RetrofitInstance
import com.example.attoapp.data.Reviews
import com.example.attoapp.data.User
import com.example.attoapp.data.UserPreferences
import com.example.attoapp.domain.GetUseCase
import com.example.attoapp.domain.makeItFavorite
import com.example.attoapp.domain.mergeBrandsAndProducts
import com.example.attoapp.domain.mergeFavorite
import com.example.attoapp.domain.mergePromotions
import com.example.attoapp.domain.photoForReview
import com.example.attoapp.domain.stripe.PaymentRequest
import com.example.attoapp.domain.stripe.StripeInstance
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.payments.paymentlauncher.PaymentLauncher
import com.stripe.android.payments.paymentlauncher.PaymentResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataViewModel @Inject constructor(
    private val repository: DataRepository ,
    private val userPreferences: UserPreferences ,
    private val getUseCase: GetUseCase ,
) : ViewModel() {
    private val _productsWithFavorites = MutableStateFlow<List<MergedProductsBrands>>(emptyList())
    val productsWithFavorites : StateFlow<List<MergedProductsBrands>> get() = _productsWithFavorites


    private val _currentBottomNavIcon = MutableStateFlow<Int?>(0)
    val currentBottomNavIcon : StateFlow<Int?> = _currentBottomNavIcon

    private val _allSelected = MutableStateFlow<Boolean?>(null)
    val allSelected : StateFlow<Boolean?> = _allSelected

    private val _sumOfItems = MutableStateFlow<Double?>(null)
    val sumOfItems : StateFlow<Double?> = _sumOfItems

    private val _countOfItems = MutableStateFlow<Int?>(null)
    val countOfItems : StateFlow<Int?> = _countOfItems

    private val _currentItem = MutableStateFlow<Int?>(null)
    val currentItem : StateFlow<Int?> = _currentItem

    private val _currentBrand = MutableStateFlow<Int?>(null)
    val currentBrand : StateFlow<Int?> = _currentBrand

    private val _brands = MutableStateFlow<List<Brands>>(emptyList())
    val brands : StateFlow<List<Brands>> get() = _brands

    private val _products = MutableStateFlow<List<Products>>(emptyList())
    val products : StateFlow<List<Products>> get() = _products

    private val _promotion = MutableStateFlow<List<Promotion>>(emptyList())
    val promotion : StateFlow<List<Promotion>> = _promotion

    private val _promotions = MutableStateFlow<List<Promotions>>(emptyList())
    val promotions : StateFlow<List<Promotions>> = _promotions

    private val _reviews = MutableStateFlow<List<Reviews>>(emptyList())
    val reviews : StateFlow<List<Reviews>> = _reviews

    private val _cartSummary = MutableStateFlow(CartSummary(0 ,0.0))
    val cartSummary : StateFlow<CartSummary> = _cartSummary

    data class CartSummary(val count : Int ,val sum : Double)

    fun updateCartSummary() {
        val selectedItems = _userInfo.value?.cartInfo?.filter { it.isSelected } ?: emptyList()
        _cartSummary.value = CartSummary(
            selectedItems.sumOf { it.count } ,
            selectedItems.sumOf { (it.product.price ?: 0.0) * it.count }
        )
    }


    private val _userId = MutableStateFlow<Int?>(null)
    val userId : StateFlow<Int?> = _userId
    private val _userInfo = MutableStateFlow<User?>(null)
    val userInfo : StateFlow<User?> = _userInfo
    private val _productsWithBrands = MutableStateFlow<List<MergedProductsBrands>>(emptyList())
    val productsWithBrands : StateFlow<List<MergedProductsBrands>> =
        combineTransform(
            _reviews ,
            _brands ,
            _products ,
            _userInfo
        ) { reviews ,brands ,products ,user ->
            if (products.isEmpty() || brands.isEmpty()) return@combineTransform
            val favoriteIds = user?.favorite_productsInfo?.map { it.id }?.toSet() ?: emptySet()
            emit(
                mergeBrandsAndProducts(
                    reviews ,
                    brands ,
                    products
                ).map { it.copy(isFavorite = it.id in favoriteIds) })
        }.stateIn(viewModelScope ,SharingStarted.WhileSubscribed(5000) ,emptyList())

    val promotionsWithProducts : StateFlow<List<MergedPromotions>> =
        combine(_promotions ,_promotion ,productsWithBrands) { promotions ,promotion ,products ->
            mergePromotions(promotions ,promotion ,products)
        }.stateIn(viewModelScope ,SharingStarted.Lazily ,emptyList())

    fun fetchUserInfo() {
        viewModelScope.launch {

            // Создаем бесконечный цикл для обновления данных
            while (true) {
                fetchBrands()
                fetchProducts()
                fetchPromotion()
                fetchPromotions()
                fetchReviews()
                val userIdValue = userId.value
                if (userIdValue != null) {
                    val user = getUseCase.executeUserInfo(userIdValue)

                    productsWithBrands.collect { products ->
                        if (products.isNotEmpty() && user != null) {
                            val brandsValue = brands.value
                            if (brandsValue != null) {
                                val updatedUser = mergeFavorite(user ,brandsValue ,products)
                                _userInfo.value = updatedUser

                                updateAllSelectedState()
                                _productsWithFavorites.value = makeItFavorite(products ,user)
                            }
                        }
                    }

                }

                // Задержка на 5 минут (300000 миллисекунд)
                delay(100000)
            }
        }
    }


    fun updateCurrentNavIcon(index : Int) {
        _currentBottomNavIcon.value = index
    }

    fun itemsCountAndPrice() {
        val userInfo = _userInfo.value
        val cartItems = userInfo?.cartInfo.orEmpty()

        val selectedItems = cartItems!!.filter { it.isSelected == true }
        _countOfItems.value = selectedItems.sumOf { it.count }
        val sum = selectedItems.sumOf { (it.product.price ?: 0.0) * it.count }
        _sumOfItems.value = String.format("%.2f" ,sum).toDouble()
    }

    init {
        viewModelScope.launch {
            launch { fetchBrands() }
            launch { fetchProducts() }
            launch { fetchPromotion() }
            launch { fetchPromotions() }
            launch { fetchReviews() }
            userPreferences.userId.collectLatest { id ->
                _userId.value = id
                fetchUserInfo()
            }
        }
    }

    fun updateAllSelectedState() {
        val cartInfo = _userInfo.value?.cartInfo
        _allSelected.value = cartInfo?.all { it.isSelected } ?: false
        itemsCountAndPrice()
    }

    fun toggleAllSelection() {
        val userInfo = _userInfo.value ?: return
        val cartInfo = userInfo.cartInfo ?: return

        val allSelected = cartInfo.all { it.isSelected }
        val newList =
            cartInfo.map { it.copy(isSelected = !allSelected) }.toList() // Преобразуем в новый List

        _userInfo.value = userInfo.copy(cartInfo = newList) // Создаём новую ссылку

        updateAllSelectedState()

        Log.d("Proverochka" ,"${_userInfo.value!!.cartInfo}")
    }

    fun toggleSelection(index : Int) {
        val newList = _userInfo.value!!.cartInfo!!.toMutableList()
        newList[index] = newList[index].copy(isSelected = !newList[index].isSelected)
        _userInfo.value = _userInfo.value!!.copy(cartInfo = newList)
        updateAllSelectedState()
    }

    fun toggleFavorite(index : Int) {
        val selectedItem = _userInfo.value!!.cartInfo!![index]
        val updatedItem = selectedItem.copy(isFavorite = !selectedItem.isFavorite)

        // Обновляем локальное состояние UI
        val newList = _userInfo.value!!.cartInfo!!.toMutableList()
        newList[index] = updatedItem
        _userInfo.value = _userInfo.value!!.copy(cartInfo = newList)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.apiService.updateFavorite(
                    FavoriteUpdate(
                        userId.value!! ,
                        selectedItem.id ,
                        updatedItem.isFavorite
                    ) // передаем id товара и его новый статус
                )

                if (response.isSuccessful) {
                    val updatedFavorite = response.body()?.favorite
                    Log.d("favorite" ,"Updated on server: $updatedFavorite")

                    // После успешного ответа от сервера обновляем локальное состояние
                    _userInfo.value =
                        _userInfo.value!!.copy(cartInfo = _userInfo.value!!.cartInfo!!.mapIndexed { idx ,item ->
                            if (idx == index) item.copy(isFavorite = updatedFavorite.toBoolean()) else item
                        })
                } else {
                    Log.e("favorite" ,"Server error: ${response.errorBody()?.string()}")
                }
            } catch (e : Exception) {
                Log.e("favorite" ,"Network error: ${e.localizedMessage}")
            }
        }
    }

    fun toggleFavoriteForRecommend(id : Int) {
        val updatedList = _productsWithBrands.value.map { item ->
            if (item.id == id) item.copy(isFavorite = !item.isFavorite) else item
        }
        _productsWithBrands.value = updatedList

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.apiService.updateFavorite(
                    FavoriteUpdate(
                        userId.value ?: return@launch ,
                        id ,
                        updatedList.find { it.id == id }!!.isFavorite
                    )
                )
                if (!response.isSuccessful) {
                    Log.e("favorite" ,"Server error: ${response.errorBody()?.string()}")
                }
            } catch (e : Exception) {
                Log.e("favorite" ,"Network error: ${e.localizedMessage}")
            }
        }
    }


    fun updateCount(index : Int ,newCount : Int) {
        val newList = _userInfo.value!!.cartInfo!!.toMutableList()
        newList[index] = newList[index].copy(count = newCount)
        _userInfo.value = _userInfo.value!!.copy(cartInfo = newList)
        itemsCountAndPrice()
    }

    fun fetchBrands() {
        viewModelScope.launch {
            _brands.value = getUseCase.executeBrands()
        }
    }

    fun fetchProducts() {
        viewModelScope.launch {
            _products.value = getUseCase.executeProducts()
        }
    }

    fun fetchPromotion() {
        viewModelScope.launch {
            _promotion.value = getUseCase.executePromotion()
        }
    }

    fun fetchPromotions() {
        viewModelScope.launch {
            _promotions.value = getUseCase.executePromotions()
        }
    }

    fun fetchReviews() {
        viewModelScope.launch {
            _reviews.value = getUseCase.executeReviews().map { review ->
                review.copy(photo_urls = photoForReview(review.image_url))
            }
        }
    }

    fun setCurrentItemId(productId : Int) {
        viewModelScope.launch {
            _currentItem.value = productId
        }
    }

    fun setCurrentBrandId(brandId : Int) {
        viewModelScope.launch {
            _currentBrand.value = brandId - 1
        }
    }
}


