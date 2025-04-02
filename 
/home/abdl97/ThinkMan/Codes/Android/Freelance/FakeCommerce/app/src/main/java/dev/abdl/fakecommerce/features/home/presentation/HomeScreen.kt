
    Home(
        uiState = uiState,
        onCategorySelected = viewModel::onCategorySelected,
        onClearCategoryFilter = viewModel::clearCategoryFilter
    )
fun Home(
    uiState: HomeUiState,
    onCategorySelected: (String) -> Unit,
    onClearCategoryFilter: () -> Unit
) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Kategori",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                
                if (uiState.selectedCategory != null) {
                    FilterChip(
                        selected = true,
                        onClick = { onClearCategoryFilter() },
                        label = { Text("Clear Filter") },
                        trailingIcon = {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = "Clear",
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            CategorySection(
                categoryItems = categoryItems,
                selectedCategory = uiState.selectedCategory,
                onCategorySelected = onCategorySelected
            )

    Home(
        uiState = HomeUiState(
            products = productItems,
            selectedCategory = null
        ),
        onCategorySelected = {},
        onClearCategoryFilter = {}
    )
