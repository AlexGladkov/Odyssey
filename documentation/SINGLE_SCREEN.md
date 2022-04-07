To create simple screen inside navigation graph just use code like this

```kotlin
fun RootComposeBuilder.generateGraph() {
    screen(name = "splash") {
        SplashScreen() // <- Your start screen
    }
    
    screen(name = "next") {
        NextScreen() // <- Your next screen
    }
}
```

To call this screen in your code just use this from your current screen
```kotlin
    val rootController = LocalRootController.current
    rootController.push("next")
```