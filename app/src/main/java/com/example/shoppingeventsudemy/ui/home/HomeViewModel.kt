package com.example.shoppingeventsudemy.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingeventsudemy.data.entities.ShoppingEvent
import com.example.shoppingeventsudemy.data.repos.ShoppingEventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val shoppingEventRepository: ShoppingEventRepository
) : ViewModel(){
    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState = _homeUiState.asStateFlow()

    init {
        viewModelScope.launch{
           shoppingEventRepository.getEvents()
               .collect {events ->
                   _homeUiState.update {
                       it.copy(events = events)
                   }
               }
        }
    }

    suspend fun deleteEvent(event: ShoppingEvent) {
        shoppingEventRepository.delete(event)
    }
}