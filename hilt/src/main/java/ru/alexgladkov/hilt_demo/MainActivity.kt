package ru.alexgladkov.hilt_demo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.CompositionLocalProvider
import dagger.hilt.android.AndroidEntryPoint
import ru.alexgladkov.hilt_demo.ui.HiltScreenOne
import ru.alexgladkov.hilt_demo.ui.HiltScreenTwo
import ru.alexgladkov.odyssey.android.hilt.builder.hiltScreen
import ru.alexgladkov.odyssey.android.hilt.local.LocalHiltViewModelStoreOwnerManager
import ru.alexgladkov.odyssey.android.hilt.setHiltNavigation
import ru.alexgladkov.odyssey.android.hilt.viewmodel.HiltViewModelStoreOwnerManager
import ru.alexgladkov.odyssey.compose.base.Navigator
import ru.alexgladkov.odyssey.compose.extensions.setupWithActivity
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration

/**
 * Credits: @puritanin
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val configuration = OdysseyConfiguration(this)
            setHiltNavigation(configuration) {
                generateGraph()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun RootComposeBuilder.generateGraph(): RootComposeBuilder {
    hiltScreen(name = "one") {
        HiltScreenOne()
    }
    hiltScreen(name = "two") {
        HiltScreenTwo()
    }

    return this
}
