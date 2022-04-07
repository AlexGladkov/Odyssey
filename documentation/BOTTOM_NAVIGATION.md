To work with bottom navigation you should use bottomNavigation inside graph builder

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

All params can be configured through special class you should to inherit
For example like that
```kotlin
class BottomConfiguration : BottomNavModel() {

    override val bottomNavConfiguration: BottomNavConfiguration
        @Composable
        get() {
            return BottomNavConfiguration(
                backgroundColor = Color.White,
                selectedColor = Color.DarkGray,
                unselectedColor = Color.LightGray
            )
        }
}
```

For tab configuration you should inherit from another class
Example
```kotlin
class FeedTab : TabItem() {

    override val configuration: TabConfiguration
        @Composable
        get() {
            return TabConfiguration(
                title = "Feed",
                selectedColor = Color.DarkGray,
                unselectedColor = Color.LightGray,
                titleStyle = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }
}
```

As you can see you have selected and unselected colors both for tabs and for all bottom navigation
It's because tab configuration will override all configuration and if you need specific color for tab you should use in
its own class