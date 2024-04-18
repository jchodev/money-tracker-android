package com.jerry.moneytracker.feature.transaction.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jerry.moneytracker.core.designsystem.theme.MoneyTrackerTheme
import com.jerry.moneytracker.core.designsystem.theme.dimens
import com.jerry.moneytracker.core.model.data.Setting
import com.jerry.moneytracker.core.model.data.Transaction
import com.jerry.moneytracker.core.model.data.TransactionType
import com.jerry.moneytracker.core.ui.component.TransactionHeader
import com.jerry.moneytracker.core.ui.component.TransactionItem
import com.jerry.moneytracker.core.ui.component.TransactionItemWithRemove
import com.jerry.moneytracker.core.ui.constants.ColorConstant
import com.jerry.moneytracker.core.ui.ext.convertMillisToDate
import com.jerry.moneytracker.core.ui.ext.formatAmount
import com.jerry.moneytracker.core.ui.preview.DevicePreviews
import com.jerry.moneytracker.feature.transaction.model.TransactionGroup

import java.util.Calendar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionsList(
    modifier:Modifier = Modifier,
    setting: Setting = Setting(),
    transactionData: List<TransactionGroup> = listOf(),
    onDelete: (Transaction) -> Unit = {},
    supportDelete: Boolean = true
) {
    Box(
      modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()) {
            transactionData.forEachIndexed { index, group->
                stickyHeader {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.background)) {
                        if (index > 0) {
                            Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen8))
                        }
                        TransactionHeader(
                            bgColor = MaterialTheme.colorScheme.surfaceVariant,
                            leftText = group.date.convertMillisToDate(setting.dateFormat),
                            rightText = group.totalAmount.formatAmount(setting = setting, withPlus = true),
                            rightTextColor =  if (group.totalAmount < 0.0) ColorConstant.ExpensesRed else ColorConstant.IncomeGreen,
                        )
                        Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen8))
                    }
                }

                items(
                    items = group.transactions
                ) {
                    if (supportDelete) {
                        TransactionItemWithRemove(
                            setting = setting,
                            transaction = it,
                            onDelete = onDelete
                        )
                    }
                    else {
                        TransactionItem(
                            setting = setting,
                            transaction = it,
                        )
                    }
                }
            }

        }
    }
}

@DevicePreviews
@Composable
private fun LazyColumnWithStickyHeaderPreview(){

    MoneyTrackerTheme {
        TransactionsList(
            transactionData = listOf(
                TransactionGroup(
                    date = Calendar.getInstance().timeInMillis,
                    totalAmount = 0.0,
                    transactions = listOf(
                        Transaction(
                            id = 1,
                            type = TransactionType.EXPENSES,
                            amount = 1.0,
                            description = "description",
                            date =  Calendar.getInstance().timeInMillis,
                            hour = 1,
                            minute = 1,
                            uri = ""
                        ),
                        Transaction(
                            id = 2,
                            type = TransactionType.EXPENSES,
                            amount = 2.0,
                            description = "description2",
                            date =  Calendar.getInstance().timeInMillis,
                            hour = 2,
                            minute = 1,
                            uri = ""
                        )
                    )
                )
            )
        )
    }

}
