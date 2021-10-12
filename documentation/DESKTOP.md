### How to use it with desktop

First you need create your Application Screen Host

```kotlin
// You need to inherit from DesktopScreenHost
class AppScreenHost(window: JFrame) : DesktopScreenHost(window) {

    @Composable
    override fun launchScreen(destinationPoint: DestinationPoint) {
        val state = destinationPoint.rootController.backStackObserver.observeAsState()
        state.value?.let { entry ->
            when (entry.destination.destinationName()) {
                // Here you can set up your root layer navigation
                "splash" -> SplashScreen(entry.rootController)
            }
        }
    }
}
```

Then you need tune your **main** function like this

```kotlin
fun main() = SwingUtilities.invokeLater {
    val window = JFrame()
    window.title = "Odyssey Demo"
    window.setSize(800, 600)

    val screenHost = AppScreenHost(window)
    val rootController = RootController(screenHost)
    rootController.generateNavigationGraph()
    screenHost.prepareFowDrawing()
    rootController.launch(NavigationTree.Root.Splash.toString())
}
```
