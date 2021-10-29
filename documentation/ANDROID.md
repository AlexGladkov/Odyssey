### How to use it with android

For working with Android I've created helper class AndroidScreenHost and now to setup Activity you just need to use this 
in your `onCreate` function

```kotlin
AndroidScreenHost(this)
    .setupActivityWithRootController(
        startScreen = NavigationTree.Root.Splash.toString(),
        block = buildComposeNavigationGraph()
    )
```

If you need to customize parameters of your ScreenHost you can create your own ScreenHost class like this

```kotlin
class AndroidScreenHost constructor(
    val composeActivity: ComponentActivity,
    // add your params here
) : ComposableScreenHost() {

    override fun prepareFowDrawing() {
        // Below function setup drawing, you can extend it 
        // by adding CompositionLocalProviders or something else
        composeActivity.setContent {
            val destinationState = destinationObserver.wrap().observeAsState()
            destinationState.value?.let {
                launchScreen(it)
            }
        }
    }
}
```

And you need to create your own extension to setup back press callback

```kotlin
fun AndroidScreenHost.setupActivityWithRootController(startScreen: String, block: RootComposeBuilder.() -> Unit) {
    prepareFowDrawing()

    val builder = RootComposeBuilder(screenHost = this)
    val rootController = builder.apply(block).build()
    rootController.setupWithActivity(composeActivity)

    setScreenMap(builder.screenMap)
    rootController.launch(screen = startScreen)
}
```