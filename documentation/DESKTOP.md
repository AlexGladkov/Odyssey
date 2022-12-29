### How to use it with desktop

First, you need to setup your navigation graph (check getting started section), and then setup it in `main` function

```kotlin
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Odyssey Desktop Example",
        state = rememberWindowState(
            width = 1024.dp,
            height = 800.dp,
            position = WindowPosition.Aligned(Alignment.Center)
        )
    ) {
        setNavigationContent(OdysseyConfiguration(), onApplicationFinish = {
            exitApplication()
        }) {
            navigationGraph()
        }
    }
}
```

**Navigation Configuration**

`setNavigationContent` function is expect/actual and contains `OdysseyConfiguration` class which is expect/actual
too, so for every platform
it contains different implementations. For desktop it has following parameters

```kotlin
/**
 * @param startScreen (optional) - sealed class with two types of params First and Custom.
 * if you check first, which is by default, it will take first screen from your navigation graph, otherwise
 * it takes as entry point screen with provided name
 * @param backgroundColor (optional) - color for animation background. For example if you have dark theme you may want
 * provide your theme color to avoid blink effect in animations
 */
actual class OdysseyConfiguration(
    val startScreen: StartScreen = StartScreen.First,
    val backgroundColor: Color = Color.White
)
```

**Theme, LocalCompositionProvider**

If you need add theme or any composition local provider you can wrap setNavigationContent in any Composable function

```kotlin
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Odyssey Desktop Example",
        state = rememberWindowState(
            width = 1024.dp,
            height = 800.dp,
            position = WindowPosition.Aligned(Alignment.Center)
        )
    ) {
        OdysseyTheme {
            val configuration = OdysseyConfiguration(
                backgroundColor = Odyssey.color.primaryBackground
            )

            setNavigationContent(configuration, onApplicationFinish = {
                exitApplication()
            }) {
                navigationGraph()
            }
        }
    }
}
```
