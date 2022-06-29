package navigation

import ru.alexgladkov.odyssey.compose.extensions.bottomNavigation
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.extensions.tab
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import screens.TabScreen
import tabs.BottomConfiguration
import tabs.CartTab
import tabs.FeedTab
import tabs.SearchTab

fun RootComposeBuilder. mainScreen() {
    bottomNavigation(name = "main", tabsNavModel = BottomConfiguration()) {
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