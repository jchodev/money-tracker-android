package com.jerry.moneytracker.core.domain.di

import com.jerry.moneytracker.core.domain.repository.SettingPreferenceRepository
import com.jerry.moneytracker.core.domain.repository.TransactionRepository
import com.jerry.moneytracker.core.domain.usecase.CategoriesUseCase
import com.jerry.moneytracker.core.domain.usecase.SettingUseCase
import com.jerry.moneytracker.core.domain.usecase.TransactionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetCategoriesUseCase(): CategoriesUseCase {
        return CategoriesUseCase()
    }

    @Provides
    @Singleton
    fun provideSettingUseCase(settingPreferenceRepository: SettingPreferenceRepository): SettingUseCase {
        return SettingUseCase(
            settingPreferenceRepository = settingPreferenceRepository
        )
    }


    @Provides
    @Singleton
    fun provideTransactionUseCase(
        transactionRepository: TransactionRepository
    ): TransactionUseCase {
        return TransactionUseCase(
            transactionRepository = transactionRepository
        )
    }
}
