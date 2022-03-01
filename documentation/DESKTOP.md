### How to use it with Desktop

To setup Desktop you need to launch first screen from graph For that I've made default function which works in default
JFrame

```kotlin
val window = JFrame()
window.title = "Odyssey Demo"
window.setSize(800, 600)

window.setupNavigation("start") {
    generateGraph()
}
```

```"start"``` using for start screen and ```generateGraph()``` is you navigation graph

With compose you often need to set some `LocalProviders` for compose. For this you can provide providers
inside `setupNavigation` function

```kotlin
val providers = arrayOf(
    LocalPlatformConfiguration provides platformConfiguration,
    LocalAnalyticsTracker provides analyticsTracker,
    LocalPerformanceTracker provides FirebasePerformance(),
    LocalCrashTracker provides FirebaseCrashTracker(),
    LocalLifecycleOwner provides LifecycleOwner(owner = this),
    LocalKamelConfig provides kamelConfig
)

val window = JFrame()
window.title = "Odyssey Demo"
window.setSize(800, 600)

window.setupNavigation(
    startScreen = "start",
    providers = providers
) {
    generateGraph()
}
```

Sometimes you need to add compose wrapper for your navigator for this you can write your own function like this

```kotlin
fun JFrame.setupThemedNavigation(
    startScreen: String,
    vararg providers: ProvidedValue<*>,
    navigationGraph: RootComposeBuilder.() -> Unit
) {
    val rootController = RootComposeBuilder().apply(navigationGraph).build()

    defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
    title = "OdysseyDemo"

    val composePanel = ComposePanel()
    composePanel.setContent {
        CompositionLocalProvider(
            *providers,
            LocalRootController provides rootController
        ) {
            // Here you can provide your own composables
            ModalSheetNavigator {
                Navigator(startScreen)
            }
        }
    }

    contentPane.add(composePanel, BorderLayout.CENTER)
    setSize(800, 600)
    setLocationRelativeTo(null)
    isVisible = true
}
```