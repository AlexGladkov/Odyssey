package ru.alexgladkov.odyssey.core.backpress

class OnBackPressedDispatcher {
    var backPressedCallback: BackPressedCallback? = null

    fun onBackPressed() {
        backPressedCallback?.onBackPressed()
    }
}