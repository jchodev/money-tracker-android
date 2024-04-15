package com.jerry.moneytracker.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jerry.moneytracker.core.database.dao.TransactionDao
import com.jerry.moneytracker.core.database.model.TransactionEntity

@Database(
    entities = [TransactionEntity::class],
    version = 1
)
internal abstract class MoneyDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}