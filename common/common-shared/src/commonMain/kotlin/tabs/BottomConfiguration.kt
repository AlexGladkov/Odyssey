package tabs

import androidx.compose.runtime.Composable
import theme.Odyssey
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.*

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

class CustomConfiguration(private val content: @Composable () -> Unit) :
    TabsNavModel<CustomNavConfiguration>() {

    override val navConfiguration: CustomNavConfiguration
        @Composable
        get() {
            return CustomNavConfiguration(
               content = content
            )
        }
}

fun RootComposeBuilder.customNavigation(
    name: String,
    tabsNavModel: TabsNavModel<CustomNavConfiguration>,
    builder: MultiStackBuilder.() -> Unit
) {
    addMultiStack(
        key = name,
        tabsNavModel = tabsNavModel,
        multiStackBuilderModel = MultiStackBuilder(name).apply(builder).build()
    )
}