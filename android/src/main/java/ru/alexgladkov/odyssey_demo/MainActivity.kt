package ru.alexgladkov.odyssey_demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import navigation.mainScreen
import ru.alexgladkov.common.compose.navigation.customNavScreen
import ru.alexgladkov.common.compose.navigation.topNavScreen
import ru.alexgladkov.common.compose.screens.ActionsScreen
import ru.alexgladkov.common.compose.screens.PresentedActionsScreen
import ru.alexgladkov.odyssey.compose.extensions.flow
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey_demo.theme.setupThemedNavigation

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupThemedNavigation("actions") {
            screen("actions") {
                ActionsScreen(count = 0)
            }

            screen("push") {
                ActionsScreen(count = it as? Int)
            }

            flow("present") {
                screen("present_screen") {
                    PresentedActionsScreen(count = (it as? Int) ?: 0)
                }
            }

            mainScreen()
            topNavScreen()
            customNavScreen()
        }
    }
}
