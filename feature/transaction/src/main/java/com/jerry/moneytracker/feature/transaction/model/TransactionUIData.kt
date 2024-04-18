package com.jerry.moneytracker.feature.transaction.model

import android.os.Parcelable
import com.jerry.moneytracker.core.model.data.Transaction

import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionUIData (
    val listOfYearMonth: List<YearMonthItem> = emptyList(),
    val transactionList: List<TransactionGroup> = emptyList(),
    val transaction: Transaction? = null,
): Parcelable
