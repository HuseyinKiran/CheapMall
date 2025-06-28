package com.huseyinkiran.cheapmall.presentation.home

import com.huseyinkiran.cheapmall.presentation.model.ProductUIModel

data class ProductUiState(
    val isLoading: Boolean = false,
    val products: List<ProductUIModel> = emptyList(),
    val errorMessage: String? = null
)