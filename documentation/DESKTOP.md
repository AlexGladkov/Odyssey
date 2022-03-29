### How to use it with desktop

For working with Android I've created helper class DesktopScreenHost and now to setup Application you just need to use this
in your `main()` function

```kotlin
val window = JFrame()
window.title = "Odyssey Demo"
window.setSize(800, 600)

window.setupNavigation("actions") {
    screen("actions") {
        ActionsScreen(count = 0)
    }

    // Here you can place other graph
}
```

Sometimes you need to add compose wrapper for your navigator for this you can write your own function like this
```kotlin
fun JFrame.setupNavigation(
    startScreen: String,
    vararg providers: ProvidedValue<*>,
    navigationGraph: RootComposeBuilder.() -> Unit
) {
    val rootController = RootComposeBuilder().apply(navigationGraph).build()

    setContent {
        CompositionLocalProvider(
            *providers,
            LocalRootController provides rootController
        ) {
            // Here you can provide your own composables (Theme for example)
            ModalSheetNavigator {
                Navigator(startScreen)
            }
        }
    }
}
```
