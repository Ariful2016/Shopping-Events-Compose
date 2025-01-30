package com.example.shoppingeventsudemy.ui.eventdetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.shoppingeventsudemy.data.entities.ShoppingItem
import com.example.shoppingeventsudemy.data.repos.ShoppingEventRepository
import com.example.shoppingeventsudemy.data.repos.ShoppingItemRepository
import com.example.shoppingeventsudemy.destinations.EventDetailsRoute
import com.example.shoppingeventsudemy.ui.addevent.AddEventDetails
import com.example.shoppingeventsudemy.ui.addevent.toAddEventDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val shoppingEventRepository: ShoppingEventRepository,
    private val itemRepository: ShoppingItemRepository,
) : ViewModel(){
    private val detailsRoute : EventDetailsRoute = savedStateHandle.toRoute<EventDetailsRoute>()

    private val _eventDetailsUiState = MutableStateFlow(EventDetailsUiState())
    val eventDetailsUiState = _eventDetailsUiState.asStateFlow()

    init {
        viewModelScope.launch {
            shoppingEventRepository.getEventAndItems(detailsRoute.eventId).collect {map ->
                Log.d("EventDetailsViewModel", map.toString())
                val entry = map.entries.firstOrNull()
                _eventDetailsUiState.update {
                    it.copy(
                        eventDetails = entry?.key?.toAddEventDetails() ?: AddEventDetails(name = detailsRoute.eventName),
                        itemList = entry?.value?.map {item->
                            ItemUiState(itemDetails = item.toItemDetails())
                        } ?: emptyList()
                    )
                }
            }
        }
    }

    fun updateItemUiState(itemDetails: ItemDetails) {
        _eventDetailsUiState.update {state ->
            state.copy(itemList = state.itemList.map {
                if (it.itemDetails.itemId == itemDetails.itemId) {
                    it.copy(itemDetails = itemDetails)
                } else {
                    it
                }
            })
        }
    }

    fun enableEditMode(itemDetails: ItemDetails) {
        _eventDetailsUiState.update {state ->
            state.copy(
                itemList = state.itemList.map {
                    if (it.itemDetails.itemId == itemDetails.itemId) {
                        it.copy(isEdit = true)
                    } else {
                        it
                    }
                }
            )
        }
    }

    suspend fun addItem() {
        val item = ShoppingItem(eventId = detailsRoute.eventId, itemName = "Item")
        itemRepository.insert(item)
    }

    suspend fun updateItem(itemDetails: ItemDetails) {
        itemRepository.update(itemDetails.toShoppingItem())
    }

    suspend fun deleteShoppingItem(details: ItemDetails) {
        itemRepository.delete(details.toShoppingItem())
    }
}