package com.example.shoppingeventsudemy.ui.addevent

import android.media.metrics.Event
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoppingeventsudemy.customcomposables.ShoppingAppBar
import com.example.shoppingeventsudemy.utils.formatDate
import kotlinx.coroutines.launch

@Composable
fun AddEventPage(
    navigateBack: () -> Unit,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddEventViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            ShoppingAppBar(
                title = "Add Event",
                canNavigateBack = true,
                navigateUp = navigateUp
            )
        }
    ) { innerPadding ->
        EventForm(
            uiState = viewModel.addEventUiState,
            onEventValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch{
                    viewModel.saveEvent()
                    navigateBack()
                }
            },
            modifier = modifier.padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventForm(
    uiState: AddEventUiState,
    onEventValueChange: (AddEventDetails) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var openDatePickerDialog by remember {
        mutableStateOf(false)
    }
    val datePickerState = rememberDatePickerState()
    Column(
        modifier = modifier
            .padding(8.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextInputFields(
            addEventDetails = uiState.addEventDetails,
            onEventValueChange = onEventValueChange
        )

        DatePickerUi(
            shouldOpenDialog = openDatePickerDialog,
            state = datePickerState,
            onDismissRequest = {
                openDatePickerDialog = false
            },
            onClickConfirmButton = {
                datePickerState.selectedDateMillis?.let {
                    onEventValueChange(uiState.addEventDetails.copy(
                        eventDate = formatDate(it)!!
                    ))
                }
                openDatePickerDialog = false
            },
            onClickCancelButton = {
                openDatePickerDialog = false
            }
        )

        DatePickerButtonUi(
            state = datePickerState,
            onSelectDateButtonClicked = {
                openDatePickerDialog = true
            }
        )

        Button(
            onClick = onSaveClick,
            enabled = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth().padding(20.dp)
        ) {
            Text("SAVE")
        }
    }
}

@Composable
fun TextInputFields(
    addEventDetails: AddEventDetails,
    onEventValueChange: (AddEventDetails) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = addEventDetails.name,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
            ),
            onValueChange = {
                onEventValueChange(addEventDetails.copy(name = it))
            },
            label = {
                Text(text = "Event Name")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        OutlinedTextField(
            value = addEventDetails.initialBudget,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
            ),
            onValueChange = {
                onEventValueChange(addEventDetails.copy(initialBudget = it))
            },
            label = {
                Text(text = "Initial Budget (optional)")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerUi(
    shouldOpenDialog: Boolean,
    state: DatePickerState,
    onDismissRequest: () -> Unit,
    onClickConfirmButton: () -> Unit,
    onClickCancelButton: () -> Unit,
    modifier: Modifier = Modifier
) {

    if (shouldOpenDialog) {
        val confirmEnabled by remember {
            derivedStateOf { state.selectedDateMillis != null }
        }

        DatePickerDialog(
            modifier = modifier,
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(
                    enabled = confirmEnabled,
                    onClick = onClickConfirmButton,
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onClickCancelButton,
                ) {
                    Text("CANCEL")
                }
            }
        ) {
            DatePicker(
                state = state
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerButtonUi(
    state: DatePickerState,
    onSelectDateButtonClicked: () -> Unit,
    modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ElevatedButton(
            onClick = onSelectDateButtonClicked
        ) {
            Text("Select Date")
        }

        Text(text = formatDate(state.selectedDateMillis) ?: "Nothing selected")
    }
}

@Preview(showBackground = true)
@Composable
private fun EventFormPreview() {
    EventForm(
        uiState = AddEventUiState(),
        onEventValueChange = {},
        onSaveClick = {}
    )
}