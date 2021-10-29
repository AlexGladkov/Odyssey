package ru.alexgladkov.odyssey.compose.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ru.alexgladkov.odyssey.compose.helpers.BottomItemModel
import ru.alexgladkov.odyssey.compose.helpers.BottomNavigationColors
import ru.alexgladkov.odyssey.compose.helpers.BottomNavigationHost
import ru.alexgladkov.odyssey.compose.helpers.ScreenBundle
import ru.alexgladkov.odyssey.compose.navigation.ComposeFlowBuilder
import ru.alexgladkov.odyssey.compose.navigation.ComposeMultiStackBuilder
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.core.destination.DestinationScreen

fun RootComposeBuilder.screen(name: String, content: @Composable ScreenBundle.() -> Unit) {
    addDestination(destination = DestinationScreen(name), screenMap = hashMapOf(name to content))
}

fun RootComposeBuilder.flow(name: String, block: ComposeFlowBuilder.() -> Unit) {
    val builder = ComposeFlowBuilder(name)
    val destinationFlow = builder.apply(block).build()

    addDestination(screenMap = builder.screenMap, destination = destinationFlow)
}

fun RootComposeBuilder.multistack(
    name: String,
    host: @Composable ScreenBundle.() -> Unit,
    block: ComposeMultiStackBuilder.() -> Unit
) {
    val builder = ComposeMultiStackBuilder(name)
    val destinationMultiFlow = builder.apply(block).build()

    addScreenValue(name = name, content = host)
    addDestination(screenMap = builder.screenMap, destination = destinationMultiFlow)
}

fun RootComposeBuilder.bottomNavigation(
    name: String,
    selectedColor: Color = Color.Black,
    unselectedColor: Color = Color.DarkGray,
    backgroundColor: Color = Color.White,
    bottomItemModels: List<BottomItemModel>,
    block: ComposeMultiStackBuilder.() -> Unit
) {
    val builder = ComposeMultiStackBuilder(name)
    val destinationMultiFlow = builder.apply(block).build()

    addScreenValue(name = name, content = {
        BottomNavigationHost(
            screenBundle = this,
            bottomNavigationColors = BottomNavigationColors(
                selectedColor = selectedColor,
                unselectedColor = unselectedColor,
                backgroundColor = backgroundColor
            ),
            bottomItemModels = bottomItemModels
        )
    })
    addDestination(screenMap = builder.screenMap, destination = destinationMultiFlow)
}