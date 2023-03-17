package ru.alexgladkov.odyssey.compose.controllers

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import ru.alexgladkov.odyssey.compose.Render
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.AlertConfiguration
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.CustomModalConfiguration
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalSheetConfiguration
import ru.alexgladkov.odyssey.core.animations.AnimationType
import ru.alexgladkov.odyssey.core.animations.getAnimationTime
import ru.alexgladkov.odyssey.core.extensions.CFlow
import ru.alexgladkov.odyssey.core.extensions.wrap

sealed class ModalDialogState {
    object Idle : ModalDialogState()
    object Open : ModalDialogState()
    data class Close(val animate: Boolean = true, val byBackPressed: Boolean = false) :
        ModalDialogState()
}

/**
 * Class helper to use with compose for bottom modal sheet
 * @param maxHeight - maxHeight in Float. use null for wrap by content
 * @param cornerRadius - card corner radius in dp
 * @param threshold - threshold for closing modal bottom sheet
 * @param alpha - screamer alpha
 * @param closeOnBackdropClick - true if you want to close on backdrop click
 * @param closeOnSwipe - true if you want to close on swipe
 * @param content - composable content
 * @param closeOnBackClick - true if you want to close on the hardware back button
 */
@Immutable
internal data class ModalSheetBundle(
    override val key: String,
    override val dialogState: ModalDialogState,
    override val animationType: AnimationType,
    override val animationTime: Int,
    override val content: Render,
    val maxHeight: Float?,
    val threshold: Float,
    val closeOnBackdropClick: Boolean,
    val alpha: Float,
    val cornerRadius: Int,
    val closeOnSwipe: Boolean = true,
    val backContent: Render? = null,
    val closeOnBackClick: Boolean = true,
) : ModalBundle

/**
 * Class helper to use with compose for alert dialog
 * @param maxHeight - maxHeight in Float. use null for wrap by content
 * @param maxWidth - maxWidth in Float, use null for wrap by content
 * @param closeOnBackdropClick - true if you want to close on backdrop click
 * @param cornerRadius - card corner radius in dp
 * @param alpha - scrimer alpha
 * @param content - composable content
 * @param closeOnBackClick - true if you want to close on the hardware back button
 */
@Immutable
internal data class AlertBundle(
    override val key: String,
    override val dialogState: ModalDialogState,
    override val animationType: AnimationType,
    override val animationTime: Int,
    override val content: Render,
    val maxHeight: Float?,
    val maxWidth: Float?,
    val closeOnBackdropClick: Boolean,
    val alpha: Float,
    val cornerRadius: Int,
    val closeOnBackClick: Boolean = true,
) : ModalBundle

/**
 * Class helper to use with modal for custom modal
 */
@Immutable
internal data class CustomModalBundle(
    override val key: String,
    override val dialogState: ModalDialogState,
    override val animationType: AnimationType,
    override val animationTime: Int,
    override val content: Render
) : ModalBundle

/**
 * Common interface for any modal screens
 * @see ModalDialogState
 */
sealed interface ModalBundle {
    val key: String

    /**
     * composable content
     */
    val content: Render

    /**
     * Animation type
     */
    val animationType: AnimationType

    /**
     * Animation time
     */
    val animationTime: Int

    /**
     * current dialog state
     */
    val dialogState: ModalDialogState
}

/**
 * Class controller for bottom modal sheet
 */
open class ModalController {
    private var _backStack = mutableListOf<ModalBundle>()
    private val _currentStack: MutableStateFlow<ImmutableList<ModalBundle>> =
        MutableStateFlow(persistentListOf())
    val currentStack: CFlow<ImmutableList<ModalBundle>> = _currentStack.wrap()

    internal fun presentNew(
        modalSheetConfiguration: ModalSheetConfiguration,
        content: Render
    ) {
        _backStack.add(modalSheetConfiguration.wrap(randomizeKey(), content))
        redrawStack()
    }

    internal fun presentNew(
        alertConfiguration: AlertConfiguration,
        content: Render
    ) {
        _backStack.add(alertConfiguration.wrap(randomizeKey(), content))
        redrawStack()
    }

    internal fun presentNew(
        customConfiguration: CustomModalConfiguration,
        content: Render
    ) {
        _backStack.add(customConfiguration.wrap(randomizeKey(), content))
        redrawStack()
    }

    @Deprecated("@see popBackStack with key param", ReplaceWith("popBackStack(key = KEY)"))
    fun popBackStack(animate: Boolean = true, byBackPressed: Boolean = false) {
        popBackStack(key = null, animate = animate, byBackPressed = byBackPressed)
    }

    fun popBackStack(key: String? = null, animate: Boolean = true, byBackPressed: Boolean = false) {
        setTopDialogState(
            modalDialogState = ModalDialogState.Close(
                animate,
                byBackPressed = byBackPressed
            ), key
        )
    }

    internal fun setTopDialogState(modalDialogState: ModalDialogState, key: String? = null) {
        if (modalDialogState is ModalDialogState.Close && !modalDialogState.animate) {
            finishCloseAction(key, byBackPressed = modalDialogState.byBackPressed)
            return
        }
        val last = when {
            key != null -> _backStack.firstOrNull { it.key == key }
            modalDialogState !is ModalDialogState.Close -> _backStack.lastOrNull()
            else -> _backStack.lastOrNull { it.dialogState != modalDialogState }
        } ?: return

        if (modalDialogState is ModalDialogState.Close && modalDialogState.byBackPressed && !last.closeOnBackClick)
            return

        val index = _backStack.indexOf(last)
        val newState =
            when (last) {
                is ModalSheetBundle -> last.copy(dialogState = modalDialogState)
                is AlertBundle -> last.copy(dialogState = modalDialogState)
                is CustomModalBundle -> last.copy(dialogState = modalDialogState)
            }

        _backStack[index] = newState
        redrawStack()
    }

    /** Removes last modal from backstack */
    internal fun finishCloseAction(key: String?, byBackPressed: Boolean = false) {
        val modalBundle = when {
            key != null -> _backStack.firstOrNull { it.key == key }
            else -> _backStack.lastOrNull()
        }
        modalBundle?.let { bundle ->
            if (!byBackPressed || bundle.closeOnBackClick) {
                _backStack.remove(bundle)
            }
        }
        redrawStack()
    }

    fun clearBackStack() {
        _backStack.clear()
        redrawStack()
    }

    fun isEmpty() = _backStack.none { it.dialogState !is ModalDialogState.Close }

    private fun redrawStack() {
        val newStack = ArrayList<ModalBundle>().apply {
            addAll(_backStack)
        }
        _currentStack.value = newStack.toImmutableList()
    }

    companion object {
        private val ModalBundle.closeOnBackClick
            get() = when (this) {
                is ModalSheetBundle -> this.closeOnBackClick
                is AlertBundle -> this.closeOnBackClick
                else -> true
            }

        internal fun randomizeKey() = RootController.randomizeKey("modal_")
    }
}

internal fun ModalSheetConfiguration.wrap(key: String, with: Render): ModalBundle =
    ModalSheetBundle(
        key = key,
        maxHeight = maxHeight,
        closeOnBackdropClick = closeOnBackdropClick,
        closeOnSwipe = closeOnSwipe,
        closeOnBackClick = closeOnBackClick,
        threshold = threshold,
        animationType = animationType,
        animationTime = animationType.getAnimationTime(),
        cornerRadius = cornerRadius,
        alpha = alpha,
        backContent = backContent,
        dialogState = ModalDialogState.Idle,
        content = with
    )

internal fun AlertConfiguration.wrap(key: String, with: Render): ModalBundle = AlertBundle(
    key = key,
    maxHeight = maxHeight,
    maxWidth = maxWidth,
    animationType = animationType,
    animationTime = animationType.getAnimationTime(),
    dialogState = ModalDialogState.Idle,
    closeOnBackdropClick = closeOnBackdropClick,
    closeOnBackClick = closeOnBackClick,
    cornerRadius = cornerRadius,
    alpha = alpha,
    content = with
)

@Suppress("unused")
internal fun CustomModalConfiguration.wrap(key: String, with: Render): ModalBundle =
    CustomModalBundle(
        key = key,
        content = with,
        dialogState = ModalDialogState.Idle,
        animationType = animationType,
        animationTime = animationType.getAnimationTime()
    )