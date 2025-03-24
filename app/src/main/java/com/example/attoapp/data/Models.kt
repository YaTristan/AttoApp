package com.example.attoapp.data

import com.google.gson.annotations.SerializedName
import retrofit2.http.Url

data class Brands(
    val id:Int,
    val name:String,
    val description:String,
    val image_url:String,
    val category:String,
)

data class Products(
    val id:Int,
    val name:String,
    val image_url : String,
    val price:Double,
    val brand_id : Int,
    val description : String,
    val sizes : String,
)

data class Promotion(
    val promotion_id: Int,
    val product_ids: List<Int>
)
data class Promotions(
    val id:Int,
    val name:String,
    val description : String
)

data class MergedPromotions(
    val id:Int,
    val name:String,
    val description : String,
    val products : List<MergedProductsBrands>,
)

data class MergedProductsBrands(
    val id : Int,
    val name : String,
    val image_url : String,
    val price : Double,
    val brand_name : String,
    val brand_id:Int,
    val isFavorite : Boolean=  false,
    val description : String,
    val review: Reviews?,
    val sizes : List<Int>,
)

data class Reviews(
    val id : Int,
    val user_name:String,
    val user_surname:String,
    val stars : Int,
    val product_id : Int,
    val review_text : String?,
    val image_url : String?,
    val created_at : String,
    val photo_urls : List<String>?
)

data class CartItem(
    val id: Int,
    val product : MergedProductsBrands,
    val color : String,
    val size : Int,
    val count : Int = 1,
    val isFavorite : Boolean,
    val isSelected : Boolean = true,
)