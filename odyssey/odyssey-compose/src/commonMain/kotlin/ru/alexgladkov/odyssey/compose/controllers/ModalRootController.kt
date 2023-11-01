package ru.alexgladkov.odyssey.compose.controllers

import kotlinx.coroutines.flow.MutableStateFlow
import ru.alexgladkov.odyssey.compose.Render
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.AlertConfiguration
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.CustomModalConfiguration
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalSheetConfiguration
import ru.alexgladkov.odyssey.core.extensions.CFlow
import ru.alexgladkov.odyssey.core.extensions.wrap

class Modal(val key: String, val name: String?)

sealed class ModalDialogState {
    data object Idle : ModalDialogState()
    data object Open : ModalDialogState()
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
    override val key: String,
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
    override val name: String?,
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
    override val key: String,
    override val dialogState: ModalDialogState,
    override val animationTime: Int,
    override val content: Render,
    val maxHeight: Float?,
    val maxWidth: Float?,
    val closeOnBackdropClick: Boolean,
    val alpha: Float,
    val cornerRadius: Int,
    override val name: String?,
) : ModalBundle

/**
 * Class helper to use with modal for custom modal
 */
internal data class CustomModalBundle(
    override val key: String,
    override val dialogState: ModalDialogState,
    override val animationTime: Int,
    override val content: Render,
    override val name: String?,
) : ModalBundle

/**
 * Common interface for any modal screens
 * @see ModalDialogState
 */
sealed interface ModalBundle {
    /**
     * Non-unique user identifier
     */
    val name: String?

    val key: String

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

@Deprecated("see ModalController", ReplaceWith("ModalController"))
class ModalSheetController : ModalController()

/**
 * Class controller for bottom modal sheet
 */
open class ModalController {
    private var _backStack = mutableListOf<ModalBundle>()
    private val _currentStack: MutableStateFlow<List<ModalBundle>> = MutableStateFlow(emptyList())
    val currentStack: CFlow<List<ModalBundle>> = _currentStack.wrap()
    val backStack: List<Modal> get() = _backStack
        .filter { !it.isClosed }
        .map { Modal(it.key, it.name) }

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
    fun popBackStack(animate: Boolean = true) {
        popBackStack(key = null, animate = animate)
    }

    fun popBackStack(key: String? = null, animate: Boolean = true) {
        setTopDialogState(modalDialogState = ModalDialogState.Close(animate), key)
    }

    internal fun setTopDialogState(modalDialogState: ModalDialogState, key: String? = null) {
        if (modalDialogState is ModalDialogState.Close && !modalDialogState.animate) {
            finishCloseAction(key)
            return
        }
        val last = if (key != null) _backStack.firstOrNull { it.key == key }
        else _backStack
            .lastOrNull { modalDialogState !is ModalDialogState.Close || it.dialogState != modalDialogState }
        if (last == null) return
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
    internal fun finishCloseAction(key: String?) {
        when {
            key != null -> {
                val index = _backStack.indexOfFirst { it.key == key }
                if (index != -1)
                    _backStack.removeAt(index)
            }
            _backStack.isNotEmpty() -> _backStack.removeLast()
        }
        redrawStack()
    }

    fun clearBackStack() {
        _backStack.clear()
        redrawStack()
    }

    fun isEmpty(): Boolean = _backStack.none { !it.isClosed }

    private fun redrawStack() {
        val newStack = ArrayList<ModalBundle>().apply {
            addAll(_backStack)
        }
        _currentStack.value = newStack
    }
    companion object{
        internal fun randomizeKey() = RootController.randomizeKey("modal_")
    }
}

internal fun ModalSheetConfiguration.wrap(key: String, with: Render): ModalBundle = ModalSheetBundle(
    key = key,
    name = name,
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

internal fun AlertConfiguration.wrap(key: String, with: Render): ModalBundle = AlertBundle(
    key = key,
    name = name,
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
internal fun CustomModalConfiguration.wrap(key: String, with: Render): ModalBundle = CustomModalBundle(
    key = key,
    name = name,
    content = with,
    dialogState = ModalDialogState.Idle,
    animationTime = animationTime
)

private inline val ModalBundle.isClosed: Boolean get() = this.dialogState is ModalDialogState.Close