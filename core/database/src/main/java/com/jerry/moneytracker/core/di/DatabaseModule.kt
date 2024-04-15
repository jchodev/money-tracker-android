package com.jerry.moneytracker.core.di

import android.content.Context
import androidx.room.Room
import com.jerry.moneytracker.core.database.MoneyDatabase
import com.jerry.moneytracker.core.database.dao.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideAssessmentDatabase(@ApplicationContext context: Context): MoneyDatabase {
        return Room.databaseBuilder(
            context,
            MoneyDatabase::class.java,
            "MoneyDatabase.db"
        ).build()
    }

    @Provides
    fun providesTransactionDao(
        database: MoneyDatabase,
    ): TransactionDao = database.transactionDao()

}