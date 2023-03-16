package ru.alexgladkov.shared.hilt

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.shared.screens.ActionCell
import ru.alexgladkov.shared.theme.Odyssey

@Composable
fun HiltDemoScreen(
    onClick: () -> Unit
) {
    val rootController = LocalRootController.current

    Box(
        modifier = Modifier.background(Odyssey.color.primaryBackground).fillMaxSize()
    ) {
        LazyColumn {
            item {
                ActionCell(text = "Start Hilt demo activity", icon = Icons.Filled.ArrowForward) {
                    onClick()
                }
            }

            item {
                ActionCell(text = "Back", icon = Icons.Filled.ArrowBack) {
                    rootController.popBackStack()
                }
            }
        }
    }
}