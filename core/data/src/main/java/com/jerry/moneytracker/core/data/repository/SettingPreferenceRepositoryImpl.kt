package com.jerry.moneytracker.core.data.repository

import com.jerry.moneytracker.core.common.network.Dispatcher
import com.jerry.moneytracker.core.common.network.MmasDispatchers
import com.jerry.moneytracker.core.data.model.toSettingPreference
import com.jerry.moneytracker.core.datastore.datasource.SettingPreferenceDataSource
import com.jerry.moneytracker.core.datastore.model.toSetting
import com.jerry.moneytracker.core.domain.repository.SettingPreferenceRepository
import com.jerry.moneytracker.core.model.data.Setting
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject


class SettingPreferenceRepositoryImpl @Inject constructor(
    private val settingPreferenceDataSource: SettingPreferenceDataSource,
    @Dispatcher(MmasDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): SettingPreferenceRepository {

    override suspend fun saveSetting(setting: Setting) {
        withContext(ioDispatcher){
            settingPreferenceDataSource.saveSettingPreference(
                setting.toSettingPreference()
            )
        }
    }

    override suspend fun getSetting(): Flow<Setting> {
        return withContext(ioDispatcher) {
            settingPreferenceDataSource.getSettingPreference().map {
                it.toSetting()
            }
        }
    }
}