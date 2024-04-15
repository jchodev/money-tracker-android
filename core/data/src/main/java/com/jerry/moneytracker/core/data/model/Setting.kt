package com.jerry.moneytracker.core.data.model

import com.jerry.moneytracker.core.datastore.model.SettingPreference
import com.jerry.moneytracker.core.model.data.Setting

fun Setting.toSettingPreference(): SettingPreference {
    return SettingPreference(
        countryCode = countryCode,
        theme = themeType?.value ?: "",
        dateFormat = dateFormat,
        use24HourFormat = timeFormatType.value,
    )
}