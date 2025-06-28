package com.huseyinkiran.cheapmall.presentation.favorites

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huseyinkiran.cheapmall.domain.repository.FavoritesRepository
import com.huseyinkiran.cheapmall.presentation.model.ProductUIModel
import com.huseyinkiran.cheapmall.presentation.model.toUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: FavoritesRepository
) : ViewModel() {

    private val _favorites = MutableStateFlow<List<ProductUIModel>>(emptyList())
    val favorites: StateFlow<List<ProductUIModel>> = _favorites

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    fun fetchFavorites() = viewModelScope.launch {
        try {
            val favProducts = repository.getFavorites()
            favProducts.collect { favs ->
                _favorites.value = favs.map { it.toUIModel() }
            }
        } catch (e: Exception) {
            _errorMessage.value = e.localizedMessage ?: "Unknown error"
        }
    }

}