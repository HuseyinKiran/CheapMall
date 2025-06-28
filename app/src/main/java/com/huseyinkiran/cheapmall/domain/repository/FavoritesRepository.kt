package com.huseyinkiran.cheapmall.domain.repository

import com.huseyinkiran.cheapmall.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    suspend fun addToFavorites(product: Product)
    suspend fun removeFromFavorites(productId: Int)
    suspend fun isFavorite(productId: Int): Boolean
    fun getFavorites(): Flow<List<Product>>
}