package com.jerry.moneytracker.core.datastore.di

import android.content.Context
import com.jerry.moneytracker.core.datastore.datasource.SettingPreferenceDataSource
import com.jerry.moneytracker.core.datastore.datasource.SettingPreferenceDataSourceImpl

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object  DatastoreModule {

    @Provides
    @Singleton
    fun provideSettingPreferenceDataSource(
        @ApplicationContext context: Context
    ): SettingPreferenceDataSource {
        return SettingPreferenceDataSourceImpl(context)
    }
}