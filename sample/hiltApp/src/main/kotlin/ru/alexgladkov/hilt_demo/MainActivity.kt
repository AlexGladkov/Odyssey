package ru.alexgladkov.hilt_demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import ru.alexgladkov.odyssey.android.hilt.builder.hiltScreen
import ru.alexgladkov.odyssey.android.hilt.setHiltNavigation
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration
import ru.alexgladkov.hilt_demo.legacy.ui.HiltScreenOne
import ru.alexgladkov.hilt_demo.legacy.ui.HiltScreenTwo

/**
 * Credits: @puritanin
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val configuration = OdysseyConfiguration(this)
            setHiltNavigation(configuration) {
                generateGraph()
            }
        }
    }
}

private fun RootComposeBuilder.generateGraph(): RootComposeBuilder {
    hiltScreen(name = "one") {
        HiltScreenOne()
    }
    hiltScreen(name = "two") {
        HiltScreenTwo()
    }

    return this
}