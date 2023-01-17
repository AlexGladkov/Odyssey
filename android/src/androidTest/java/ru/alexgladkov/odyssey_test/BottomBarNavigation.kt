package ru.alexgladkov.odyssey_test

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test
import ru.alexgladkov.odyssey_demo.MainActivity
import androidx.compose.ui.test.*
import ru.alexgladkov.common.compose.tests.TestTags

class BottomBarNavigation {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testBottomBarNavigationAndBack() {
        composeTestRule.onNodeWithTag(TestTags.actionScreenBottomNavigation).performClick()
        composeTestRule.activity.onBackPressed()
        composeTestRule.onNodeWithTag(TestTags.actionScreenPush).performClick()
        composeTestRule.activity.onBackPressed()
    }
}