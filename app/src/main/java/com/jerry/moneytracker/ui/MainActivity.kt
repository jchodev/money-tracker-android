package com.jerry.moneytracker.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface

import androidx.compose.ui.Modifier

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jerry.moneytracker.core.designsystem.theme.MoneyTrackerTheme
import com.jerry.moneytracker.core.model.data.ThemeType
import com.jerry.moneytracker.core.ui.component.LoadingCompose
import com.jerry.moneytracker.feature.setting.ui.viewmodel.FetchSettingDataState
import com.jerry.moneytracker.feature.setting.ui.viewmodel.SettingViewModel
import com.jerry.moneytracker.navigation.MainNavHost
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val settingViewModel by viewModels<SettingViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var dynamicColor = true
            var darkTheme = false
            val setting = settingViewModel.settingState.collectAsStateWithLifecycle().value
            val fetchSettingState = settingViewModel.fetchSettingState.collectAsStateWithLifecycle().value

            when (setting.themeType) {
                ThemeType.DEVICE_THEME -> {

                }
                ThemeType.DARK -> {
                    dynamicColor = false
                    darkTheme = true
                }
                else -> {
                    darkTheme = false
                }
            }

            MoneyTrackerTheme(dynamicColor = dynamicColor, darkTheme = darkTheme) {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Timber.d("MainActivity::fetchSettingState::${fetchSettingState}")
                    when (fetchSettingState) {
                        is FetchSettingDataState.Loading -> LoadingCompose()
                        is FetchSettingDataState.Success -> {
                            MainNavHost(
                                settingViewModel = settingViewModel
                            )
                        }
                        else -> {
                            //TODO
                        }
                    }

                }
            }
        }

        settingViewModel.fetchSetting()
    }
}

