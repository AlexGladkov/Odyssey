Flow is a chain of screens connected with one root controller with his own backstack

```kotlin
fun RootComposeBuilder.generateGraph() {
    // Previous screens ...

    flow(name = "auth_flow") {
        screen(name = "login") {
            // Params available by design in Any? type
            LoginScreen(rootController, it as? String)
        }

        screen(name = "login_code") {
            LoginCodeScreen(rootController, it as? String)
        }
    }
}
```

To call this screen in your code just use this from your current screen
```kotlin
    val rootController = LocalRootController.current
    rootController.present("auth_flow")
```

Or if you try to show flow above current flow you need to call new flow from rootController navigation
```kotlin
    val rootController = LocalRootController.current
    rootController.findRootController().present("auth_flow")
```