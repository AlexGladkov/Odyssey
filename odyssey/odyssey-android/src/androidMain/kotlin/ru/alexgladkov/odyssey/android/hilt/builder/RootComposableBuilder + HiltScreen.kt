package ru.alexgladkov.odyssey.android.hilt.builder

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import ru.alexgladkov.odyssey.android.hilt.local.LocalHiltViewModelStoreOwnerManager

//@ExperimentalFoundationApi
//fun RootComposeBuilder.hiltScreen(
//    name: String,
//    content: RenderWithParams<Any?>
//) {
//    screen(
//        name = name,
//        content = {
//            WrapHiltViewModelNavigation(name) {
//                content(it)
//            }
//        }
//    )
//}
//
//@ExperimentalFoundationApi
//fun FlowBuilder.hiltScreen(name: String, content: RenderWithParams<Any?>) {
//    screen(
//        name = name,
//        content = {
//            WrapHiltViewModelNavigation(name) {
//                content(it)
//            }
//        }
//    )
//}

///**
// * Credits: @puritanin
// */
@Composable
private fun WrapHiltViewModelNavigation(
    key: String,
    content: @Composable () -> Unit
) {
    val hiltViewModelStoreOwnerManager = LocalHiltViewModelStoreOwnerManager.current
    val owner = remember {
        hiltViewModelStoreOwnerManager.getViewModelStoreOwnerByKey(key)
    }
    DisposableEffect(Unit) {
        onDispose {
            hiltViewModelStoreOwnerManager.clearViewModelStoreOwnerByKey(key)
        }
    }
    CompositionLocalProvider(LocalViewModelStoreOwner provides owner) {
        content()
    }
}