package com.huseyinkiran.cheapmall.domain.repository

import com.huseyinkiran.cheapmall.domain.model.Product

interface ProductRepository {

    suspend fun getProducts(): List<Product>
}