To work with bottom navigation you should use bottomNavigation inside graph builder

### Prerequisite
Bottom Navigation Bar as well as Every tab has some visual characteristics like `backgroundColor` and selected/unselected colors

To setup this you need make tabs configuration. To create this you should inherit TabsNavModel<BottomNavConfiguration> class 
and override desired parameters. `navConfiguration` has Composable get, so you can use any `LocalProviders` you want 

For example with theming: 

```kotlin
class BottomConfiguration : TabsNavModel<BottomNavConfiguration>() {

    override val navConfiguration: BottomNavConfiguration
        @Composable 
        get() {
            return BottomNavConfiguration(
                backgroundColor = Odyssey.color.secondaryBackground,
                selectedColor = Odyssey.color.primaryText,
                unselectedColor = Odyssey.color.controlColor
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

And after that you can start you `bottomNavigation` with navigation graph

```kotlin
bottomNavigation(name = "main", tabsNavModel = BottomConfiguration()) {
        tab(FeedTab()) {
            screen(name = "feed") {
                FeedScreen()
            }
        }
        tab(SearchTab()) {
            screen(name = "search") {
                SearchScreen()
            }

            screen(name = "product") {
                ProductScreen()
            }
        }
        tab(CartTab()) {
            screen(name = "cart") {
                CartScreen()
            }
        }
    }
```