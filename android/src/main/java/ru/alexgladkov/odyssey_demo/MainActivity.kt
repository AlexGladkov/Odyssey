package ru.alexgladkov.odyssey_demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.alexgladkov.common.compose.NavigationTree
import ru.alexgladkov.common.compose.navigation.buildComposeNavigationGraph
import ru.alexgladkov.odyssey.compose.AndroidScreenHost
import ru.alexgladkov.odyssey.compose.setupActivityWithRootController

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidScreenHost(this)
            .setupActivityWithRootController(
                startScreen = NavigationTree.Root.Splash.toString(),
                block = buildComposeNavigationGraph()
            )
    }
}