package com.huseyinkiran.cheapmall.presentation.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.huseyinkiran.cheapmall.R
import com.huseyinkiran.cheapmall.presentation.cart.CartViewModel
import com.huseyinkiran.cheapmall.presentation.home.RatingStars
import com.huseyinkiran.cheapmall.presentation.model.ProductUIModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun FavoritesContent(
    favoritesViewModel: FavoritesViewModel,
    cartViewModel: CartViewModel,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    navigateToDetail: (ProductUIModel) -> Unit
) {

    val favorites = favoritesViewModel.favorites.collectAsState()
    val cartItems by cartViewModel.cartItems.collectAsState()
    val errorMessage = favoritesViewModel.errorMessage.value

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (errorMessage != null) {
            Text(text = errorMessage, modifier = Modifier.padding(16.dp), color = Color.Red)
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(favorites.value) { product ->
                FavProductItem(
                    product = product,
                    cartItems = cartItems,
                    cartViewModel = cartViewModel,
                    snackbarHostState = snackbarHostState,
                    coroutineScope = coroutineScope,
                    navigateToDetail = navigateToDetail
                )
            }
        }

    }

}

@Composable
fun FavProductItem(
    product: ProductUIModel,
    cartItems: List<ProductUIModel>,
    cartViewModel: CartViewModel,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    navigateToDetail: (ProductUIModel) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(4.dp)
            .clickable {
                navigateToDetail(product)
            }
    ) {
        AsyncImage(
            model = product.image,
            contentDescription = product.title,
            modifier = Modifier
                .weight(0.24f)
                .size(100.dp)
                .align(Alignment.CenterVertically)
                .background(Color.White),
            contentScale = ContentScale.Inside,
        )
        Spacer(modifier = Modifier.weight(0.02f))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.74f)
        ) {
            Text(text = product.category, color = Color.Blue)
            Text(text = product.title, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = product.rating.rate.toString())
                Spacer(modifier = Modifier.width(4.dp))
                RatingStars(rating = product.rating.rate)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "(${product.rating.count} reviews)")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$ %.2f".format(product.price),
                    modifier = Modifier.weight(0.5f),
                    fontWeight = FontWeight.SemiBold
                )
                Button(
                    onClick = {
                        val isInCart = cartItems.any { it.id == product.id }

                        if (!isInCart) {
                            cartViewModel.addToCart(product = product)
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("${product.title} added to cart")
                            }
                        } else {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("You already added ${product.title} to cart")
                            }
                        }
                    }, modifier = Modifier.weight(0.5f),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.orange))
                ) {
                    Text(text = "Add to cart")
                }
            }
        }
    }
    HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))
}