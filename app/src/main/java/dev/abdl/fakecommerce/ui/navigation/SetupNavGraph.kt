package dev.abdl.fakecommerce.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.abdl.fakecommerce.features.auth.presentation.LoginScreen
import dev.abdl.fakecommerce.features.home.presentation.HomeScreen
import dev.abdl.fakecommerce.features.cart.presentation.CartScreen
import dev.abdl.fakecommerce.ui.components.ProfileBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupNavGraph(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    var showProfileSheet by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Login.route) {
                BottomBar(
                    navController = navController,
                    currentRoute = currentRoute,
                    onProfileClick = { showProfileSheet = true }
                )
            }
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
                CartScreen(navController = navController)
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
                }
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
