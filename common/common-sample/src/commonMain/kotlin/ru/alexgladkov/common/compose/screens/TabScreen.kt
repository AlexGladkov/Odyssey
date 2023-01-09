package ru.alexgladkov.common.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.alexgladkov.common.compose.NavigationTree
import ru.alexgladkov.common.compose.theme.Odyssey
import ru.alexgladkov.odyssey.compose.controllers.MultiStackRootController
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import kotlin.random.Random

@Composable
fun TabScreen(count: Int?) {
    val rootController = LocalRootController.current

    Column {
        CounterView(count)

        Box(
            modifier = Modifier.background(Odyssey.color.primaryBackground).fillMaxSize()
        ) {
            LazyColumn {
                item {
                    ActionCell(text = "Push Screen", icon = Icons.Filled.ArrowForward) {
                        rootController.push(NavigationTree.Tab.name, (count ?: 0) + 1)
                    }
                }

                item {
                    ActionCell("Present Flow", icon = Icons.Filled.KeyboardArrowUp) {
                        rootController.findRootController().present(NavigationTree.TabPresent.name)
                    }
                }

                item {
                    ActionCell("Switch Tab", icon = Icons.Filled.KeyboardArrowRight) {
                        val parent = rootController.findHostController()
                        parent?.switchTab(2)
                    }
                }
            }
        }
    }
}