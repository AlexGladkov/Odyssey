To create simple screen inside navigation graph just use code like this

**How to read example**
 - [Navigation Graph](/common/common-sample/src/commonMain/kotlin/ru/alexgladkov/common/compose/navigation/NavigationGraph.kt)
 - [Navigation Example](/common/common-sample/src/commonMain/kotlin/ru/alexgladkov/common/compose/screens/ActionsScreen.kt)

```kotlin
fun RootComposeBuilder.navigationGraph() {
    screen(NavigationTree.Actions.name) {
        ActionsScreen(count = 0) // <- Navigation starts here
    }

    screen(NavigationTree.Push.name) {
        ActionsScreen(count = it as? Int) // <- Next Screen
    }
}
```

To call this screen in your code just use this from your current screen
```kotlin
    val rootController = LocalRootController.current
    rootController.push(NavigationTree.Push.name, (count ?: 0) + 1)
```