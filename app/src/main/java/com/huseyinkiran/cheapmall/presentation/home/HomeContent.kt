package com.huseyinkiran.cheapmall.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.huseyinkiran.cheapmall.presentation.model.ProductUIModel
import kotlin.math.floor
import kotlin.math.roundToInt

@Composable
fun HomeContent(products: List<ProductUIModel>, navigateToDetail: (ProductUIModel) -> Unit) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        content = {
            items(products) { product ->
                ProductItem(product = product, navigateToDetail = navigateToDetail)
            }
        }
    )

}

@Composable
fun ProductItem(product: ProductUIModel, navigateToDetail: (ProductUIModel) -> Unit) {
    Card(
        modifier = Modifier
            .height(250.dp)
            .padding(8.dp)
            .clickable {
                navigateToDetail(product)
            },
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {

            Box(
                modifier = Modifier
                    .weight(0.7f)
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = product.image,
                    contentDescription = product.title,
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(8.dp)
                )
            }
            Box(modifier = Modifier.weight(0.1f)) {
                Text(
                    text = product.title,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth()
                )
            }
            Row(modifier = Modifier.weight(0.1f), verticalAlignment = Alignment.CenterVertically) {
                RatingStars(product.rating.rate)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "(${product.rating.count})", fontSize = 12.sp)
            }
            Box(modifier = Modifier.weight(0.1f)) {
                Text(text = "$ %.2f".format(product.price), modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun RatingStars(rating: Double) {

    val filledStars = floor(rating).toInt()
    val emptyStars = 5 - rating.roundToInt()

    Row {
        for (i in 1..rating.roundToInt()) {
            val icon = if (i <= filledStars) Icons.Filled.Star else Icons.Outlined.Star
            Icon(
                imageVector = icon,
                contentDescription = "Filled star",
                tint = Color(0xFFFFC107),
                modifier = Modifier.size(16.dp)
            )
        }
        repeat(emptyStars) {
            Icon(imageVector = Icons.Filled.StarOutline,
                contentDescription = "Empty Star",
                tint = Color(0xFFFFC107),
                modifier = Modifier.size(16.dp))
        }
    }
}