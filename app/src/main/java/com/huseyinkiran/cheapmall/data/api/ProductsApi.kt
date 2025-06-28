package com.huseyinkiran.cheapmall.data.api

import com.huseyinkiran.cheapmall.data.model.ProductDto
import retrofit2.http.GET

interface ProductsApi {

    @GET("/products")
    suspend fun getProducts(): List<ProductDto>
}