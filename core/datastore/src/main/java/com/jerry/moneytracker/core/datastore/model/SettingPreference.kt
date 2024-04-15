package com.jerry.moneytracker.core.datastore.model


import com.jerry.moneytracker.core.model.data.Setting
import com.jerry.moneytracker.core.model.data.ThemeType
import com.jerry.moneytracker.core.model.data.TimeFormatType
import kotlinx.serialization.Serializable


@Serializable
data class SettingPreference(
    val countryCode : String = "",
    val theme: String = "",
    val dateFormat: String = "",
    val use24HourFormat: Boolean = true,
)

fun SettingPreference.toSetting(): Setting {
    return Setting (
        countryCode = countryCode,
        themeType = ThemeType.fromValue(theme) ?: ThemeType.DEVICE_THEME,
        dateFormat = dateFormat,
        timeFormatType = TimeFormatType.fromValue(use24HourFormat) ?: TimeFormatType.HOUR_24
    )
}