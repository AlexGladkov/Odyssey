### How to use it with android

To setup Android you need to setup hardware backpress handler and launch first screen from graph
For that I've made default function which works in default ComposeActivity

```kotlin
setupNavigation("start") {
    generateGraph()
}
```

```"start"``` using for start screen and ```generateGraph()``` is you navigation graph

With compose you often need to set some `LocalProviders` for compose. For this you can provide
providers inside `setupNavigation` function

```kotlin
val providers = arrayOf(
    LocalPlatformConfiguration provides platformConfiguration,
    LocalAnalyticsTracker provides analyticsTracker,
    LocalPerformanceTracker provides FirebasePerformance(),
    LocalCrashTracker provides FirebaseCrashTracker(),
    LocalLifecycleOwner provides LifecycleOwner(owner = this),
    LocalKamelConfig provides kamelConfig
)

setupNavigation(
    startScreen = "start",
    providers = providers
) {
    generateGraph()
}
```

Sometimes you need to add compose wrapper for your navigator for this you can write your own function like this
```kotlin
fun ComponentActivity.setupThemedNavigation(
    startScreen: String,
    vararg providers: ProvidedValue<*>,
    navigationGraph: RootComposeBuilder.() -> Unit
) {
    val rootController = RootComposeBuilder().apply(navigationGraph).build()
    rootController.setupWithActivity(this)

    setContent {
        CompositionLocalProvider(
            *providers,
            LocalRootController provides rootController
        ) {
            // Here you can provide your own composables
            ModalSheetNavigator {
                Navigator(startScreen = startScreen) 
            }
        }
    }
}
```
