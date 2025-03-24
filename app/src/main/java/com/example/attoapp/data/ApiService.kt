package com.example.attoapp.data

import okhttp3.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("brands")
    suspend fun getBrands(): List<Brands>

    @GET("products")
    suspend fun getProducts(): List<Products>

    @GET("promotions")
    suspend fun getPromotions(): List<Promotions>

    @GET("/promotion_products")
    suspend fun getPromotion(): List<Promotion>

    @GET("reviews")
    suspend fun getReviews(): List<Reviews>

    @GET("/check_phone/{number}")
    suspend fun checkPhone(@Path("number") number : String) : Response<NumberLoginCheckResponse>
    @GET("/check_username/{username}")
    suspend fun checkUsername(@Path("username") username : String) : Response<NumberLoginCheckResponse>

    @POST("/create_user")
    suspend fun createUser(@Body user: User): Response<UserResponse>
    @POST("/login")
    suspend fun login(@Body user: UserLogin): Response<UserResponse>

    @GET("/user_info/{user_id}")
    suspend fun getUserInfo(@Path("user_id") user_id : Int) : User

    @POST("updateFavorite")
    suspend fun updateFavorite(@Body favoriteUpdate: FavoriteUpdate): Response<FavoriteResponse>

}