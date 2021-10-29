### How to use it with desktop

For working with Android I've created helper class DesktopScreenHost and now to setup Application you just need to use this
in your `main()` function

```kotlin
fun main() = SwingUtilities.invokeLater {
        val window = JFrame()
        window.title = "Odyssey Demo"
        window.setSize(800, 600)

        DesktopScreenHost(window)
            .setupWithRootController(
                startScreen = NavigationTree.Root.Splash.toString(),
                block = buildComposeNavigationGraph()
            )
    }
```

If you need to customize parameters of your ScreenHost you can create your own ScreenHost class like this

```kotlin
class DesktopScreenHost constructor(
    private val window: JFrame,
    // add your params here
) : ComposableScreenHost() {

    override fun prepareFowDrawing() {
        val composePanel = ComposePanel()

        composePanel.setContent {
            val destinationState = destinationObserver.wrap().observeAsState()
            destinationState.value?.let {
                launchScreen(it)
            }
        }

        window.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        window.contentPane.add(composePanel, BorderLayout.CENTER)
        window.setLocationRelativeTo(null)
        window.isVisible = true
    }
}
```
