package ru.alexgladkov.odyssey_demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.alexgladkov.common.compose.navigation.generateGraph
import ru.alexgladkov.odyssey.core.extensions.setupNavigation

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupNavigation("start") {
            generateGraph()
        }
    }
}
