package ru.alexgladkov.odyssey_demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.alexgladkov.common.compose.NavigationTree
import ru.alexgladkov.common.compose.navigation.generateNavigationGraph
import ru.alexgladkov.odyssey.core.RootController
import ru.alexgladkov.odyssey.core.extensions.setupWithActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val screenHost = AppScreenHost(this)
        val rootController = RootController(screenHost)
        rootController.generateNavigationGraph()
        rootController.setupWithActivity(this)
        screenHost.prepareFowDrawing()
        rootController.launch(NavigationTree.Root.Splash.toString())
    }
}