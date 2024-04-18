package com.jerry.moneytracker.feature.home.ui.component.dialog

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import com.jerry.moneytracker.core.common.uistate.UIState
import com.jerry.moneytracker.core.designsystem.theme.MoneyTrackerTheme
import com.jerry.moneytracker.core.model.data.Setting
import com.jerry.moneytracker.core.testing.tubs.ExceptionTestTubs
import com.jerry.moneytracker.feature.home.ui.data.InputTransactionData
import org.junit.Rule
import org.junit.Test

class InputTransactionDialogTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun loading_test() {
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        rule.setContent {
            MoneyTrackerTheme {
                InputTransactionDialog(
                    uiState = UIState(data = InputTransactionData(), loading = true),
                    setting = Setting(),
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
    fun success_test() {
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        rule.setContent {
            MoneyTrackerTheme {
                InputTransactionDialog(
                    uiState = UIState(data = InputTransactionData().copy(isSuccess = true)),
                    setting = Setting(),
                )
            }
        }

        //verify
        rule.onNodeWithText(context.resources.getString(com.jerry.moneytracker.core.designsystem.R.string.core_designsystem_completed)).assertExists()
    }

    @Test
    fun error_test() {
        rule.setContent {
            MoneyTrackerTheme {
                InputTransactionDialog(
                    uiState = UIState(
                        exception = ExceptionTestTubs.NormalException,
                        data = InputTransactionData()
                    ),
                    setting = Setting(),
                )
            }
        }

        //verify
        rule.onNodeWithText(ExceptionTestTubs.exceptionStr).assertExists()
    }
}