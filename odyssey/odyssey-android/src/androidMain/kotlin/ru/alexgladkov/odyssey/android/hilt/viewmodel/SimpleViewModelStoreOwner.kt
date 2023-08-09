package ru.alexgladkov.odyssey.android.hilt.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import dagger.hilt.android.internal.lifecycle.HiltViewModelFactory

/**
 * Class bridge between Android ViewModelStoreOwner and Odyssey
 * @param activityContext - Android Context
 * Credits: @puritanin
 */
class SimpleViewModelStoreOwner(
    activityContext: Context,
) : ViewModelStoreOwner,
    HasDefaultViewModelProviderFactory,
    SavedStateRegistryOwner,
    LifecycleOwner {

    private val activity = activityContext.let {
        var ctx = it
        while (ctx is ContextWrapper) {
            if (ctx is Activity) {
                return@let ctx
            }
            ctx = ctx.baseContext
        }
        throw IllegalStateException()
    }

    private val store = ViewModelStore()

    override val lifecycle = LifecycleRegistry(this).apply {
        this.currentState = Lifecycle.State.INITIALIZED
    }

    private val savedStateRegistryController = SavedStateRegistryController.create(this).apply {
        this.performRestore(null)
    }

    private val defaultFactory = SavedStateViewModelFactory(
        (activityContext.applicationContext as? Application),
        this,
        null
    )

    private val factory: ViewModelProvider.Factory = HiltViewModelFactory.createInternal(
        activity,
        this,
        null,
        defaultFactory
    )

    override val defaultViewModelProviderFactory: ViewModelProvider.Factory = factory
    override val viewModelStore: ViewModelStore = store

    override val savedStateRegistry: SavedStateRegistry
        get() = savedStateRegistryController.savedStateRegistry

}