package com.huseyinkiran.cheapmall.presentation.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.huseyinkiran.cheapmall.R
import com.huseyinkiran.cheapmall.presentation.model.ProductUIModel
import com.huseyinkiran.cheapmall.presentation.utils.SwipeToDeleteContainer

@Composable
fun CartContent(viewModel: CartViewModel, navigateToDetail: (ProductUIModel) -> Unit) {

    val cartItems by viewModel.cartItems.collectAsState()
    val totalAmount by viewModel.totalAmount.collectAsState()

    var lineCount by remember { mutableIntStateOf(1) }
    val bottomPadding = if (lineCount > 1) 96.dp else 72.dp

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = bottomPadding)
        ) {
            items(cartItems, key = { it.id }) { cartItem ->
                SwipeToDeleteContainer(item = cartItem, onDelete = {
                    viewModel.removeFromCart(cartItem.id)
                }) {
                    CartItem(
                        product = cartItem,
                        viewModel = viewModel,
                        navigateToDetail = navigateToDetail
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(0.5f)) {
                Text(
                    text = "Total",
                    color = Color.DarkGray,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "$ %.2f".format(totalAmount),
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = if (totalAmount >= 1000.0) "Free Shipping" else "Spend $ %.2f".format(
                        1000.0 - totalAmount
                    ) + " more to get free shipping",
                    color = colorResource(if (totalAmount>= 1000.0) R.color.green else R.color.orange),
                    fontWeight = FontWeight.SemiBold,
                    onTextLayout = { layoutResult ->
                        lineCount = layoutResult.lineCount
                    }
                )
            }
            Button(
                onClick = {

                },
                modifier = Modifier.weight(0.5f),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(colorResource(R.color.orange))
            ) {
                Text(text = "Complete Order")
            }
        }

    }

}

@Composable
fun CartItem(
    product: ProductUIModel,
    viewModel: CartViewModel,
    navigateToDetail: (ProductUIModel) -> Unit
) {

    Column(modifier = Modifier.background(Color.White)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .clickable {
                    navigateToDetail(product)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(0.2f)) {
                AsyncImage(
                    model = product.image,
                    contentDescription = product.title,
                    modifier = Modifier
                        .size(80.dp)
                )
            }
            Spacer(modifier = Modifier.weight(0.02f))
            Column(modifier = Modifier.weight(0.74f)) {
                Text(
                    text = product.title,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(text = product.category, color = Color.Blue)
                Row(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.weight(0.5f)) {
                        Card(
                            shape = CircleShape,
                            modifier = Modifier
                                .size(32.dp)
                                .align(Alignment.CenterVertically)
                        ) {
                            IconButton(onClick = {
                                viewModel.updateQuantity(product = product, countChange = -1)
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Remove,
                                    contentDescription = "Decrease count",
                                    tint = colorResource(R.color.orange)
                                )
                            }
                        }
                        Card(
                            shape = CircleShape,
                            modifier = Modifier.align(Alignment.CenterVertically),
                            colors = CardDefaults.cardColors(Color.White)
                        ) {
                            Text(
                                text = product.quantity.toString(),
                                modifier = Modifier.padding(8.dp),
                                fontWeight = FontWeight.SemiBold,
                                color = colorResource(R.color.orange)
                            )
                        }
                        Card(
                            shape = CircleShape,
                            modifier = Modifier
                                .size(32.dp)
                                .align(Alignment.CenterVertically),
                            colors = CardDefaults.cardColors(colorResource(R.color.orange))
                        ) {
                            IconButton(onClick = {
                                viewModel.updateQuantity(product = product, countChange = 1)
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Increase count",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                    Text(
                        text = "$ %.2f".format(product.price * product.quantity),
                        color = Color.Red,
                        modifier = Modifier
                            .weight(0.5f)
                            .align(Alignment.CenterVertically)
                            .padding(horizontal = 8.dp),
                        textAlign = TextAlign.End,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
    }
}