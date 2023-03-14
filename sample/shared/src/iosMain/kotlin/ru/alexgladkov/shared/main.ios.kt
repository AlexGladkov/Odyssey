package ru.alexgladkov.shared

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController =
    ComposeUIViewController {
        MainView(platformConfiguration = PlatformConfiguration())
    }