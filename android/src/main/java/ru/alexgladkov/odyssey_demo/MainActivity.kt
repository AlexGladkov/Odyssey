package ru.alexgladkov.odyssey_demo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.toArgb
import ru.alexgladkov.common.compose.navigation.navigationGraph
import ru.alexgladkov.common.compose.theme.Odyssey
import ru.alexgladkov.common.compose.theme.OdysseyTheme
import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration
import ru.alexgladkov.odyssey.compose.setup.setNavigationContent
import ru.alexgladkov.odyssey.core.configuration.DisplayType

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            OdysseyTheme {
                val configuration = OdysseyConfiguration(
                    canvas = this,
                    displayType = DisplayType.FullScreen(Odyssey.color.primaryBackground),
                    backgroundColor = Odyssey.color.primaryBackground,
                    navigationBarColor = Odyssey.color.primaryBackground.toArgb()
                )

                setNavigationContent(configuration) {
                    navigationGraph()
                }
            }
        }
    }
}
