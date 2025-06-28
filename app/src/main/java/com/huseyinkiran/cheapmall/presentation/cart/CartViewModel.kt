package com.huseyinkiran.cheapmall.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huseyinkiran.cheapmall.domain.repository.CartRepository
import com.huseyinkiran.cheapmall.presentation.model.ProductUIModel
import com.huseyinkiran.cheapmall.presentation.model.toDomain
import com.huseyinkiran.cheapmall.presentation.model.toUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: CartRepository
) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<ProductUIModel>>(emptyList())
    val cartItems: StateFlow<List<ProductUIModel>> = _cartItems

    private val _totalAmount = MutableStateFlow(0.0)
    val totalAmount: StateFlow<Double> = _totalAmount

    fun getCart() = viewModelScope.launch {
        repository.getCartItems().collect { cartList ->
            _cartItems.value = cartList.map { it.toUIModel() }
        }
    }

    fun getTotalAmount() = viewModelScope.launch {
        repository.getTotalAmountFlow().collect {total ->
            _totalAmount.value = total
        }
    }

    fun addToCart(product: ProductUIModel) = viewModelScope.launch {
        repository.addToCart(product.toDomain())
        repository.getCartItems().collect { cartList ->
            _cartItems.value = cartList.map { it.toUIModel() }
        }
    }

    fun updateQuantity(product: ProductUIModel, countChange: Int) = viewModelScope.launch {
        val newQty = product.quantity + countChange

        if (newQty < 1) {
            repository.removeFromCart(productId = product.id)
        } else {
            repository.updateQuantity(productId = product.id, quantity = newQty)
        }

        repository.getCartItems().collect { cartList ->
            _cartItems.value = cartList.map { it.toUIModel() }
        }

    }

    fun removeFromCart(productId: Int) = viewModelScope.launch {
        repository.removeFromCart(productId)
        repository.getCartItems().collect { cartList ->
            _cartItems.value = cartList.map { it.toUIModel() }
        }
    }

}