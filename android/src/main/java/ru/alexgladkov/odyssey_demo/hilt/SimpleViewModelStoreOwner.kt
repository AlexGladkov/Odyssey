package ru.alexgladkov.odyssey_demo.hilt

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import dagger.hilt.android.internal.lifecycle.HiltViewModelFactory

class SimpleViewModelStoreOwner(
    activityContext: Context
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

    private val lifecycle = LifecycleRegistry(this).apply {
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

    override fun getViewModelStore(): ViewModelStore = store

    override fun getDefaultViewModelProviderFactory(): ViewModelProvider.Factory = factory

    override fun getLifecycle(): Lifecycle = lifecycle

    override fun getSavedStateRegistry(): SavedStateRegistry {
        return savedStateRegistryController.savedStateRegistry
    }
}
