package ru.alexgladkov.odyssey.android.hilt.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModelStoreOwner
import ru.alexgladkov.odyssey.compose.RootController

/**
 * Class for android ViewModel store integration with hilt and odyssey
 * @param context - Android Context
 * @param rootController - Odyssey RootController
 * Credits: @puritanin
 */
class HiltViewModelStoreOwnerManager(
    private val context: Context,
    private val rootController: RootController
) {
    private val owners = HashMap<String, ViewModelStoreOwner>()
    private val ownersLevels = HashMap<String, Int>()

    init {
        rootController.onScreenRemove = {
            // we can't delete owner here, the screen is still alive
            it.realKey?.let { key ->
                ownersLevels[key]?.let {
                    // mark for deletion
                    println("mark for deletion - $key")
                    ownersLevels[key] = Int.MAX_VALUE
                }
            }
        }
    }

    fun getViewModelStoreOwnerByKey(key: String): ViewModelStoreOwner {
        val currentLevel = rootController.measureLevel()
        ownersLevels[key] = currentLevel
        return owners[key] ?: SimpleViewModelStoreOwner(context).also {
            owners[key] = it
        }
    }

    fun clearViewModelStoreOwnerByKey(key: String) {
        val currentLevel = rootController.measureLevel()
        val ownerLevel = ownersLevels[key] ?: Int.MAX_VALUE
        if (currentLevel < ownerLevel) {
            owners[key]?.viewModelStore?.clear()
            owners.remove(key)
            ownersLevels.remove(key)
        }
    }

    fun dispose() {
        val keys = owners.keys.toList()
        keys.forEach { key ->
            owners[key]?.viewModelStore?.clear()
            owners.remove(key)
            ownersLevels.remove(key)
        }
    }
}