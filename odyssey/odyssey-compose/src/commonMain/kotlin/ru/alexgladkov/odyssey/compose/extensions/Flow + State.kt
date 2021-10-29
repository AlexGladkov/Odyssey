package ru.alexgladkov.odyssey.compose.extensions

import androidx.compose.runtime.*
import ru.alexgladkov.odyssey.core.extensions.CFlow

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