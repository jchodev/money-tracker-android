package com.jerry.moneytracker.core.data.di


import com.jerry.moneytracker.core.domain.repository.SettingPreferenceRepository
import com.jerry.moneytracker.core.domain.repository.TransactionRepository
import com.jerry.moneytracker.core.data.repository.SettingPreferenceRepositoryImpl
import com.jerry.moneytracker.core.data.repository.TransactionRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryAbstractModule {

    @Binds
    @Singleton
    abstract fun bindTransactionRepository(transactionRepository: TransactionRepositoryImpl): TransactionRepository

    @Binds
    @Singleton
    abstract fun bindSettingPreferenceRepository(settingPreferenceRepository: SettingPreferenceRepositoryImpl): SettingPreferenceRepository

}