package dev.abdl.fakecommerce.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

@Composable
fun ProductItem(
    productItem: ProductItem,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(productItem.imageUrl),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
                .height(170.dp)
                .clip(MaterialTheme.shapes.extraSmall)
        )
        Text(
            text = productItem.title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = productItem.priceTag,
                fontSize = 14.sp,
                color = Color.Green,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

data class ProductItem(
    val imageUrl: String, val title: String,
    val priceTag: String,
)

@Composable
@DefaultPreview
fun ProductReview() {
    MaterialTheme {
        ProductItem(
            productItem = ProductItem(
                imageUrl = "https://picsum.photos/400/400", title = "Produk 6",
                priceTag = "Rp. 143.000",
            )
        )
    }
}