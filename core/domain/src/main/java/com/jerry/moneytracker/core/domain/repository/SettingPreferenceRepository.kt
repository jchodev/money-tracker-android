package com.jerry.moneytracker.core.domain.repository


import com.jerry.moneytracker.core.model.data.Setting
import kotlinx.coroutines.flow.Flow


interface SettingPreferenceRepository {

    suspend fun saveSetting(setting: Setting)
    suspend fun getSetting(): Flow<Setting>

}