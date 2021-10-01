package ru.alexgladkov.common.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.alexgladkov.common.compose.`compose-extension`.*
import ru.alexgladkov.common.compose.screens.ContainerFlow
import ru.alexgladkov.odyssey.core.RootController
import ru.alexgladkov.odyssey.compose.RootHost
import ru.alexgladkov.odyssey.compose.extensions.destination

@Composable
fun RootContainer(
    rootController: RootController
) {
    RootHost(startScreen = NavigationTree.Root.Start, rootController = rootController) {
        destination(screen = NavigationTree.Root.Start) {
            Surface(modifier = Modifier.fillMaxSize()) {
                Button(
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    onClick = {
                        rootController.launch(NavigationTree.Root.Container)
                    }
                ) {
                    Text("Start Journey")
                }
            }
        }

        flow(
            screen = NavigationTree.Root.Container,
            content = {
                val parentRoot = this
                ComposeRenderable(
                    render = {
                        ContainerFlow(parentRoot)
                    }
                )
            }
        )
    }
}