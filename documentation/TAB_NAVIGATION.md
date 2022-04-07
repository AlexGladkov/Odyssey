To work with tab navigation you should use topNavigation inside graph builder

### Prerequisite
Tab Navigation Bar as well as Every tab has some visual characteristics like `backgroundColor` and selected/unselected colors

To setup this you need make tabs configuration. To create this you should inherit TabsNavModel<TopNavConfiguration>() class
and override desired parameters. `navConfiguration` has Composable get, so you can use any `LocalProviders` you want

For example with theming:

```kotlin
class TopConfiguration : TabsNavModel<TopNavConfiguration>() {

    override val navConfiguration: TopNavConfiguration
        @Composable
        get() {
            return TopNavConfiguration(
                backgroundColor = Odyssey.color.secondaryBackground,
                contentColor = Odyssey.color.primaryText
            )
        }
}
```

You can use the same approach for every tab. For example:

```kotlin
override val configuration: TabConfiguration

    @Composable
    get() {
        return TabConfiguration(
            title = "Feed",
            selectedColor = Odyssey.color.primaryText,
            unselectedColor = Odyssey.color.controlColor,
            titleStyle = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        )
    }
```

As you can see you have selected and unselected colors both for tabs and for all bottom navigation
It's because tab configuration will override all configuration and if you need specific color for tab you should use in
its own class

And after that you can start you `topNavigation` with navigation graph

```kotlin
fun RootComposeBuilder.topNavScreen() {
    topNavigation(name = "top", tabsNavModel = TopConfiguration()) {
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