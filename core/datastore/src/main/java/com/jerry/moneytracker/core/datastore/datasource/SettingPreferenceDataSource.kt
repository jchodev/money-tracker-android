package com.jerry.moneytracker.core.datastore.datasource

import com.jerry.moneytracker.core.datastore.model.SettingPreference
import kotlinx.coroutines.flow.Flow

interface SettingPreferenceDataSource {
    suspend fun saveSettingPreference(settingPreference: SettingPreference)
    fun getSettingPreference(): Flow<SettingPreference>
}