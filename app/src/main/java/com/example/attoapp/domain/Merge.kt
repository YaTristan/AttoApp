package com.example.attoapp.domain

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

fun List<Promotions>.mergeWithProducts(
    promotionProducts: List<Promotion>,
    allProducts: List<MergedProductsBrands>
): List<MergedPromotions> {

    val productMap = allProducts.associateBy { it.id }  // O(n)
    val promotionMap = promotionProducts.associate { it.promotion_id to it.product_ids } // O(n)

    return this.mapNotNull { promo ->
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

fun List<Products>.mergeWithBrands(
    reviews: List<Reviews>,
    brands: List<Brands>
): List<MergedProductsBrands> {

    val brandMap = brands.associateBy({ it.id }, { it.name })  // O(n)

    return this.map { product ->
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
            sizes = product.sizes.toSizeList()
        )
    }
}


fun User.mergeFavorite(
    brands: List<Brands>,
    allProducts: List<MergedProductsBrands>
): User {

    val favoriteProductIds = splitFavorite(this.favorite_product ?: "").toSet()  // O(n)
    val favoriteShopIds = splitFavorite(this.favorite_shop ?: "").toSet()  // O(n)

    val favoriteProductsInfo = allProducts.filter { it.id in favoriteProductIds }  // O(n)
    val favoriteShopsInfo = brands.filter { it.id in favoriteShopIds }  // O(n)

    val cartItems = this.cart?.split(Regex("\\s*,\\s*|\\s+"))?.mapNotNull { it.trim() } ?: emptyList()

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

    return this.copy(
        favorite_productsInfo = favoriteProductsInfo,
        favorite_shopInfo = favoriteShopsInfo,
        cartInfo = result
    )
}


fun String.toSizeList(): List<Int> {
    val result = mutableListOf<Int>()
    this.split(",").forEach { part ->
        if ("-" in part) {
            val (start, end) = part.split("-").map { it.toInt() }
            result.addAll(start..end)
        } else {
            result.add(part.toInt())
        }
    }
    return result
}


fun List<Brands>.groupForCatalog(): List<Pair<String, List<Brands>>> {
    return this.sortedBy { it.name }
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