package com.example.attoapp.domain

import android.util.Log
import com.example.attoapp.data.Reviews

fun photoForReview(
    photo_urls : String?,
    ) : List<String> {

    if(photo_urls != null) {
        val result = mutableListOf<String>()
        val urlsParts = photo_urls.split(",")

        for (parts in urlsParts) {
            result += parts
        }

        return result
    }
    else return emptyList()
}

fun bestReview(
    reviews: List<Reviews>,
    productId : Int
) : Reviews? {

    Log.d("Size of reviews list:" ,"${reviews.size}")

    var bestReview: Reviews? = null
    var bestRate = 0

    reviews.forEach { review ->
        if(review.product_id == productId) {
            val rate = review.stars * 2 +
                    (if (review.image_url == null) 0 else 3) +
                    (if (review.review_text != null) 2 else 0)


            if (rate > bestRate || bestReview == null) {
                bestRate = rate
                bestReview = review
            }
        }
    }

    return bestReview
}

fun reviewsAll(
    productId: Int,
    reviewsList: List<Reviews>
): List<Reviews> {
    return reviewsList.filter { it.product_id == productId }
}

