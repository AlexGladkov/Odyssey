package ru.alexgladkov.hilt_demo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.CompositionLocalProvider
import dagger.hilt.android.AndroidEntryPoint
import ru.alexgladkov.hilt_demo.ui.HiltScreenOne
import ru.alexgladkov.hilt_demo.ui.HiltScreenTwo
import ru.alexgladkov.odyssey.android.hilt.builder.hiltScreen
import ru.alexgladkov.odyssey.android.hilt.local.LocalHiltViewModelStoreOwnerManager
import ru.alexgladkov.odyssey.android.hilt.viewmodel.HiltViewModelStoreOwnerManager
import ru.alexgladkov.odyssey.compose.base.Navigator
import ru.alexgladkov.odyssey.compose.extensions.setupWithActivity
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder

/**
 * Credits: @puritanin
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

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
                Navigator(startScreen = "one")
            }
        }
    }
}

private fun RootComposeBuilder.generateGraph(): RootComposeBuilder {
    hiltScreen(name = "one") {
        HiltScreenOne()
    }
    hiltScreen(name = "two") {
        HiltScreenTwo()
    }

    return this
}
