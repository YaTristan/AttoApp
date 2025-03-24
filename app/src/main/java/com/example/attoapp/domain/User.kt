package com.example.attoapp.domain

import com.example.attoapp.data.Products

fun splitFavorite(favorites: String): List<Int> {
    return favorites.split(",")
        .mapNotNull { it.trim().toIntOrNull() }
}