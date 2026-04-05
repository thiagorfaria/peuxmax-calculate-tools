package com.peumax.calculatetools.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.peumax.calculatetools.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class CalculatorScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun whenAllInputsAreFilled_resultIsDisplayed() {
        // Wait for splash to navigate (2s) then interact
        composeRule.waitUntil(timeoutMillis = 5000) {
            composeRule.onAllNodes(
                androidx.compose.ui.test.hasText("Diâmetro Superior", substring = true)
            ).fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithText("Diâmetro Superior", substring = true).performTextInput("10")
        composeRule.onNodeWithText("Altura", substring = true).performTextInput("30")
        composeRule.onNodeWithText("Diâmetro Inferior", substring = true).performTextInput("60")

        composeRule.onNodeWithText("39", substring = true).assertIsDisplayed()
    }

    @Test
    fun whenLimparIsClicked_allFieldsAreCleared() {
        composeRule.waitUntil(timeoutMillis = 5000) {
            composeRule.onAllNodes(
                androidx.compose.ui.test.hasText("Diâmetro Superior", substring = true)
            ).fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithText("Diâmetro Superior", substring = true).performTextInput("10")
        composeRule.onNodeWithText("Altura", substring = true).performTextInput("30")
        composeRule.onNodeWithText("Diâmetro Inferior", substring = true).performTextInput("60")

        composeRule.onNodeWithText("Limpar").performClick()

        composeRule.onNodeWithText("—").assertIsDisplayed()
    }
}
