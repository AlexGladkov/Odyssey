package ru.alexgladkov.odyssey_demo

import androidx.compose.ui.window.Application
import platform.UIKit.UIViewController
import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration
import ru.alexgladkov.odyssey.compose.setup.setNavigationContent
import ru.alexgladkov.shared.navigation.navigationGraph
import ru.alexgladkov.shared.theme.Odyssey
import ru.alexgladkov.shared.theme.OdysseyTheme

fun MainViewController(): UIViewController =
    Application("JetHabit") {

        OdysseyTheme {
            val configuration = OdysseyConfiguration(
                backgroundColor = Odyssey.color.primaryBackground
            )

            setNavigationContent(configuration) {
                navigationGraph()
            }
        }
    }