package com.example.shoppingeventsudemy.customcomposables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun EmptyListUi(
    message: String,
    modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize()
    ) {
        Text(message, textAlign = TextAlign.Center)
    }
}