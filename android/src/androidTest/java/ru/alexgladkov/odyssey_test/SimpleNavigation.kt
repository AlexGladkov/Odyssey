package ru.alexgladkov.odyssey_test

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test
import ru.alexgladkov.odyssey_demo.MainActivity
import androidx.compose.ui.test.*
import ru.alexgladkov.common.compose.tests.TestTags

class SimpleNavigation {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testPushAndBack() {
        composeTestRule.onNodeWithTag(TestTags.actionScreenPush).performClick()
        composeTestRule.onNodeWithTag(TestTags.actionScreenPush).performClick()
        composeTestRule.onNodeWithTag(TestTags.actionScreenPush).performClick()
        composeTestRule.onNodeWithTag(TestTags.actionScreenPush).performClick()
        composeTestRule.onNodeWithTag(TestTags.actionScreenPush).performClick()

        composeTestRule.onNodeWithTag(TestTags.actionScreenBack).performClick()
        composeTestRule.onNodeWithTag(TestTags.actionScreenBack).performClick()
        composeTestRule.onNodeWithTag(TestTags.actionScreenBack).performClick()
        composeTestRule.onNodeWithTag(TestTags.actionScreenBack).performClick()
        composeTestRule.onNodeWithTag(TestTags.actionScreenBack).performClick()
    }

    @Test
    fun testPresentAndBack() {
        composeTestRule.onNodeWithTag(TestTags.actionScreenPresent).performClick()
        composeTestRule.onNodeWithTag(TestTags.presentedActionScreenBack).performClick()
    }

    @Test
    fun testPresentAndPush() {
        composeTestRule.onNodeWithTag(TestTags.actionScreenPresent).performClick()
        composeTestRule.onNodeWithTag(TestTags.presentedActionScreenPush).performClick()
        composeTestRule.onNodeWithTag(TestTags.presentedActionScreenPush).performClick()
        composeTestRule.onNodeWithTag(TestTags.presentedActionScreenPush).performClick()
    }

    @Test
    fun testPresentWithBack() {
        composeTestRule.onNodeWithTag(TestTags.actionScreenPresent).performClick()
        composeTestRule.onNodeWithTag(TestTags.presentedActionScreenPush).performClick()
        composeTestRule.onNodeWithTag(TestTags.presentedActionScreenBack).performClick()
        composeTestRule.onNodeWithTag(TestTags.presentedActionScreenBack).performClick()

        composeTestRule.onNodeWithTag(TestTags.actionScreenPush).performClick()
        composeTestRule.onNodeWithTag(TestTags.actionScreenPush).performClick()
        composeTestRule.onNodeWithTag(TestTags.actionScreenPush).performClick()

        composeTestRule.onNodeWithTag(TestTags.actionScreenBack).performClick()
        composeTestRule.onNodeWithTag(TestTags.actionScreenBack).performClick()
        composeTestRule.onNodeWithTag(TestTags.actionScreenBack).performClick()
    }
}