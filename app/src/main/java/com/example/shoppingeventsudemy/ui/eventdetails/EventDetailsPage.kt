package com.example.shoppingeventsudemy.ui.eventdetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppingeventsudemy.customcomposables.DismissibleItem
import com.example.shoppingeventsudemy.customcomposables.EditListItem
import com.example.shoppingeventsudemy.customcomposables.EmptyListUi
import com.example.shoppingeventsudemy.customcomposables.ShoppingAppBar
import com.example.shoppingeventsudemy.ui.addevent.AddEventDetails
import kotlinx.coroutines.launch

@Composable
fun EventDetailsPage(
    navigateBack: () -> Unit,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EventDetailsViewModel = hiltViewModel()) {
    val uiState by viewModel.eventDetailsUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    Scaffold(
        topBar = {
            ShoppingAppBar(
                title = uiState.eventDetails.name,
                canNavigateBack = true,
                navigateUp = navigateUp
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                   coroutineScope.launch {
                       viewModel.addItem()
                       if (uiState.itemList.isNotEmpty()) {
                           listState.animateScrollToItem(uiState.itemList.size - 1)
                       }
                   }
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Text(text = "Add Item")
            }
        }
    ) { innerPadding ->
        if (uiState.itemList.isEmpty()) {
            EmptyListUi(
                message = "No items\nAdd an item to get started",
                modifier = modifier.padding(innerPadding)
            )
            return@Scaffold
        }

        ShoppingItemList(
            eventDetails = uiState.eventDetails,
            items = uiState.itemList,
            lazyListState = listState,
            onValueChange = viewModel::updateItemUiState,
            onEditModeChanged = viewModel::enableEditMode,
            onItemUpdate = {
                coroutineScope.launch {
                    viewModel.updateItem(it)
                }
            },
            onDeleteItem = {
                coroutineScope.launch {
                    viewModel.deleteShoppingItem(it)
                }
            },
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Composable
fun ShoppingItemList(
    eventDetails: AddEventDetails,
    items: List<ItemUiState>,
    onValueChange: (ItemDetails) -> Unit,
    onItemUpdate: (ItemDetails) -> Unit,
    lazyListState: LazyListState,
    onDeleteItem: (ItemDetails) -> Unit,
    onEditModeChanged: (ItemDetails) -> Unit,
    modifier: Modifier = Modifier) {

    LazyColumn(
        state = lazyListState,
        modifier = modifier) {
        item {
            ListItem(
                colors = ListItemDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                headlineContent = {
                    Text("Budget: \$${eventDetails.initialBudget}", style = MaterialTheme.typography.bodyLarge)
                },
                trailingContent = {
                    Text("\$${eventDetails.totalCost}", style = MaterialTheme.typography.bodyLarge)
                }
            )
        }
        items(items, key = {
            it.itemDetails.itemId
        }) {itemUiState ->
            SingleItemView(
                itemUiState = itemUiState,
                onValueChange = onValueChange,
                onItemUpdate = onItemUpdate,
                onEditModeChanged = onEditModeChanged,
                onDeleteItem = onDeleteItem
            )
        }

        item {
            Spacer(modifier = Modifier.height(70.dp))
        }
    }
}

@Composable
fun SingleItemView(
    itemUiState: ItemUiState,
    onValueChange: (ItemDetails) -> Unit,
    onItemUpdate: (ItemDetails) -> Unit,
    onDeleteItem: (ItemDetails) -> Unit,
    onEditModeChanged: (ItemDetails) -> Unit,
    modifier: Modifier = Modifier) {
    val item = itemUiState.itemDetails
    if (itemUiState.isEdit) {
        EditListItem(
            itemDetails = item,
            onValueChange = onValueChange,
            onItemUpdate = onItemUpdate,
            modifier = modifier
        )
    } else {
        DismissibleItem(
            onDelete = {
                onDeleteItem(item)
            }
        ) {
            ListItem(
                headlineContent = {
                    Text(itemUiState.itemDetails.name)
                },
                supportingContent = {
                    Text("Quantity: ${itemUiState.itemDetails.quantity}")
                },
                trailingContent = {
                    Text(
                        "\$${itemUiState.itemDetails.price}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                leadingContent = {
                    IconButton(
                        onClick = {
                            onEditModeChanged(item)
                        }
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = null)
                    }
                }
            )
        }
    }
}