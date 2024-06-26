package com.jerry.moneytracker.core.datastore.datasource

import android.content.Context
import androidx.datastore.dataStore
import com.jerry.moneytracker.core.datastore.model.SettingPreference
import com.jerry.moneytracker.core.datastore.serializer.SettingPreferenceSerializer
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingPreferenceDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
): SettingPreferenceDataSource {

    private val Context.dataStore by dataStore(
        "setting-preference.json",
        SettingPreferenceSerializer
    )

    override suspend fun saveSettingPreference(settingPreference: SettingPreference) {
        context.dataStore.updateData { settingPreference }
    }

    override fun getSettingPreference(): Flow<SettingPreference> {
        return context.dataStore.data
    }


}