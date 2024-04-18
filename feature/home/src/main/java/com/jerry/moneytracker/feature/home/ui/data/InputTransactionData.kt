package com.jerry.moneytracker.feature.home.ui.data

import android.os.Parcelable
import com.jerry.moneytracker.core.model.data.Transaction
import kotlinx.parcelize.Parcelize

@Parcelize
data class InputTransactionData(

    val transaction: Transaction = Transaction(),

    val isSuccess: Boolean = false,

    val descriptionError: Int? = null,
    val amountString: String = "",
    val amountError: Int? = null,
    val dateError: Int? = null,
    val timeError: Int? = null,
    val categoryError: Int? = null,
): Parcelable
