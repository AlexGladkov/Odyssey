### How to use it with android

To setup Android you need to setup hardware backpress handler and launch first screen from graph
For that I've made default function which works in default ComposeActivity

1. First, you need to setup your navigation graph (check getting started section), and then setup it in activity

```kotlin
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val configuration = OdysseyConfiguration(canvas = this)

            setNavigationContent(configuration) {
                navigationGraph()
            }
        }
    }
}
```

**Navigation Configuration**

`setNavigationContent` function is expect/actual and contains `OdysseyConfiguration` class which is expect/actual
   too, so for every platform
   it contains different implementations. For android it has following parameters

```kotlin
/**
 * @param canvas (required) - Host for backpress setup and other android components
 * @param startScreen (optional) - sealed class with two types of params First and Custom.
 * if you check first, which is by default, it will take first screen from your navigation graph, otherwise
 * it takes as entry point screen with provided name
 * @param backgroundColor (optional) - color for animation background. For example if you have dark theme you may want
 * provide your theme color to avoid blink effect in animations
 * @param navigationBarColor (optional) - set color for system navigation bar component if DisplayType.FullScreen selected
 * @param statusBarColor (optional) - set color for system status bar component if DisplayType.FullScreen selected
 * @param displayType (optional) - set display type for navigation host. Choose EdgeToEdge if you want place host between status bar and navigation bar
 * Choose FullScreen if you want to control status bar and navigation bar color and want modal navigator dim status bar
 */
class OdysseyConfiguration(
    val canvas: ComponentActivity,
    val startScreen: StartScreen = StartScreen.First,
    val backgroundColor: Color = Color.White,
    val navigationBarColor: Int = 0x00FFFFFF.toInt(),
    val statusBarColor: Int = 0x00FFFFFF.toInt(),
    val displayType: DisplayType = DisplayType.EdgeToEdge
)
```

**Theme, LocalCompositionProvider**

If you need add theme or any composition local provider you can wrap setNavigationContent in any Composable function

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
        OdysseyTheme {
            val configuration = OdysseyConfiguration(
                canvas = this,
                displayType = DisplayType.FullScreen(Odyssey.color.primaryBackground),
                backgroundColor = Odyssey.color.primaryBackground
            )

            setNavigationContent(configuration) {
                navigationGraph()
            }
        }
    }
}
```