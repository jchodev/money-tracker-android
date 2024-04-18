package com.jerry.moneytracker.feature.transaction.ui.screen

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import com.jerry.moneytracker.core.common.uistate.UIState
import com.jerry.moneytracker.core.designsystem.theme.MoneyTrackerTheme
import com.jerry.moneytracker.core.testing.tubs.ExceptionTestTubs
import com.jerry.moneytracker.feature.transaction.model.TransactionUIData
import org.junit.Rule
import org.junit.Test

class TransactionScreenTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun loading_test() {
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        rule.setContent {
            MoneyTrackerTheme {
                TransactionScreen(
                    uiState = UIState(data = TransactionUIData(), loading = true),
                )
            }
        }

        rule
            .onNodeWithContentDescription(
                context.resources.getString(com.jerry.moneytracker.core.designsystem.R.string.core_designsystem_loading)
            )
            .assertExists()
    }

    @Test
    fun error_test() {
        rule.setContent {
            MoneyTrackerTheme {
                TransactionScreen(
                    uiState = UIState(data = TransactionUIData(), exception = ExceptionTestTubs.NormalException),
                )
            }
        }

        //verify
        rule.onNodeWithText(ExceptionTestTubs.exceptionStr).assertExists()
    }

}