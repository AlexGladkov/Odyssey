package ru.alexgladkov.common.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.alexgladkov.common.compose.theme.Odyssey
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
fun TabScreen(count: Int?) {
    val rootController = LocalRootController.current
    val modalController = rootController.findModalController()

    Column {
        CounterView(count)

        Box(
            modifier = Modifier.background(Odyssey.color.primaryBackground).fillMaxSize()
        ) {
            LazyColumn {
                item {
                    ActionCell(text = "Push Screen", icon = Icons.Filled.ArrowForward) {
                        rootController.push("tab", (count ?: 0) + 1)
                    }
                }

                item {
                    ActionCell("Present Flow", icon = Icons.Filled.ArrowUpward) {
                        rootController.findRootController().present("present")
                    }
                }
            }
        }
    }
}