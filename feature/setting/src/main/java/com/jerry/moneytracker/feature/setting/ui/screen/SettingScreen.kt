package com.jerry.moneytracker.feature.setting.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jerry.moneytracker.core.designsystem.dialog.ConfirmDialog
import com.jerry.moneytracker.core.designsystem.dialog.SuccessDialog
import com.jerry.moneytracker.core.designsystem.theme.MoneyTrackerTheme
import com.jerry.moneytracker.core.designsystem.theme.dimens
import com.jerry.moneytracker.core.designsystem.topbar.MmaTopBar
import com.jerry.moneytracker.core.model.data.CountryData
import com.jerry.moneytracker.core.model.data.Setting
import com.jerry.moneytracker.core.model.data.ThemeType
import com.jerry.moneytracker.core.model.data.TimeFormatType
import com.jerry.moneytracker.core.ui.component.ExceptionErrorDialog
import com.jerry.moneytracker.core.ui.component.LoadingCompose
import com.jerry.moneytracker.core.ui.ext.toCountryData
import com.jerry.moneytracker.core.ui.preview.DevicePreviews
import com.jerry.moneytracker.feature.setting.R
import com.jerry.moneytracker.feature.setting.ui.component.SettingItem
import com.jerry.moneytracker.feature.setting.ui.component.dialog.CurrencySelectDialog
import com.jerry.moneytracker.feature.setting.ui.component.dialog.DateFormatDialog
import com.jerry.moneytracker.feature.setting.ui.component.dialog.ThemeDialog
import com.jerry.moneytracker.feature.setting.ui.component.dialog.TimeFormatDialog
import com.jerry.moneytracker.feature.setting.ui.ext.getString
import com.jerry.moneytracker.feature.setting.ui.viewmodel.ClearDataUIState
import com.jerry.moneytracker.feature.setting.ui.viewmodel.SettingViewModel
import timber.log.Timber

@Composable
fun SettingRoute(
    viewModel: SettingViewModel = hiltViewModel()
){
    val clearDataUIState by viewModel.clearDataUIState.collectAsStateWithLifecycle()
    SettingScreen(
        clearDataUIState = clearDataUIState,
        setting = viewModel.settingState.collectAsStateWithLifecycle().value,
        countryList = viewModel.getCountryList(),
        onCountrySelected = {
            viewModel.onCountryDataSelected(it.countryCode)
        },
        dateFormatList = viewModel.getDateFormatList(),
        onDateFormatSelected = viewModel::onDateFormatSelected,
        timeFormatList = viewModel.getTimeFormatList(),
        onTimeFormatSelected = viewModel::onTimeFormatSelected,
        themeList = viewModel.getThemeList(),
        onThemeSelected =  viewModel::onThemeSelected,
        onConfirmClearData = viewModel::clearData,
        onErrorDismiss = viewModel::resultUiState,
        onErrorRetry = viewModel::clearData,
        onSuccessDismiss = viewModel::resultUiState,
    )
}

@Composable
internal fun SettingScreen(
    clearDataUIState: ClearDataUIState,
    setting: Setting = Setting(),
    countryList: List<CountryData> = emptyList(),
    dateFormatList: List<String> = emptyList(),
    timeFormatList: List<TimeFormatType> = emptyList(),
    themeList: List<ThemeType> = emptyList(),
    onCountrySelected: (CountryData) -> Unit = {},
    onDateFormatSelected: (String) -> Unit = {},
    onTimeFormatSelected: (TimeFormatType) -> Unit = {},
    onThemeSelected: (ThemeType) -> Unit = {},
    onConfirmClearData: () -> Unit = {},
    onErrorDismiss:() -> Unit = {},
    onErrorRetry:() -> Unit = {},
    onSuccessDismiss:() -> Unit = {},
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        SettingScreenContent(
            setting = setting,
            countryList = countryList,
            dateFormatList = dateFormatList,
            timeFormatList = timeFormatList,
            themeList = themeList,
            onCountrySelected = onCountrySelected,
            onDateFormatSelected = onDateFormatSelected,
            onTimeFormatSelected = onTimeFormatSelected,
            onThemeSelected= onThemeSelected,
            onConfirmClearData = onConfirmClearData
        )

        when (clearDataUIState) {
            is ClearDataUIState.Loading -> LoadingCompose()
            is ClearDataUIState.Error -> {
                ExceptionErrorDialog(
                    exception = clearDataUIState.exception,
                    onDismissRequest = onErrorDismiss,
                    onRetryRequest = onErrorRetry
                )
            }
            is ClearDataUIState.Success -> {
                SuccessDialog(
                    onDismissRequest = onSuccessDismiss
                )
            }
            else ->{

            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingScreenContent(
    setting: Setting = Setting(),
    countryList: List<CountryData> = emptyList(),
    dateFormatList: List<String> = emptyList(),
    timeFormatList: List<TimeFormatType> = emptyList(),
    themeList: List<ThemeType> = emptyList(),
    onCountrySelected: (CountryData) -> Unit = {},
    onDateFormatSelected: (String) -> Unit = {},
    onTimeFormatSelected: (TimeFormatType) -> Unit = {},
    onThemeSelected: (ThemeType) -> Unit = {},
    onConfirmClearData: () -> Unit = {},
) {
    Scaffold (
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            MmaTopBar(
                modifier = Modifier.shadow(elevation = MaterialTheme.dimens.dimen4),
                title = stringResource(id = R.string.feature_setting_setting),
                showBack = false
            )
        }
    ){
        paddingValues ->

        //Currency
        var openCountrySelectDialog by remember { mutableStateOf(false) }
        if (openCountrySelectDialog) {
            CurrencySelectDialog(
                countryList = countryList,
                onDismissRequest = { openCountrySelectDialog = false },
                onSelected = {
                    openCountrySelectDialog = false
                    Timber.d("selected Country is ${it}")
                    onCountrySelected.invoke(it)
                },
                defaultCountryCode = setting.countryCode,
            )
        }

        //date format
        var openDateFormatSelectDialog by remember { mutableStateOf(false) }
        if (openDateFormatSelectDialog) {
            DateFormatDialog(
                itemList = dateFormatList,
                onDismissRequest = { openDateFormatSelectDialog = false },
                onItemSelected = {
                    openDateFormatSelectDialog = false
                    Timber.d("selected data format is ${it}")
                    onDateFormatSelected.invoke(it)
                },
                defaultItem = setting.dateFormat,
            )
        }

        //time format
        var openTimeFormatSelectDialog by remember { mutableStateOf(false) }
        if (openTimeFormatSelectDialog) {
            TimeFormatDialog(
                itemList = timeFormatList,
                onDismissRequest = { openTimeFormatSelectDialog = false },
                onItemSelected = {
                    openTimeFormatSelectDialog = false
                    Timber.d("selected time format is ${it}")
                    onTimeFormatSelected.invoke(it)
                },
                defaultItem = setting.timeFormatType
            )
        }

        //theme
        var openThemeSelectDialog by remember { mutableStateOf(false) }
        if (openThemeSelectDialog) {
            ThemeDialog(
                itemList = themeList,
                onDismissRequest = { openThemeSelectDialog = false },
                onItemSelected = {
                    openTimeFormatSelectDialog = false
                    Timber.d("selected theme is ${it}")
                    onThemeSelected.invoke(it)
                },
                defaultItem = setting.themeType
            )
        }

        //clear data
        var clearDataDialog by remember { mutableStateOf(false) }
        if (clearDataDialog) {
            ConfirmDialog(
                title = null,
                text = stringResource(id = R.string.feature_setting_are_you_sure_delete_all_transaction),
                onConfirmRequest = {
                    onConfirmClearData.invoke()
                    clearDataDialog = false
                },
                onDismissRequest = { clearDataDialog = false },
            )
        }

        LazyColumn (modifier = Modifier
            .padding(paddingValues) ){
            //Currency
            item {
                SettingItem(
                    title = stringResource(id = R.string.feature_setting_currency),
                    selectedValue = setting.countryCode.toCountryData().currency.symbol,
                    onClick = {
                        openCountrySelectDialog = true
                    }
                )
            }
            //Date format
            item {
                SettingItem(
                    title = stringResource(id = R.string.feature_setting_date_format),
                    selectedValue = setting.dateFormat,
                    onClick = {
                        openDateFormatSelectDialog = true
                    }
                )
            }
            //Time format
            item {
                SettingItem(
                    title = stringResource(id = R.string.feature_setting_time_format),
                    selectedValue = setting.timeFormatType.getString(),
                    onClick = {
                        openTimeFormatSelectDialog = true
                    }
                )
            }
            //theme
            item {
                SettingItem(
                    title = stringResource(id = R.string.feature_setting_theme),
                    selectedValue = setting.themeType.getString() ,
                    onClick = {
                        openThemeSelectDialog = true
                    }
                )
            }

            //clear data
            item {
                ListItem(
                    modifier = Modifier
                        .padding(top = MaterialTheme.dimens.dimen8)
                        .fillMaxWidth()
                        .clickable {
                            clearDataDialog = true
                        },
                    headlineContent = {
                        Text (
                            color = MaterialTheme.colorScheme.onBackground,
                            text = stringResource(id = R.string.feature_setting_delete_all_transaction),
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                )
            }

        }
    }
}

@DevicePreviews
@Composable
private fun SettingScreenContentPreview(){
    MoneyTrackerTheme {
        SettingScreenContent()
    }
}