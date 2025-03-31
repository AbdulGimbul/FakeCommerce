package dev.abdl.fakecommerce.features.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.abdl.fakecommerce.R
import dev.abdl.fakecommerce.ui.components.CategorySection
import dev.abdl.fakecommerce.ui.components.DefaultPreview
import dev.abdl.fakecommerce.ui.components.ProductItem
import dev.abdl.fakecommerce.ui.components.RecommendationSection
import dev.abdl.fakecommerce.ui.components.ShapeItem

@Composable
fun HomeScreen(
//    viewModel: ProfileViewModel,
//    navController: NavController
) {

    Home()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home() {
    Scaffold(
        modifier = Modifier
            .background(color = Color.White)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(it)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Kategori",
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            CategorySection(shapeItems = shapeItems)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Rekomendasi",
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            RecommendationSection(productItem = productItems, navigateToDetail = {})
        }
    }
}

val shapeItems = listOf(
    ShapeItem(
        imageUrl = R.drawable.ic_launcher_foreground,
        label = "Sayur"
    ),
    ShapeItem(
        imageUrl = R.drawable.ic_launcher_foreground,
        label = "Buah"
    ),
    ShapeItem(
        imageUrl = R.drawable.ic_launcher_foreground,
        label = "Umbi"
    ),
    ShapeItem(
        imageUrl = R.drawable.ic_launcher_foreground,
        label = "Rempah"
    )
)

val productItems = listOf(
    ProductItem(
        imageUrl = "https://picsum.photos/400/400",
        title = "Produk 1",
        priceTag = "Rp. 15.000",
    ),
    ProductItem(
        imageUrl = "https://picsum.photos/400/400",
        title = "Produk 2",
        priceTag = "Rp. 19.000",
    ),
    ProductItem(
        imageUrl = "https://picsum.photos/400/400",
        title = "Produk 3",
        priceTag = "Rp. 13.000",
    ),
    ProductItem(
        imageUrl = "https://picsum.photos/400/400",
        title = "Produk 4",
        priceTag = "Rp. 63.000",
    ),
    ProductItem(
        imageUrl = "https://picsum.photos/400/400",
        title = "Produk 5",
        priceTag = "Rp. 43.000",
    ),
    ProductItem(
        imageUrl = "https://picsum.photos/400/400",
        title = "Produk 6",
        priceTag = "Rp. 143.000",
    ),
)

@DefaultPreview
@Composable
fun PreviewHome() {
    Home()
}