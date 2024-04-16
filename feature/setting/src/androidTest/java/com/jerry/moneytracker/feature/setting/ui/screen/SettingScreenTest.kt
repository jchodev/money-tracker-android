package com.jerry.moneytracker.feature.setting.ui.screen


import android.content.Context

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription

import androidx.compose.ui.test.onNodeWithText

import androidx.test.platform.app.InstrumentationRegistry
import com.jerry.moneytracker.core.designsystem.theme.MoneyTrackerTheme
import com.jerry.moneytracker.core.model.data.Setting
import com.jerry.moneytracker.feature.setting.R
import com.jerry.moneytracker.feature.setting.ui.viewmodel.SettingUIState
import org.junit.Rule
import org.junit.Test

//https://github.com/jchodev/mobile-coverage-2/blob/main/app/src/androidTest/java/com/jerry/assessment/mobilecoverage/presentation/components/MobileCoverageScreenTest.kt
class SettingScreenTest {

    @get:Rule
    val rule = createComposeRule()

//    @get:Rule
//    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun loading_showsLoading() {
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        rule.setContent {
            MoneyTrackerTheme {
                SettingScreen(
                    uiState = SettingUIState.Loading
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
    fun loading_showsSuccess() {
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        rule.setContent {
            MoneyTrackerTheme {
                SettingScreen(
                    uiState = SettingUIState.Success,
                    setting = Setting(
                        countryCode = "HK",
                        dateFormat = "yyyy-MM-dd"
                    )
                )
            }
        }

        //verify
        rule.onNodeWithText(context.resources.getString(R.string.feature_setting_currency)).assertExists()
        rule.onNodeWithText(context.resources.getString(R.string.feature_setting_date_format)).assertExists()
        rule.onNodeWithText("yyyy-MM-dd").assertExists()
        rule.onNodeWithText(context.resources.getString(R.string.feature_setting_time_format)).assertExists()
        rule.onNodeWithText(context.resources.getString(R.string.feature_setting_theme)).assertExists()

    }
}