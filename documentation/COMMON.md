### How to use it with desktop

First you need to create navigation graph. For this use extension function to `RootComposeBuilder`

```kotlin
fun RootComposeBuilder.generateGraph()
```

You can provide array of 3 types of Destinations

#### Create simple screen

```kotlin
fun RootComposeBuilder.generateGraph() {
    screen(name = "splash") {
        SplashScreen(rootController) // <- Your composable screen
    }
}
```

#### Create flow with screens

Flow is a chain of screens connected with one root controller with his own backstack

```kotlin
fun RootComposeBuilder.generateGraph() {
    // Previous screens ...

    flow(name = "auth_flow") {
        screen(name = "login") {
            // Params available by design in Any? type
            LoginScreen(rootController, params as? String)
        }

        screen(name = "login_code") {
            LoginCodeScreen(rootController, params as? String)
        }
    }
}
```

#### Create multi stack with flows

Multistack is a bunch of flow which has their own navigation history

BottomNavigationView for example

You can use it with two ways First, I've created production ready extension `bottomNavigation`

```kotlin
fun RootComposeBuilder.generateGraph() {
    bottomNavigation(
        name = "main_screen",
        bottomItemModels = listOf(
            BottomItemModel(title = "Main"),
            BottomItemModel(title = "Favorite"),
            BottomItemModel(title = "Settings")
        )
    ) {
        tab(name = "main_tab") {
            screen(name = "feed") {
                FeedScreen(rootController)
            }

            screen(name = "detail") {
                DetailScreen(rootController, param = params as DetailParams)
            }
        }

        // You can add more tabs if you need
    }
}
```

Second. If you want to make your own host (for example you need viewpager or something like that)
then use multistack extension

```kotlin
fun RootComposeBuilder.generateGraph() {
    multistack(name = "main", host = { YourHost(this) }) {
        tab("main_tab") {
            screen(name = "feed") {
                FeedScreen(rootController)
            }

            screen(name = "detail") {
                DetailScreen(rootController, param = params as DetailParams)
            }
        }
    }
}
```