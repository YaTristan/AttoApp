package com.example.attoapp.domain

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.example.attoapp.data.Brands
import com.example.attoapp.data.CartItem
import com.example.attoapp.data.MergedProductsBrands
import com.example.attoapp.data.MergedPromotions
import com.example.attoapp.data.Products
import com.example.attoapp.data.Promotion
import com.example.attoapp.data.Promotions
import com.example.attoapp.data.Reviews
import com.example.attoapp.data.User

fun searchListOfBrand(
    brand_id : Int,
    allProducts : List<MergedProductsBrands>
) : List <MergedProductsBrands> {

    return allProducts.filter { it.brand_id == brand_id }

}

fun mergePromotions(
    promotions: List<Promotions>,
    promotionProducts: List<Promotion>,
    allProducts: List<MergedProductsBrands>
): List<MergedPromotions> {

    val productMap = allProducts.associateBy { it.id }  // O(n)
    val promotionMap = promotionProducts.associate { it.promotion_id to it.product_ids } // O(n)

    return promotions.mapNotNull { promo ->
        val productIds = promotionMap[promo.id] ?: emptyList()
        val products = productIds.mapNotNull { productMap[it] } // O(1) доступ к каждому продукту

        if (products.isNotEmpty()) {
            MergedPromotions(
                id = promo.id,
                name = promo.name,
                description = promo.description,
                products = products
            )
        } else null
    }
}


fun mergeBrandsAndProducts(
    reviews: List<Reviews>,
    brands: List<Brands>,
    allProducts: List<Products>
): List<MergedProductsBrands> {

    val brandMap = brands.associateBy({ it.id }, { it.name })  // O(n)

    return allProducts.map { product ->
        val brandName = brandMap[product.brand_id] ?: "Неизвестный бренд"
        MergedProductsBrands(
            id = product.id,
            name = product.name,
            price = product.price,
            image_url = product.image_url,
            brand_name = brandName,
            brand_id = product.brand_id,
            description = product.description,
            review = bestReview(reviews = reviews, product.id),
            sizes = createListOfSizes(product.sizes)
        )
    }
}


fun mergeFavorite(
    user: User,
    brands: List<Brands>,
    allProducts: List<MergedProductsBrands>
): User {

    val favoriteProductIds = splitFavorite(user.favorite_product ?: "").toSet()  // O(n)
    val favoriteShopIds = splitFavorite(user.favorite_shop ?: "").toSet()  // O(n)

    val favoriteProductsInfo = allProducts.filter { it.id in favoriteProductIds }  // O(n)
    val favoriteShopsInfo = brands.filter { it.id in favoriteShopIds }  // O(n)

    val cartItems = user.cart?.split(Regex("\\s*,\\s*|\\s+"))?.mapNotNull { it.trim() } ?: emptyList()

    val result = cartItems.chunked(4).mapNotNull { chunk ->
        if (chunk.size == 4) {
            val productId = chunk[0].toIntOrNull() ?: return@mapNotNull null
            val size = chunk[1]
            val color = chunk[2]
            val count = chunk[3].toIntOrNull() ?: 1

            val product = allProducts.find { it.id == productId } ?: return@mapNotNull null
            CartItem(
                id = product.id,
                product = product,
                size = size.toInt(),
                count = count,
                color = color,
                isFavorite = true
            )
        } else null
    }

    return user.copy(
        favorite_productsInfo = favoriteProductsInfo,
        favorite_shopInfo = favoriteShopsInfo,
        cartInfo = result
    )
}



fun createListOfSizes(
    sizes : String
) : List<Int> {
    val result = mutableListOf<Int>()
    val sizeParts = sizes.split(",")

    for (part in sizeParts) {
        if(part.contains("-")) {
            val rangeParts = part.split("-")
            val start = rangeParts[0].toInt()
            val end = rangeParts[1].toInt()

            result.addAll((start..end).toList())
        } else {
            result.add(part.toInt())
        }
    }
    return result
}

fun brandsForCatalog(brandList: List<Brands>): List<Pair<String, List<Brands>>> {
    return brandList
        .sortedBy { it.name }
        .groupBy { it.name[0].uppercaseChar() }
        .map { (letter, brands) -> letter.toString() to brands }
}

fun cartParse(
    allProducts: List<MergedProductsBrands>,
    cartList: String
): List<CartItem> {

    val productMap = allProducts.associateBy { it.id }  // O(n)

    return cartList.split(",").map { it.trim() }
        .chunked(4)
        .mapNotNull { chunk ->
            if (chunk.size == 4) {
                val productId = chunk[0].toIntOrNull() ?: return@mapNotNull null
                val product = productMap[productId] ?: return@mapNotNull null
                CartItem(
                    id = product.id,
                    product = product,
                    size = chunk[1].toInt(),
                    count = chunk[3].toIntOrNull() ?: 1,
                    color = chunk[2],
                    isFavorite = product.isFavorite
                )
            } else null
        }
}


fun makeItFavorite(
    allProducts: List<MergedProductsBrands>,
    user: User
): List<MergedProductsBrands> {

    val favoriteIds = user.favorite_productsInfo?.map { it.id }?.toSet() ?: emptySet()

    return allProducts.map { product ->
        product.copy(isFavorite = product.id in favoriteIds)
    }
}