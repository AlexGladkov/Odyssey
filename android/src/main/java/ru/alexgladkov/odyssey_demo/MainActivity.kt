package ru.alexgladkov.odyssey_demo

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Text
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.view.WindowCompat
import ru.alexgladkov.common.compose.NavigationTree
import ru.alexgladkov.common.compose.navigation.customNavScreen
import ru.alexgladkov.common.compose.navigation.mainScreen
import ru.alexgladkov.common.compose.navigation.navigationGraph
import ru.alexgladkov.common.compose.navigation.topNavScreen
import ru.alexgladkov.common.compose.screens.ActionsScreen
import ru.alexgladkov.common.compose.screens.PresentedActionsScreen
import ru.alexgladkov.odyssey.compose.extensions.flow
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey_demo.theme.setupThemedNavigation

class MainActivity : AppCompatActivity() {

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupThemedNavigation(NavigationTree.Actions.name) { navigationGraph() }
    }
}
