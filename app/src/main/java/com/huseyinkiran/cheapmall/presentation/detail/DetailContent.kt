package com.huseyinkiran.cheapmall.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.huseyinkiran.cheapmall.R
import com.huseyinkiran.cheapmall.presentation.cart.CartViewModel
import com.huseyinkiran.cheapmall.presentation.home.RatingStars
import com.huseyinkiran.cheapmall.presentation.model.ProductUIModel
import kotlinx.coroutines.launch

@Composable
fun DetailContent(
    product: ProductUIModel,
    detailViewModel: DetailViewModel,
    cartViewModel: CartViewModel,
    snackbarHostState: SnackbarHostState
) {

    val cartItems by cartViewModel.cartItems.collectAsState()
    val isFavorite = detailViewModel.isFavorite.value
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 64.dp)
        ) {

            Box(
                modifier = Modifier
                    .weight(0.7f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = product.image,
                    contentDescription = "Product Image",
                    modifier = Modifier.padding(32.dp),
                    contentScale = ContentScale.Fit
                )
                Card(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 16.dp, end = 16.dp),
                    shape = CircleShape,
                    elevation = CardDefaults.cardElevation(8.dp),
                    colors = CardDefaults.cardColors(Color.White)
                ) {
                    IconButton(onClick = {
                        if (isFavorite) {
                            detailViewModel.removeFromFavorites(product.id)
                        } else {
                            detailViewModel.addToFavorites(product)
                        }
                    }) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (isFavorite) Color.Red else Color.Black
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                Text(text = product.category, color = Color.Blue)
                Text(text = product.title, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                Text(
                    text = product.description,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = product.rating.rate.toString())
                    Spacer(modifier = Modifier.width(8.dp))
                    RatingStars(rating = product.rating.rate)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "(${product.rating.count} reviews)")
                }
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(64.dp)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$ %.2f".format(product.price),
                modifier = Modifier.weight(0.5f),
                color = Color.Red,
                fontWeight = FontWeight.Bold
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
                },
                modifier = Modifier.weight(0.5f),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(colorResource(R.color.orange))
            ) {
                Text(text = "Add to cart")
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )

    }
}