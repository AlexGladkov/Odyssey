package ru.alexgladkov.odyssey.android.hilt.local

import androidx.compose.runtime.compositionLocalOf
import ru.alexgladkov.odyssey.android.hilt.viewmodel.HiltViewModelStoreOwnerManager

val LocalHiltViewModelStoreOwnerManager = compositionLocalOf<HiltViewModelStoreOwnerManager> {
    error("No HiltViewModelStoreOwnerManager")
}