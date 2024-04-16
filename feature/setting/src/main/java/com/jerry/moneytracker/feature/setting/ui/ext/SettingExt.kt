package com.jerry.moneytracker.feature.setting.ui.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.jerry.moneytracker.core.model.data.ThemeType
import com.jerry.moneytracker.core.model.data.TimeFormatType
import com.jerry.moneytracker.feature.setting.R


@Composable
fun TimeFormatType.getString() : String{
    return when (this){
        TimeFormatType.HOUR_12 -> stringResource(R.string.feature_setting_12_hour)
        else -> stringResource(R.string.feature_setting_24_hour)
    }
}

@Composable
fun ThemeType.getString() : String{
    return when (this){
        ThemeType.DARK -> stringResource(R.string.feature_setting_dark)
        ThemeType.LIGHT -> stringResource(R.string.feature_setting_light)
        else -> stringResource(R.string.feature_setting_device_theme)
    }
}
