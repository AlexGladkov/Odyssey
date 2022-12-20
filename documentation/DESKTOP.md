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
fun JFrame.setupThemedNavigation(
    startScreen: String,
    vararg providers: ProvidedValue<*>,
    navigationGraph: RootComposeBuilder.() -> Unit
) {

    val composePanel = ComposePanel()

    // Below function setup drawing, you can extend it
    // by adding CompositionLocalProviders or something else
    composePanel.setContent {
        OdysseyTheme {
            val rootController = RootComposeBuilder(backgroundColor = Odyssey.color.primaryBackground)
                .apply(navigationGraph).build()

            CompositionLocalProvider(
                *providers,
                LocalRootController provides rootController
            ) {
                ModalNavigator {
                    Navigator(startScreen = startScreen)
                }
            }
        }
    }

    defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
    contentPane.add(composePanel, BorderLayout.CENTER)
    setLocationRelativeTo(null)
    isVisible = true
}
```
