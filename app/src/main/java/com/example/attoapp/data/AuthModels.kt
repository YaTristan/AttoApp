package com.example.attoapp.data

data class User(
    val id: Int? = null,
    val name : String,
    val surname : String,
    val username : String,
    val number : String? = null,
    val password : String? = null,
    val admin : Boolean = false,
    val favorite_product : String = "",
    val favorite_productsInfo : List<MergedProductsBrands>? = null,
    val favorite_shop : String = "",
    val favorite_shopInfo : List<Brands>? = null,
    val cart : String = "",
    val cartInfo : List<CartItem>? = null,
    val image_url : String? = null,
)

data class UserLogin(
    val username : String,
    val password : String
)

data class NumberLoginCheckResponse(
    val isFree : Boolean
)

data class UserResponse(
    val success: Boolean,
    val message: String,
    val user_id: Int? = null,
    val error: String? = null
)

data class FavoriteUpdate(
    val user_id: Int,
    val product_id: Int,
    val is_favorite: Boolean  // Убедись, что название совпадает с тем, что ожидает сервер
)

data class FavoriteResponse(
    val message: String,
    val favorite: String  // Обновленный список избранного
)