To handle params you can use implicit receiver inside any Odyssey screen function such as 
`screen`, `flow` or `bottomNavigation`

For example:
```kotlin
screen("actions") { // it: Any? ->
    ActionsScreen(count = it as Int)
}

@Composable
fun ActionsScreen(count: Int) {
    // Your content here
}
```

```kotlin
val rootController = LocalRootController.current
rootController.push(screen = "actions", params = 0)
```