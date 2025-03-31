package dev.abdl.fakecommerce.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

//sealed class BottomNavItem(val screen: Screen, val title: String, val icon: ImageVector) {
//    data object Home : BottomNavItem(Screen.Home, R.string.home, Icons.Filled.Home)
//    data object Fav : BottomNavItem(Screen.Cart, R.string.fav, Icons.Filled.Favorite)
//    data object Profile : BottomNavItem(Screen.Profile, R.string.profile, Icons.Filled.Person)
//}

data class BottomNavItem(val screen: Screen, val title: String, val icon: ImageVector)