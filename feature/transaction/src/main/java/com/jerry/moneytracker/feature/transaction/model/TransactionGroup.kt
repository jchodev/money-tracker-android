package com.jerry.moneytracker.feature.transaction.model

import android.os.Parcelable
import com.jerry.moneytracker.core.model.data.Transaction

import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionGroup (
    val date: Long = 0,
    val totalAmount: Double = 0.0,
    val transactions: List<Transaction> = listOf(),
) : Parcelable

