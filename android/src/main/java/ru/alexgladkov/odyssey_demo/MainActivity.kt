package ru.alexgladkov.odyssey_demo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.ExperimentalComposeUiApi
import kotlinx.coroutines.CoroutineScope
import ru.alexgladkov.common.compose.NavigationTree
import ru.alexgladkov.common.compose.navigation.navigationGraph
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey_demo.theme.setupThemedNavigation

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupThemedNavigation(NavigationTree.Actions.name) { navigationGraph() }
    }
}

fun RootController.setupAnalytics(coroutineScope: CoroutineScope) {
    onBreadcrumb = {
        val realKey = when {
            it.startsWith(RootController.multiStackKey) -> it.substringAfter(RootController.multiStackKey + "$") // started multistack
            else -> it
        }
        Log.d("NAVI", "onBreadcrumb [$realKey]")
        //Log.d("NAVI", "onBreadcrumb [$realKey]", Exception())
    }
    /*coroutineScope.launch {
        currentScreen.filterNotNull().collect { nav ->
            Log.d("NAVI", "transition [${nav.screenToRemove?.realKey}] -> [${nav.screen.realKey}]")
        }
    }*/
    /*coroutineScope.launch {
        var prev = listOf<ModalBundle>()
        findModalController().currentStack.distinctUntilChanged().collect { curr ->
            if (prev.size >= curr.size && prev.isNotEmpty()) {
                Log.d("NAVI", "pop modal")
            }
            if (prev.size <= curr.size && curr.isNotEmpty()) {
                val stack = curr.joinToString { it.key }
                Log.d("NAVI", "push modal [$stack]")
                //Log.d("NAVI", "push modal [${curr.lastOrNull()?.key}]")
            }
        }
    }*/
}