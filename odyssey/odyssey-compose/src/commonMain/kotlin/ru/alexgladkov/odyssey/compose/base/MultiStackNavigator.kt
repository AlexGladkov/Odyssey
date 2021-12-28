package ru.alexgladkov.odyssey.compose.base

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.alexgladkov.odyssey.compose.controllers.MultiStackRootController
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.TabItem
import ru.alexgladkov.odyssey.core.extensions.Closeable

@Composable
fun MultiStackNavigator(startParams: Any?) {
    val rootController = LocalRootController.current as MultiStackRootController
    var tabItem: TabItem? by remember { mutableStateOf(null) }
    var stackCloseable: Closeable? = null

    Column {
        Text(modifier = Modifier.fillMaxWidth().weight(1f), text = "Hello, ${tabItem?.configuration?.title}")

        val bottomNavConfiguration = rootController.bottomNavModel.bottomNavConfiguration
        BottomNavigation(
            backgroundColor = bottomNavConfiguration.backgroundColor
        ) {
            rootController.tabList.forEach { currentItem ->
                BottomNavigationItem(
                    selected = tabItem == currentItem,
                    selectedContentColor = currentItem.configuration.selectedColor
                        ?: bottomNavConfiguration.selectedColor,
                    unselectedContentColor = currentItem.configuration.unselectedColor
                        ?: bottomNavConfiguration.unselectedColor,
                    icon = {
                        currentItem.configuration.icon?.let {
                            Icon(
                                it,
                                contentDescription = currentItem.configuration.title
                            )
                        }
                    },
                    label = {
                        Text(
                            text = currentItem.configuration.title,
                            style = currentItem.configuration.titleStyle ?: LocalTextStyle.current
                        )
                    },
                    onClick = {
                        rootController.switchTab(currentItem)
                    })
            }
        }
    }

    LaunchedEffect(Unit) {
        stackCloseable = rootController.stackChangeObserver.watch {
            tabItem = it
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            stackCloseable?.close()
        }
    }
}