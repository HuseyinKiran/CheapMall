package com.huseyinkiran.cheapmall.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huseyinkiran.cheapmall.domain.repository.ProductRepository
import com.huseyinkiran.cheapmall.presentation.model.toUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _productsState = mutableStateOf(ProductUiState())
    val productsState: State<ProductUiState> = _productsState

    init {
        fetchProducts()
    }

    private fun fetchProducts() = viewModelScope.launch {

        _productsState.value = ProductUiState(isLoading = true)
        try {
            val result = repository.getProducts()
            val response = result.map { it.toUIModel() }
            _productsState.value = ProductUiState(isLoading = false, products = response)
        } catch (e: Exception) {
            _productsState.value =
                ProductUiState(isLoading = false, errorMessage = "Error occurred ! ${e.message}")
        }
    }

}