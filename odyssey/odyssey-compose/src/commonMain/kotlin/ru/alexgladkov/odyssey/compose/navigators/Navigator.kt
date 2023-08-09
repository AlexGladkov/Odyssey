package ru.alexgladkov.odyssey.compose.navigators

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.alexgladkov.odyssey.compose.AllowedDestination
import ru.alexgladkov.odyssey.compose.Render
import ru.alexgladkov.odyssey.compose.RenderWithParams
import ru.alexgladkov.odyssey.compose.ScreenType
import ru.alexgladkov.odyssey.compose.base.AnimatedHost
import ru.alexgladkov.odyssey.compose.helpers.FlowBuilder
import ru.alexgladkov.odyssey.core.CoreScreen
import ru.alexgladkov.odyssey.core.animations.AnimationType
import ru.alexgladkov.odyssey.core.breadcrumbs.Breadcrumb
import ru.alexgladkov.odyssey.core.configuration.RootControllerType
import ru.alexgladkov.odyssey.core.extensions.createUniqueKey
import ru.alexgladkov.odyssey.core.platform.Parcelable
import ru.alexgladkov.odyssey.core.platform.Parcelize
import ru.alexgladkov.odyssey.core.screen.ScreenBundle
import kotlin.jvm.JvmStatic

@Composable
fun TestNavigator() {
    val navigationController = rememberSaveable {
        println("DEBUG: create new controller")
        NavigationController()
    }
    val currentScreen by navigationController.currentScreen.collectAsState()
    val saveableStateHolder = rememberSaveableStateHolder()

    CompositionLocalProvider(
        LocalNavigationController provides navigationController
    ) {
        currentScreen?.let {
            AnimatedHost(
                currentScreen = ScreenBundle(key = it.key, realKey = it.realKey, params = null),
                animationType = AnimationType.None,
                isForward = true,
                screenToRemove = null,
                content = { screen ->
                    val key = remember { screen.key }
                    saveableStateHolder.SaveableStateProvider(key) {
                        it.render.invoke(it.realKey)
                    }
                }
            )
        }
    }
}

val LocalNavigationController =
    staticCompositionLocalOf<NavigationController> { error("No default navigation controller") }

@Stable
@Parcelize
class ScreenHolder(
    val key: String,
    val realKey: String,
    val render: Render,
    val animationType: AnimationType,
) : Parcelable

@Stable
@Parcelize
class NavigationController : NewCoreRootController<ScreenHolder>(RootControllerType.Root),
    Parcelable {

    override var onScreenNavigate: ((Breadcrumb) -> Unit)? = null
    override var onScreenRemove: (CoreScreen) -> Unit = { }
    override val _backstack: MutableList<ScreenHolder> = mutableListOf()

    private val _screenMap = LinkedHashMap<String, RenderWithParams<Any?>>()

    init {
        val firstFlowBuilder = FlowBuilder("flow_1")
        firstFlowBuilder.addScreen("flow_screen_1") { TextWithCounter("flow_screen_1") }
        firstFlowBuilder.addScreen("flow_screen_2") { TextWithCounter("flow_screen_2") }
        firstFlowBuilder.addScreen("flow_screen_3") { TextWithCounter("flow_screen_3") }

        _allowedDestinations.add(AllowedDestination("screen1", ScreenType.Simple))
        _allowedDestinations.add(AllowedDestination("screen2", ScreenType.Simple))
        _allowedDestinations.add(AllowedDestination("screen3", ScreenType.Simple))
        _allowedDestinations.add(
            AllowedDestination("screen4", ScreenType.Flow(firstFlowBuilder.build()))
        )

        _screenMap["screen1"] = { TextWithCounter("screen1") }
        _screenMap["screen2"] = { TextWithCounter("screen2") }
        _screenMap["screen3"] = { TextWithCounter("screen3") }

        launch("screen1")
    }

    override fun pushToStack(value: ScreenHolder) {
        _backstack.add(value)
    }

    fun launch(key: String) {
        val screenType = _allowedDestinations.find { it.key == key }?.screenType
            ?: throw IllegalStateException("Can't find screen in destination. Did you provide this screen?")

        when (screenType) {
            is ScreenType.Flow -> {
                _currentScreen.value =
                    screenType.flowBuilderModel.screenMap[screenType.flowBuilderModel.screenMap.keys.first()]?.let {
                        ScreenHolder(
                            key = randomizeKey(key),
                            realKey = key,
                            animationType = AnimationType.Present(animationTime = 300),
                            render = it
                        )
                    }
            }

            is ScreenType.MultiStack -> {}
            ScreenType.Simple -> {
                _screenMap[key]?.let {
                    val screenHolder = ScreenHolder(
                        key = randomizeKey(key),
                        realKey = key,
                        animationType = AnimationType.Present(animationTime = 300),
                        render = it
                    )

                    _currentScreen.value = screenHolder
                    pushToStack(screenHolder)
                }
            }
        }
    }
}

abstract class NewCoreRootController<T>(
    val rootControllerType: RootControllerType
) {

    abstract var onScreenNavigate: ((Breadcrumb) -> Unit)?
    abstract var onScreenRemove: (CoreScreen) -> Unit
    protected val _allowedDestinations: MutableList<AllowedDestination> = mutableListOf()
    protected abstract val _backstack: MutableList<T>
    protected val _currentScreen: MutableStateFlow<T?> = MutableStateFlow(null)
    val currentScreen: StateFlow<T?> = _currentScreen.asStateFlow()

    protected fun cleanRealKeyFromType(realKey: String): String =
        realKey
            .replace(flowKey, "")
            .replace(multiStackKey, "")
            .replace("$", "")

    open fun popBackStack(byBackPressed: Boolean = false): T? {
        println("DEBUG: Back clicked $_backstack")
        if (_backstack.isEmpty() || _backstack.size == 1) return null

        val removedObject = _backstack.removeLast()
        println("DEBUG: removed object $removedObject")
        _currentScreen.value = removedObject
//        onScreenRemove.invoke(removedObject)
        return removedObject
    }

    open fun backToScreen(screenName: String) {
        if (_backstack.isEmpty()) return

//        do {
//            val removedObject = _backstack.removeLast()
//            onScreenRemove.invoke(removedObject)
//        } while (_backstack.last().realKey != screenName)
    }

    protected abstract fun pushToStack(value: T)

    companion object {
        const val flowKey = "odyssey_flow_reserved_type"
        const val multiStackKey = "odyssey_multi_stack_reserved_type"

        @JvmStatic
        fun randomizeKey(key: String): String = createUniqueKey(key)
    }
}

@Composable
fun TextWithCounter(key: String) {
    val navigationController = LocalNavigationController.current

    Column {
        Text(
            modifier = Modifier.clickable {
                navigationController.launch(
                    when (key) {
                        "screen1" -> "screen2"
                        "screen2" -> "screen3"
                        "screen3" -> "screen4"
                        else -> "screen1"
                    }
                )
            }
                .padding(20.dp),
            text = key,
            fontSize = 20.sp, color = Color.Black
        )

        Button(onClick = {
            navigationController.popBackStack()
        }) {
            Text("Back")
        }
    }
}