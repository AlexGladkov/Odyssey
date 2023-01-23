# Hilt setup

Full work with hilt example [here](/hilt/src/main/java/ru/alexgladkov/hilt_demo/)

First you need to add additional dependency in your android gradle file
```kotlin
implementation("io.github.alexgladkov:odyssey-android:1.3.3") // For android classes
```

All you need is just use another setup in your activity.

```kotlin
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val configuration = OdysseyConfiguration(this)
            setHiltNavigation(configuration) {
                generateGraph()
            }
        }
    }
}
```

And use hiltScreen instead of screen

```kotlin
private fun RootComposeBuilder.generateGraph(): RootComposeBuilder {
    hiltScreen(name = "one") {
        HiltScreenOne()
    }
    hiltScreen(name = "two") {
        HiltScreenTwo()
    }

    return this
}
```

Then just use hiltViewModel function as usual inside your composable function
```kotlin
@Composable
fun HiltScreenOne() {
    val viewModel: ViewModelOne = hiltViewModel()
    val rootController = LocalRootController.current
    TextButton(
        onClick = {
            rootController.push("two")
        }
    ) {
        Text("Push")
    }
    NumberLabel(viewModel.randomLifecycleValue)
}
```