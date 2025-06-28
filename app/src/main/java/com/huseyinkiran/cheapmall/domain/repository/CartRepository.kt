package com.huseyinkiran.cheapmall.domain.repository

import com.huseyinkiran.cheapmall.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun addToCart(product: Product)
    suspend fun removeFromCart(productId: Int)
    suspend fun updateQuantity(productId: Int, quantity: Int)
    fun getCartItems(): Flow<List<Product>>
    fun getTotalAmountFlow(): Flow<Double>
    suspend fun clearCart()
}