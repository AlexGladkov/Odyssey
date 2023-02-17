package ru.alexgladkov.common.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.alexgladkov.odyssey.compose.base.TabNavigator
import ru.alexgladkov.odyssey.compose.controllers.MultiStackRootController
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
fun DrawerScreen(params: Any?) {
    val rootController = LocalRootController.current as MultiStackRootController
    val tabItem = rootController.stackChangeObserver.collectAsState().value ?: return
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState(
        drawerState = rememberDrawerState(DrawerValue.Open)
    )

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.DarkGray)
            ) {
                params?.toString()?.let { title ->
                    Text(
                        modifier = Modifier.height(40.dp).padding(10.dp).fillMaxWidth(),
                        text = title,
                        textAlign = TextAlign.Start,
                        color = Color.White
                    )
                }

                rootController.tabItems.forEach { currentItem ->
                    val position = rootController.tabItems.indexOf(currentItem)

                    Text(
                        modifier = Modifier.height(40.dp).fillMaxWidth()
                            .clickable {
                                coroutineScope.launch {
                                    rootController.switchTab(position)
                                    scaffoldState.drawerState.close()
                                }
                            },
                        text = currentItem.tabInfo.tabItem.name,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }

            }
        }
    ) {
        TabNavigator(modifier = Modifier.fillMaxSize(), null, tabItem)
    }


    rootController.tabsNavModel.launchedEffect.invoke()
}