package ru.alexgladkov.odyssey_demo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import ru.alexgladkov.common.compose.RootContainer
import ru.alexgladkov.odyssey.core.RootController
import ru.alexgladkov.odyssey.core.extensions.setupWithActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootController = RootController()
        rootController.debugName = "Global"
        rootController.setupWithActivity(this)

        setContent {
            RootContainer(
                rootController = rootController,
            )
        }
    }
}