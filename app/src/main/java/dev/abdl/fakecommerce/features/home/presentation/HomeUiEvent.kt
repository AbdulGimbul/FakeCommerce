package dev.abdl.fakecommerce.features.home.presentation

sealed class HomeUiEvent {
    data class CategorySelected(val category: String) : HomeUiEvent()
    object ClearCategoryFilter : HomeUiEvent()
    object RefreshProducts : HomeUiEvent()
}
