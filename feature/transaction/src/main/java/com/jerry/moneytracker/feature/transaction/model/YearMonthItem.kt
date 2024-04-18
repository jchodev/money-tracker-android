package com.jerry.moneytracker.feature.transaction.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class YearMonthItem (
    val year: Int,
    val month: Int,
    val selected: Boolean = false
): Parcelable