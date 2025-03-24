package com.example.attoapp.domain

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.Composable
import com.example.attoapp.data.Brands
import com.example.attoapp.data.DataRepository
import com.example.attoapp.data.Products
import com.example.attoapp.data.Promotion
import com.example.attoapp.data.Promotions
import com.example.attoapp.data.RetrofitInstance
import com.example.attoapp.data.Reviews
import com.example.attoapp.data.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GetUseCase(private val repository: DataRepository) {

    suspend fun executeBrands(): List<Brands> {
        return repository.getBrands()
    }
    suspend fun executeProducts(): List<Products> {
        return repository.getProducts()
    }
    suspend fun executePromotions(): List<Promotions> {
        return repository.getPromotions()
    }
    suspend fun executeReviews(): List<Reviews> {
        return repository.getReviews()
    }
    suspend fun executePromotion(): List<Promotion> {
        return repository.getPromotionProducts()
    }
    suspend fun executeUserInfo(userId : Int) : User {
        return repository.getUserInfo(userId)
    }
}