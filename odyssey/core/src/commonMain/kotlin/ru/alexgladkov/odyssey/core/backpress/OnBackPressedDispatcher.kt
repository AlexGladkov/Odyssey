package ru.alexgladkov.odyssey.core.backpress

class OnBackPressedDispatcher {
    var backPressedCallback: BackPressedCallback? = null
        internal set

    fun onBackPressed() {
        backPressedCallback?.onBackPressed()
    }
}