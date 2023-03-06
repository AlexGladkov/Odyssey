package ru.alexgladkov.shared.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.alexgladkov.shared.screens.ActionCell
import ru.alexgladkov.shared.theme.Odyssey

@Composable
fun AlertDialogScreen(onCloseClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(Odyssey.color.primaryBackground)) {
        Text(modifier = Modifier.padding(16.dp), text = "Modal Sheet", fontSize = 24.sp, color = Odyssey.color.primaryText)
        ActionCell(text = "Close", icon = Icons.Filled.Close) {
            onCloseClick.invoke()
        }
    }
}