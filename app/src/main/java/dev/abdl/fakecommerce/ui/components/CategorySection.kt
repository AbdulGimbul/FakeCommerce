package dev.abdl.fakecommerce.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.abdl.fakecommerce.features.home.domain.Category

@Composable
fun CategorySection(
    categoryItems: List<Category>,
    selectedCategory: String?,
    onCategorySelected: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(categoryItems) { item ->
            ShapeItem(
                categoryItem = item,
                isSelected = item.label == selectedCategory,
                onClick = { onCategorySelected(item.label) }
            )
        }
    }
}


@Composable
private fun ShapeItem(
    categoryItem: Category,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .aspectRatio(1f)
                .clip(shape = MaterialTheme.shapes.medium)
                .then(
                    if (isSelected) {
                        Modifier.border(
                            2.dp,
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.shapes.medium
                        )
                    } else Modifier
                )
        ) {
            Image(
                painter = painterResource(categoryItem.imageUrl),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Box(
            modifier = Modifier
                .width(100.dp)
                .padding(top = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = categoryItem.label,
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Black,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}

@DefaultPreview
@Composable
fun PreviewShapeRowExample() {
//    val shapeItems = listOf(
//        ShapeItem(
//            imageUrl = R.drawable.cat_sayur,
//            label = "Sayur"
//        ),
//        ShapeItem(
//            imageUrl = R.drawable.cat_buah,
//            label = "Buah"
//        ),
//        ShapeItem(
//            imageUrl = R.drawable.cat_umbi,
//            label = "Umbi"
//        ),
//        ShapeItem(
//            imageUrl = R.drawable.cat_rempah,
//            label = "Rempah"
//        )
//    )
//    CategorySection(shapeItems)
}