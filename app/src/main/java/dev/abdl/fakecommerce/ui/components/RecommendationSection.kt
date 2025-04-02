package dev.abdl.fakecommerce.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.abdl.fakecommerce.features.home.domain.Product

@Composable
fun RecommendationSection(
    productItem: List<Product>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(productItem) { data ->
            Surface(
                shape = RoundedCornerShape(8.dp),
                shadowElevation = 4.dp
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    ProductItem(data)
                }
            }
        }
    }
}

@DefaultPreview
@Composable
fun PreviewRecommendationExample() {
//    val productItem = listOf(
//        Product(
//            imageUrl = "https://picsum.photos/400/400",
//            title = "Produk 1",
//            priceTag = "Rp. 15.000",
//        ),
//        Product(
//            imageUrl = "https://picsum.photos/400/400",
//            title = "Produk 2",
//            priceTag = "Rp. 19.000",
//        ),
//        Product(
//            imageUrl = "https://picsum.photos/400/400",
//            title = "Produk 3",
//            priceTag = "Rp. 13.000",
//        ),
//        Product(
//            imageUrl = "https://picsum.photos/400/400",
//            title = "Produk 4",
//            priceTag = "Rp. 63.000",
//        ),
//        Product(
//            imageUrl = "https://picsum.photos/400/400",
//            title = "Produk 5",
//            priceTag = "Rp. 43.000",
//        ),
//        Product(
//            imageUrl = "https://picsum.photos/400/400",
//            title = "Produk 6",
//            priceTag = "Rp. 143.000",
//        ),
//    )
//
//    RecommendationSection(productItem, navigateToDetail = {})
}