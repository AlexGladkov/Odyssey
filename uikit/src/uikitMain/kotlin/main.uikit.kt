import androidx.compose.material.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.Application
import kotlinx.cinterop.*
import navigation.mainScreen
import platform.Foundation.NSStringFromClass
import platform.UIKit.*
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.base.Navigator
import ru.alexgladkov.odyssey.compose.extensions.flow
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalNavigator
import screens.ActionScreen
import screens.PresentedActionsScreen
import theme.Odyssey
import theme.OdysseyTheme

fun main() {
    val args = emptyArray<String>()
    memScoped {
        val argc = args.size + 1
        val argv = (arrayOf("skikoApp") + args).map { it.cstr.ptr }.toCValues()
        autoreleasepool {
            UIApplicationMain(argc, argv, null, NSStringFromClass(SkikoAppDelegate))
        }
    }
}

class SkikoAppDelegate: UIResponder, UIApplicationDelegateProtocol {

    companion object : UIResponderMeta(), UIApplicationDelegateProtocolMeta

    @ObjCObjectBase.OverrideInit
    constructor() : super()

    private var _window: UIWindow? = null
    override fun window() = _window
    override fun setWindow(window: UIWindow?) {
        _window = window
    }

    override fun application(application: UIApplication, didFinishLaunchingWithOptions: Map<Any?, *>?): Boolean {
        window = UIWindow(frame = UIScreen.mainScreen.bounds)
        window!!.rootViewController = Application("iOS Compose UI") {
            OdysseyTheme {
                val rootController = RootComposeBuilder().apply {
                    screen("actions") {
                        ActionScreen(count = 0)
                    }

                    screen("push") {
                        ActionScreen(count = it as? Int)
                    }

                    presentFlow()
                    mainScreen()
                }.build()

                rootController.backgroundColor = Odyssey.color.primaryBackground

                CompositionLocalProvider(
                    LocalRootController provides rootController
                ) {
                    ModalNavigator {
                        Navigator("actions")
                    }
                }
            }
        }
        window!!.makeKeyAndVisible()
        return true
    }
}

fun RootComposeBuilder.presentFlow() {
    flow("present") {
        screen("present_screen") {
            PresentedActionsScreen(count = (it as? Int) ?: 0)
        }
    }
}