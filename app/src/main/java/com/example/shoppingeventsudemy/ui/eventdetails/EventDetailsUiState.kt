package com.example.shoppingeventsudemy.ui.eventdetails

import com.example.shoppingeventsudemy.data.entities.ShoppingItem
import com.example.shoppingeventsudemy.ui.addevent.AddEventDetails

data class ItemDetails(
    val itemId: Long = 0,
    val eventId: Long = 0,
    val name: String = "",
    val price: String = "",
    val quantity: String = ""
)

data class ItemUiState(
    val isEdit: Boolean = false,
    val itemDetails: ItemDetails = ItemDetails()
)

data class EventDetailsUiState(
    val eventDetails: AddEventDetails = AddEventDetails(),
    val itemList: List<ItemUiState> = emptyList()
)

fun ShoppingItem.toItemDetails() : ItemDetails = ItemDetails(
    itemId = itemId,
    eventId = eventId,
    name = itemName,
    price = price.toString(),
    quantity = quantity.toString()
)

fun ItemDetails.toShoppingItem() : ShoppingItem = ShoppingItem(
    itemId = itemId,
    eventId = eventId,
    itemName = name,
    price = price.toDoubleOrNull() ?: 0.0,
    quantity = quantity.toDoubleOrNull() ?: 1.0
)