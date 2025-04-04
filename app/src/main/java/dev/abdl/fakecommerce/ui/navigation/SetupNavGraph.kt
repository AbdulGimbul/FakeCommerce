package dev.abdl.fakecommerce.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import dev.abdl.fakecommerce.features.auth.presentation.LoginScreen
import dev.abdl.fakecommerce.features.cart.presentation.CartScreen
import dev.abdl.fakecommerce.features.detail.presentation.DetailScreen
import dev.abdl.fakecommerce.features.home.presentation.HomeScreen
import dev.abdl.fakecommerce.ui.components.ProfileBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupNavGraph(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var showProfileSheet by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            if (currentRoute !in listOf(Screen.Login.route, "${Screen.Detail.route}/{productId}")) {
                BottomBar(
                    navController = navController,
                    currentRoute = currentRoute,
                    onProfileClick = { showProfileSheet = true }
                )
            }
        },
        topBar = {
            if (currentRoute == "${Screen.Detail.route}/{productId}") {
                TopAppBar(
                    title = { Text("Product Detail") },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                        }
                    }
                )
            } else null
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Login.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = Screen.Login.route) {
                LoginScreen(viewModel = hiltViewModel(), navController = navController)
            }

            composable(route = Screen.Home.route) {
                HomeScreen(viewModel = hiltViewModel(), navController = navController)
            }

            composable(route = Screen.Cart.route) {
                CartScreen(viewModel = hiltViewModel(), navController = navController)
            }

            composable(
                route = "${Screen.Detail.route}/{productId}",
                arguments = listOf(
                    navArgument("productId") { type = NavType.StringType }
                )
            ) {
                DetailScreen(viewModel = hiltViewModel())
            }
        }

        if (showProfileSheet) {
            ProfileBottomSheet(
                onDismiss = { showProfileSheet = false },
                onLogout = {
                    showProfileSheet = false
                    navController.navigate(Screen.Login.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = true
                        }
                    }
                },
                viewModel = hiltViewModel()
            )
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    currentRoute: String?,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        val navigationItems = listOf(
            BottomNavItem(
                title = "Beranda",
                icon = Icons.Outlined.Home,
                screen = Screen.Home
            ),
            BottomNavItem(
                title = "Keranjang",
                icon = Icons.Outlined.ShoppingCart,
                screen = Screen.Cart
            ),
            BottomNavItem(
                title = "Profile",
                icon = Icons.Outlined.AccountCircle,
                screen = Screen.Profile
            )
        )

        navigationItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.screen.route,
                onClick = {
                    if (item.screen == Screen.Profile) {
                        onProfileClick()
                    } else {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(text = item.title) }
            )
        }
    }
}
