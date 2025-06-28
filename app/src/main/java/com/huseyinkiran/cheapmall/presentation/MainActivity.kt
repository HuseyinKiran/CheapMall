package com.huseyinkiran.cheapmall.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.huseyinkiran.cheapmall.presentation.account.AccountViewModel
import com.huseyinkiran.cheapmall.presentation.cart.CartViewModel
import com.huseyinkiran.cheapmall.presentation.favorites.FavoritesViewModel
import com.huseyinkiran.cheapmall.presentation.navigation.AppNavHost
import com.huseyinkiran.cheapmall.presentation.navigation.BottomNavItems
import com.huseyinkiran.cheapmall.presentation.navigation.Route
import com.huseyinkiran.cheapmall.presentation.utils.TopAppBar
import com.huseyinkiran.cheapmall.ui.theme.CheapMallTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CheapMallTheme {
                CheapMall()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CheapMall() {

    val cartViewModel: CartViewModel = hiltViewModel()
    val favoritesViewModel: FavoritesViewModel = hiltViewModel()
    val accountViewModel: AccountViewModel = hiltViewModel()

    val cartItems by cartViewModel.cartItems.collectAsState()
    val favoriteItems by favoritesViewModel.favorites.collectAsState()
    val userUid by accountViewModel.userUid.collectAsState()

    LaunchedEffect(userUid) {
        cartViewModel.getCart()
        favoritesViewModel.fetchFavorites()
    }

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val contentWindowInsets = when (currentRoute) {
        Route.HomeScreen.route -> WindowInsets.safeDrawing
        Route.ProductDetailScreen.route -> WindowInsets.safeDrawing
        else -> WindowInsets.ime
    }

    val items = BottomNavItems.items

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = contentWindowInsets,
        topBar = {
            when (currentRoute) {
                Route.HomeScreen.route -> TopAppBar()
                Route.FavoritesScreen.route -> TopAppBar()
                Route.CartScreen.route -> TopAppBar()
                Route.AccountScreen.route -> TopAppBar()
                Route.ProductDetailScreen.route -> TopAppBar(navController = navController)
            }
        },
        bottomBar = {
            if (currentRoute in listOf(
                    Route.HomeScreen.route,
                    Route.CartScreen.route,
                    Route.FavoritesScreen.route,
                    Route.AccountScreen.route
                )
            ) {
                NavigationBar {
                    items.forEachIndexed { _, item ->
                        val badgeCount = when (item.route) {
                            Route.CartScreen.route -> cartItems.size
                            Route.FavoritesScreen.route -> favoriteItems.size
                            else -> 0
                        }

                        NavigationBarItem(
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            label = {
                                Text(text = item.title)
                            },
                            icon = {
                                BadgedBox(badge = {
                                    if (badgeCount > 0) {
                                        Badge {
                                            Text(text = badgeCount.toString())
                                        }
                                    }
                                }) {
                                    Icon(
                                        imageVector = if (item.route == currentRoute) item.selectedIcon
                                        else item.unselectedIcon,
                                        contentDescription = item.title
                                    )
                                }
                            }
                        )
                    }
                }
            }
        },
        content = { innerPadding ->
            AppNavHost(navController = navController, innerPadding = innerPadding)
        }

    )

}