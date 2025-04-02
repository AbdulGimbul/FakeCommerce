package dev.abdl.fakecommerce.features.home.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dev.abdl.fakecommerce.R
import dev.abdl.fakecommerce.features.home.domain.Category
import dev.abdl.fakecommerce.features.home.domain.Product
import dev.abdl.fakecommerce.ui.components.CategorySection
import dev.abdl.fakecommerce.ui.components.DefaultPreview
import dev.abdl.fakecommerce.ui.components.RecommendationSection

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavController
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    Home(
        uiState = uiState.value,
        onEvent = { viewModel.onEvent(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    uiState: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
        Text(
            text = "Categories",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        CategorySection(
            categoryItems = categoryItems,
            selectedCategory = uiState.selectedCategory,
            onCategorySelected = { category ->
                onEvent(HomeUiEvent.CategorySelected(category))
            }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = when {
                    uiState.selectedCategory != null -> "${uiState.selectedCategory} Products"
                    else -> "All Products"
                },
                fontWeight = FontWeight.Bold
            )
            
            if (uiState.selectedCategory != null) {
                IconButton(
                    onClick = { onEvent(HomeUiEvent.ClearCategoryFilter) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear filter"
                    )
                }
            }
        }
        
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        uiState.errorMessage?.let { error ->
            Text(
                text = error,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        
        RecommendationSection(
            productItem = uiState.products,
            navigateToDetail = { /* TODO: Implement navigation */ }
        )
    }
}

private val categoryItems = listOf(
    Category(
        imageUrl = R.drawable.ic_mens_clothing,
        label = "men's clothing"
    ),
    Category(
        imageUrl = R.drawable.ic_jewelry,
        label = "jewelery"
    ),
    Category(
        imageUrl = R.drawable.ic_electronics,
        label = "electronics"
    ),
    Category(
        imageUrl = R.drawable.ic_womens_clothing,
        label = "women's clothing"
    )
)

@DefaultPreview
@Composable
private fun PreviewHome() {
    val products = listOf(
        Product(
            imageUrl = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
            title = "Fjallraven - Foldsack No. 1 Backpack",
            priceTag = "$109.95",
            category = "men's clothing"
        ),
        Product(
            imageUrl = "https://fakestoreapi.com/img/71YAIFU48IL._AC_UL640_QL65_ML3_.jpg",
            title = "John Hardy Women's Chain Bracelet",
            priceTag = "$695.00",
            category = "jewelery"
        )
    )

    MaterialTheme {
        Home(
            uiState = HomeUiState(products = products),
            onEvent = {}
        )
    }
}
