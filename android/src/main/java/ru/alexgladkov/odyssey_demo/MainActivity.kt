package ru.alexgladkov.odyssey_demo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ru.alexgladkov.common.compose.navigation.generateGraph
import ru.alexgladkov.odyssey.compose.setupNavigation

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupNavigation("start") {
            generateGraph()
        }
    }
}
