package ru.alexgladkov.odyssey_demo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import ru.alexgladkov.common.compose.NavigationTree
import ru.alexgladkov.common.compose.navigation.navigationGraph
import ru.alexgladkov.common.compose.theme.Odyssey
import ru.alexgladkov.common.compose.theme.OdysseyTheme
import ru.alexgladkov.common.compose.theme.colors.OdysseyColors
import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration
import ru.alexgladkov.odyssey.compose.setup.setNavigationContent
import ru.alexgladkov.odyssey_demo.theme.setupThemedNavigation

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            OdysseyTheme {
                val configuration = OdysseyConfiguration(
                    canvas = this,
                    backgroundColor = Odyssey.color.primaryBackground
                )

                setNavigationContent(configuration) {
                    navigationGraph()
                }
            }
        }
    }
}
