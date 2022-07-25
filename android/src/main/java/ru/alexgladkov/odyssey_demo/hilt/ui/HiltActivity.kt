package ru.alexgladkov.odyssey_demo.hilt.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.CompositionLocalProvider
import dagger.hilt.android.AndroidEntryPoint
import ru.alexgladkov.odyssey.compose.base.Navigator
import ru.alexgladkov.odyssey.compose.extensions.setupWithActivity
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey_demo.hilt.HiltViewModelStoreOwnerManager
import ru.alexgladkov.odyssey_demo.hilt.LocalHiltViewModelStoreOwnerManager
import ru.alexgladkov.odyssey_demo.hilt.hiltScreen

@AndroidEntryPoint
class HiltActivity : AppCompatActivity() {

    private fun RootComposeBuilder.generateGraph(): RootComposeBuilder {
        hiltScreen(name = "one") {
            HiltScreenOne()
        }
        hiltScreen(name = "two") {
            HiltScreenTwo()
        }
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootController = RootComposeBuilder().generateGraph().build()
        rootController.setupWithActivity(this)

        val storeOwnerManager = HiltViewModelStoreOwnerManager(
            context = this,
            rootController = rootController
        )

        setContent {
            CompositionLocalProvider(
                LocalHiltViewModelStoreOwnerManager provides storeOwnerManager,
                LocalRootController provides rootController
            ) {
                Navigator("one")
            }
        }
    }
}
