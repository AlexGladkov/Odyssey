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