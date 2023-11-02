### Getting Started

1. Add dependencies to gradle

```kotlin
named("commonMain") {
    dependencies {
        implementation("io.github.alexgladkov:odyssey-core:1.3.31") // For core classes
        implementation("io.github.alexgladkov:odyssey-compose:1.3.31") // For compose extensions
    }
}
```

2. Make navigation graph

```kotlin
fun RootComposeBuilder.navigationGraph() {
    screen("splash") { SplashScreen() }
    screen("login") { LoginScreen() }
    screen("main") { MainScreen() }
}
```

3. Setup `RootController`

```kotlin
setContent { // or any composable input
    val configuration = OdysseyConfiguration(canvas = this)

    setNavigationContent(configuration) {
        navigationGraph()
    }
}
```

4. Perform simple navigation

```kotlin
@Composable
fun SplashScreen() {
    val rootController = LocalRootController.current
    rootController.push("login")
}
```