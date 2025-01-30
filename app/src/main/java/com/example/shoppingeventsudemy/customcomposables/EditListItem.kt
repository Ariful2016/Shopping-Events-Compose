package com.example.shoppingeventsudemy.customcomposables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.shoppingeventsudemy.ui.eventdetails.ItemDetails

@Composable
fun EditListItem(
    itemDetails: ItemDetails,
    onValueChange: (ItemDetails) -> Unit,
    onItemUpdate: (ItemDetails) -> Unit,
    modifier: Modifier = Modifier) {

    ListItem(
        modifier = modifier,
        headlineContent = {
            OutlinedTextField(
                value = itemDetails.name,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                onValueChange = {
                    onValueChange(itemDetails.copy(name = it))
                },
                label = { Text("Item Name") },
                modifier = Modifier.fillMaxWidth().padding(4.dp)
            )
        },
        supportingContent = {
            Row {
                OutlinedTextField(
                    value = itemDetails.quantity,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    onValueChange = {
                        onValueChange(itemDetails.copy(quantity = it))
                    },
                    label = { Text("Quantity") },
                    modifier = Modifier.weight(1f).padding(4.dp)
                )
                OutlinedTextField(
                    value = itemDetails.price,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    onValueChange = {
                        onValueChange(itemDetails.copy(price = it))
                    },
                    label = { Text("Price") },
                    modifier = Modifier.weight(1f).padding(4.dp)
                )
            }
        },
        trailingContent = {
            IconButton(
                onClick = {
                    onItemUpdate(itemDetails)
                }
            ) {
                Icon(Icons.Filled.Done, contentDescription = null)
            }
        }
    )
}