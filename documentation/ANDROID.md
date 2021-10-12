### How to use it with android

First you need create your Application Screen Host

```kotlin
// You need to inherit from AndroidScreenHost
class AppScreenHost(composeActivity: ComponentActivity) : AndroidScreenHost(composeActivity) {

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

Then you need tune your Main Activity like this

```kotlin
val screenHost = AppScreenHost(this)
val rootController = RootController(screenHost)
rootController.generateNavigationGraph()
// setupWithActivity need to handle hardware back button
rootController.setupWithActivity(this) 
screenHost.prepareFowDrawing()
rootController.launch("splash")
```
