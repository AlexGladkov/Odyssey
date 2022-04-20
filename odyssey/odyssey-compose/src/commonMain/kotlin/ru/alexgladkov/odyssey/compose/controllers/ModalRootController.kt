package ru.alexgladkov.odyssey.compose.controllers

import kotlinx.coroutines.flow.MutableStateFlow
import ru.alexgladkov.odyssey.compose.Render
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.AlertConfiguration
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.CustomModalConfiguration
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalSheetConfiguration
import ru.alexgladkov.odyssey.core.extensions.CFlow
import ru.alexgladkov.odyssey.core.extensions.wrap

sealed class ModalDialogState {
    object Idle : ModalDialogState()
    object Open : ModalDialogState()
    data class Close(val animate: Boolean = true) : ModalDialogState()
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
 */
internal data class ModalSheetBundle(
    override val dialogState: ModalDialogState,
    override val animationTime: Int,
    override val content: Render,
    val maxHeight: Float?,
    val threshold: Float,
    val closeOnBackdropClick: Boolean,
    val alpha: Float,
    val cornerRadius: Int,
    val closeOnSwipe: Boolean = true,
    val backContent: Render? = null,
) : ModalBundle

/**
 * Class helper to use with compose for alert dialog
 * @param maxHeight - maxHeight in Float. use null for wrap by content
 * @param maxWidth - maxWidth in Float, use null for wrap by content
 * @param closeOnBackdropClick - true if you want to close on backdrop click
 * @param cornerRadius - card corner radius in dp
 * @param alpha - scrimer alpha
 * @param content - composable content
 */
internal data class AlertBundle(
    override val dialogState: ModalDialogState,
    override val animationTime: Int,
    override val content: Render,
    val maxHeight: Float?,
    val maxWidth: Float?,
    val closeOnBackdropClick: Boolean,
    val alpha: Float,
    val cornerRadius: Int,
) : ModalBundle

/**
 * Class helper to use with modal for custom modal
 */
internal data class CustomModalBundle(
    override val dialogState: ModalDialogState,
    override val animationTime: Int,
    override val content: Render
) : ModalBundle

/**
 * Common interface for any modal screens
 * @see ModalDialogState
 */
sealed interface ModalBundle {
    /**
     * composable content
     */
    val content: Render

    /**
     * time for all animations
     */
    val animationTime: Int

    /**
     * current dialog state
     */
    val dialogState: ModalDialogState
}

@Deprecated("see ModalSheetController", ReplaceWith("ModalSheetController"))
class ModalSheetController : ModalController()

/**
 * Class controller for bottom modal sheet
 */
open class ModalController {
    private var _backStack = mutableListOf<ModalBundle>()
    private val _currentStack: MutableStateFlow<List<ModalBundle>> = MutableStateFlow(emptyList())
    val currentStack: CFlow<List<ModalBundle>> = _currentStack.wrap()

    internal fun presentNew(
        modalSheetConfiguration: ModalSheetConfiguration,
        content: Render
    ) {
        _backStack.add(modalSheetConfiguration.wrap(content))
        redrawStack()
    }

    internal fun presentNew(
        alertConfiguration: AlertConfiguration,
        content: Render
    ) {
        _backStack.add(alertConfiguration.wrap(content))
        redrawStack()
    }

    internal fun presentNew(
        customConfiguration: CustomModalConfiguration,
        content: Render
    ) {
        _backStack.add(customConfiguration.wrap(content))
        redrawStack()
    }

    fun popBackStack(animate: Boolean = true) {
        setTopDialogState(modalDialogState = ModalDialogState.Close(animate))
    }

    internal fun setTopDialogState(modalDialogState: ModalDialogState) {
        if (modalDialogState is ModalDialogState.Close && !modalDialogState.animate) {
            finishCloseAction()
            return
        }
        val last =
            _backStack.last { modalDialogState !is ModalDialogState.Close || it.dialogState != modalDialogState }
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
    internal fun finishCloseAction() {
        if (_backStack.isNotEmpty()) _backStack.removeLast()
        redrawStack()
    }

    @Deprecated("@see popBackStack", ReplaceWith("popBackStack()"))
    fun removeTopScreen() {
        popBackStack()
    }

    fun clearBackStack() {
        _backStack.clear()
    }

    fun isEmpty() = _backStack.isEmpty()

    private fun redrawStack() {
        val newStack = ArrayList<ModalBundle>().apply {
            addAll(_backStack)
        }
        _currentStack.value = newStack
    }
}

internal fun ModalSheetConfiguration.wrap(with: Render): ModalBundle = ModalSheetBundle(
    maxHeight = maxHeight,
    closeOnBackdropClick = closeOnBackdropClick,
    closeOnSwipe = closeOnSwipe,
    threshold = threshold,
    animationTime = animationTime,
    cornerRadius = cornerRadius,
    alpha = alpha,
    backContent = backContent,
    dialogState = ModalDialogState.Idle,
    content = with
)

internal fun AlertConfiguration.wrap(with: Render): ModalBundle = AlertBundle(
    maxHeight = maxHeight,
    maxWidth = maxWidth,
    animationTime = animationTime,
    dialogState = ModalDialogState.Idle,
    closeOnBackdropClick = closeOnBackdropClick,
    cornerRadius = cornerRadius,
    alpha = alpha,
    content = with
)

@Suppress("unused")
internal fun CustomModalConfiguration.wrap(with: Render): ModalBundle = CustomModalBundle(
    content = with, dialogState = ModalDialogState.Idle, animationTime = animationTime
)