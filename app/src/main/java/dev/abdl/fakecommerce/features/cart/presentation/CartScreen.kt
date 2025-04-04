package dev.abdl.fakecommerce.features.cart.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import dev.abdl.fakecommerce.features.cart.domain.CartEntity

@Composable
fun CartScreen(
    viewModel: CartViewModel,
    navController: NavController
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    Cart(
        uiState = uiState.value,
        onEvent = { viewModel.onEvent(it) }
    )
}

@Composable
fun Cart(
    uiState: CartUiState,
    onEvent: (CartUiEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.cartItems.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Your cart is empty")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {
                items(uiState.cartItems) { item ->
                    CartItem(item, onEvent)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Total: $${uiState.totalPrice}", fontWeight = FontWeight.Bold)
                Button(onClick = { onEvent(CartUiEvent.Checkout) }) {
                    Text("Checkout")
                }
            }
        }
    }
    if (uiState.showInvoice) {
        AlertDialog(
            onDismissRequest = { onEvent(CartUiEvent.DismissInvoice) },
            confirmButton = {
                TextButton(
                    onClick = {
                        onEvent(CartUiEvent.DismissInvoice)
                        onEvent(CartUiEvent.ClearCart)
                    }
                ) {
                    Text("Close")
                }
            },
            title = {
                Text("Invoice")
            },
            text = {
                Column {
                    uiState.cartItems.forEach { item ->
                        Text("${item.title} x${item.quantity} = $${item.price * item.quantity}")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    val total = uiState.cartItems.sumOf { it.price * it.quantity }
                    Text(
                        "Total: $${String.format("%.2f", total)}",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        )
    }
}

@Composable
fun CartItem(
    item: CartEntity,
    onEvent: (CartUiEvent) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(item.imageUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(item.title, fontWeight = FontWeight.Bold)
                Text("$${item.price} x ${item.quantity}", color = MaterialTheme.colorScheme.primary)
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onEvent(CartUiEvent.DecreaseQuantity(item.productId)) }) {
                    Icon(Icons.Default.Remove, contentDescription = "Decrease")
                }
                Text(item.quantity.toString(), Modifier.padding(horizontal = 8.dp))
                IconButton(onClick = { onEvent(CartUiEvent.IncreaseQuantity(item.productId)) }) {
                    Icon(Icons.Default.Add, contentDescription = "Increase")
                }
            }
        }
    }
}