To create simple screen inside navigation graph just use code like this

```kotlin
fun RootComposeBuilder.generateGraph() {
    screen(name = "splash") {
        SplashScreen() // <- Your composable screen
    }
}
```