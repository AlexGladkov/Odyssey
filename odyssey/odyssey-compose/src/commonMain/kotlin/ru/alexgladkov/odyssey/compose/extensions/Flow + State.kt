package ru.alexgladkov.odyssey.compose.extensions

import androidx.compose.runtime.*
import ru.alexgladkov.odyssey.core.extensions.CFlow
import ru.alexgladkov.odyssey.core.extensions.Closeable

@Composable
fun <T> CFlow<T>.observeAsState(initial: T? = null): State<T?> {
    val state = remember { mutableStateOf(initial) }
    val flow = this
    DisposableEffect(this) {
        val observer = flow.watch { state.value = it }
        onDispose {
            observer.close()
        }
    }

    return state
}

@Composable
fun <T, R> CFlow<T>.launchAsState(key: R?, initial: T): T {
    var navigation by remember(key) { mutableStateOf(initial) }
    var observer: Closeable? = null
    val flow = this

    LaunchedEffect(key) {
        observer = flow.watch {
            it?.let {
                navigation = it
            }
        }
    }

    DisposableEffect(key) {
        onDispose {
            observer?.close()
        }
    }

    return navigation
}