package com.example.attoapp.data

interface DataRepository {
    suspend fun getBrands(): List<Brands>
    suspend fun getProducts(): List<Products>
    suspend fun getPromotions(): List<Promotions>
    suspend fun getReviews(): List<Reviews>
    suspend fun getPromotionProducts(): List<Promotion>
    suspend fun getUserInfo(userId:Int) : User
}

class DataRepositoryImpl(private val apiService: ApiService) : DataRepository {
    override suspend fun getBrands(): List<Brands> {
        return apiService.getBrands()
    }

    override suspend fun getProducts(): List<Products> {
        return apiService.getProducts()
    }

    override suspend fun getPromotions(): List<Promotions> {
        return apiService.getPromotions()
    }

    override suspend fun getReviews(): List<Reviews> {
        return apiService.getReviews()
    }

    override suspend fun getPromotionProducts(): List<Promotion> {
        return apiService.getPromotion()
    }

    override suspend fun getUserInfo(userId : Int) : User {
        return apiService.getUserInfo(userId)
    }
}