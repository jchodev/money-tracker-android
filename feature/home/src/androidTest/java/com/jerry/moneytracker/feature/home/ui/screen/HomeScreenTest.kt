package com.jerry.moneytracker.feature.home.ui.screen

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import com.jerry.moneytracker.core.common.uistate.UIState
import com.jerry.moneytracker.core.designsystem.theme.MoneyTrackerTheme
import com.jerry.moneytracker.core.testing.tubs.ExceptionTestTubs
import com.jerry.moneytracker.feature.home.ui.viewmodel.HomeUiData
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun loading_test() {
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        rule.setContent {
            MoneyTrackerTheme {
                HomeScreen(
                    uiState =  UIState(
                        loading = true,
                        data = HomeUiData()
                    )
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
                HomeScreen(
                    uiState =  UIState(
                        exception = ExceptionTestTubs.NormalException,
                        data = HomeUiData()
                    )
                )
            }
        }

        //verify
        rule.onNodeWithText(ExceptionTestTubs.exceptionStr).assertExists()
    }
}