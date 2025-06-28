package com.huseyinkiran.cheapmall.presentation.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huseyinkiran.cheapmall.domain.repository.FavoritesRepository
import com.huseyinkiran.cheapmall.presentation.model.ProductUIModel
import com.huseyinkiran.cheapmall.presentation.model.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: FavoritesRepository,
) : ViewModel() {

    private val _isFavorite = mutableStateOf(false)
    val isFavorite: State<Boolean> = _isFavorite

    fun addToFavorites(product: ProductUIModel) = viewModelScope.launch {
        try {
            repository.addToFavorites(product.toDomain())
            _isFavorite.value = true
        } catch (_: Exception) {

        }
    }

    fun removeFromFavorites(productId: Int) = viewModelScope.launch {
        try {
            repository.removeFromFavorites(productId)
            _isFavorite.value = false
        } catch (_: Exception) {

        }
    }


    fun checkIfFavorite(productId: Int) = viewModelScope.launch {
        _isFavorite.value = repository.isFavorite(productId)
    }

}