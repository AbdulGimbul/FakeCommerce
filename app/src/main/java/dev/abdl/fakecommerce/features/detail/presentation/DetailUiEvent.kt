package dev.abdl.fakecommerce.features.detail.presentation

sealed class DetailUiEvent {
    object AddToCartClicked : DetailUiEvent()
}