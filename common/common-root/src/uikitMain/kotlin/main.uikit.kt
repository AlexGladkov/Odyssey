import kotlinx.cinterop.*
import platform.UIKit.*
import platform.Foundation.*
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.Application
import ru.alexgladkov.odyssey.compose.base.Navigator
import ru.alexgladkov.odyssey.compose.extensions.flow
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
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

class SkikoAppDelegate : UIResponder, UIApplicationDelegateProtocol {
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
        window!!.rootViewController = Application("ComposeOdysseyDemo") {
            OdysseyTheme {
                val rootController = RootComposeBuilder().apply {
                    screen("actions") {
                        ActionScreen(count = 0)
                    }

                    screen("push") {
                        ActionScreen(count = it as? Int)
                    }

                    flow("present") {
                        screen("present_screen") {
                            PresentedActionsScreen(count = (it as? Int) ?: 0)
                        }
                    }
                }.build()
                rootController.backgroundColor = Odyssey.color.primaryBackground

                CompositionLocalProvider(
                    LocalRootController provides rootController
                ) {
                    Navigator("actions")
                }
            }
        }
        window!!.makeKeyAndVisible()
        return true
    }
}