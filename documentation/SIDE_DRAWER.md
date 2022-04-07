To work with side navigation you should use custom navigation inside graph builder

### Prerequisite
Drawer as well as Every tab has some visual characteristics like `contentColor`

To setup this you need make tabs configuration. To create this you should inherit TabsNavModel<CustomNavConfiguration>() class
and override desired parameters. `navConfiguration` has Composable get, so you can use any `LocalProviders` you want

For example:
```kotlin
fun RootComposeBuilder.customNavScreen() {
    customNavigation(name = "drawer", tabsNavModel = CustomConfiguration(
        content = { DrawerScreen() }
    )) {
        tab(FeedTab()) {
            screen(name = "tab") {
                TabScreen(it as? Int)
            }
        }
        tab(SearchTab()) {
            screen(name = "tab") {
                TabScreen(it as? Int)
            }
        }
        tab(CartTab()) {
            screen(name = "tab") {
                TabScreen(it as? Int)
            }
        }
    }
}
```
