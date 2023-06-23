package ru.alexgladkov.odyssey_demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.alexgladkov.shared.MainView
import ru.alexgladkov.shared.PlatformConfiguration

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val platformConfiguration = PlatformConfiguration(this)
            MainView(platformConfiguration)
        }
    }
}
