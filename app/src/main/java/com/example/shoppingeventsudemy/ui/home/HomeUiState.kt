package com.example.shoppingeventsudemy.ui.home

import com.example.shoppingeventsudemy.data.entities.ShoppingEvent

data class HomeUiState(
    val events: List<ShoppingEvent> = emptyList()
)