package ru.alexgladkov.odyssey_demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.toArgb
import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration
import ru.alexgladkov.odyssey.compose.setup.setNavigationContent
import ru.alexgladkov.odyssey.core.configuration.DisplayType
import ru.alexgladkov.shared.navigation.navigationGraph
import ru.alexgladkov.shared.theme.Odyssey
import ru.alexgladkov.shared.theme.OdysseyTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            OdysseyTheme {
                val configuration = OdysseyConfiguration(
                    canvas = this,
                    displayType = DisplayType.EdgeToEdge,
                    backgroundColor = Odyssey.color.primaryBackground,
                    navigationBarColor = Odyssey.color.primaryBackground.toArgb(),
                    breadcrumbCallback = { breadcrumb ->

                    }
                )

                setNavigationContent(configuration) {
                    navigationGraph()
                }
            }
        }
    }
}
