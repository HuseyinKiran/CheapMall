package com.huseyinkiran.cheapmall.data.repository

import com.huseyinkiran.cheapmall.data.api.ProductsApi
import com.huseyinkiran.cheapmall.domain.model.Product
import com.huseyinkiran.cheapmall.domain.model.toDomain
import com.huseyinkiran.cheapmall.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductsApi
) : ProductRepository {

    override suspend fun getProducts(): List<Product> {
        return api.getProducts().map { it.toDomain() }
    }
}