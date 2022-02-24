import androidx.compose.foundation.layout.padding
import kotlinx.cinterop.*
import platform.UIKit.*
import platform.Foundation.*
import androidx.compose.material.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Application
import ru.alexgladkov.common.root.navigation.generateGraph
import ru.alexgladkov.common.root.screens.MainScreen
import ru.alexgladkov.common.root.screens.StartScreen
import ru.alexgladkov.odyssey.core.base.Navigator
import ru.alexgladkov.odyssey.core.local.LocalRootController
import ru.alexgladkov.odyssey.core.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.core.navigation.bottom_sheet_navigation.ModalSheetNavigator

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
        window!!.rootViewController = Application("ComposeOdysseyDemo") {
            val rootController = RootComposeBuilder().apply {
                generateGraph()
            }.build()

            CompositionLocalProvider(
                LocalRootController provides rootController
            ) {
//                ModalSheetNavigator {
//                    Navigator("start")
//                }
                MainScreen()
            }
        }
        window!!.makeKeyAndVisible()
        return true
    }
}
