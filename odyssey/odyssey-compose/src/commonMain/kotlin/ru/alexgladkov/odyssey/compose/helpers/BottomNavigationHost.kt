package ru.alexgladkov.odyssey.compose.helpers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.alexgladkov.odyssey.compose.extensions.observeAsState
import ru.alexgladkov.odyssey.core.controllers.MultiStackRootController

@Composable
fun BottomNavigationHost(
    screenBundle: ScreenBundle,
    selectedColor: Color = Color.Black,
    unselectedColor: Color = Color.DarkGray,
    backgroundColor: Color = Color.White
) {
    val multiStackRootController = screenBundle.rootController as MultiStackRootController
    val state = multiStackRootController.backStackObserver.observeAsState()

    Column(modifier = Modifier.fillMaxSize().background(backgroundColor)) {
        Box(modifier = Modifier.weight(1f)) {
            state.value?.let { entry ->
                FlowHost(ScreenBundle(rootController = entry.rootController, screenMap = screenBundle.screenMap))
            }
        }

        BottomNavigation(
            backgroundColor = backgroundColor
        ) {
            multiStackRootController.childrenRootController.forEach { flowRootController ->
                val position = multiStackRootController.childrenRootController.indexOf(flowRootController)
                val isSelected = state.value?.rootController == flowRootController
                val bottomItemModel = multiStackRootController.childrenRootController[position]

                BottomNavigationItem(
                    modifier = Modifier.background(backgroundColor),
                    selected = isSelected,
                    icon = {},
                    onClick = {
                        multiStackRootController.switchFlow(position, flowRootController)
                    },
                    label = {
                        Text(
                            text = bottomItemModel.debugName.toString(),
                            color = if (isSelected) selectedColor else unselectedColor
                        )
                    }
                )
            }
        }
    }
}