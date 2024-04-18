package com.jerry.moneytracker.feature.transaction.ui.screen

import android.os.Bundle
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jerry.moneytracker.core.common.uistate.UIState
import com.jerry.moneytracker.core.designsystem.theme.MoneyTrackerTheme
import com.jerry.moneytracker.core.designsystem.theme.dimens
import com.jerry.moneytracker.core.designsystem.topbar.MmaTopBar
import com.jerry.moneytracker.core.model.data.Category
import com.jerry.moneytracker.core.model.data.CategoryType
import com.jerry.moneytracker.core.model.data.Setting
import com.jerry.moneytracker.core.model.data.Transaction
import com.jerry.moneytracker.core.model.data.TransactionType
import com.jerry.moneytracker.core.ui.component.ExceptionErrorDialog
import com.jerry.moneytracker.core.ui.component.LoadingCompose
import com.jerry.moneytracker.core.ui.component.SpendFrequencyButton
import com.jerry.moneytracker.core.ui.preview.DevicePreviews
import com.jerry.moneytracker.feature.transaction.R
import com.jerry.moneytracker.feature.transaction.component.TransactionsList
import com.jerry.moneytracker.feature.transaction.component.dialog.TransactionSearchDialog
import com.jerry.moneytracker.feature.transaction.model.TransactionGroup
import com.jerry.moneytracker.feature.transaction.model.TransactionUIData
import com.jerry.moneytracker.feature.transaction.model.YearMonthItem
import com.jerry.moneytracker.feature.transaction.ui.viewmodel.TransactionViewModel

import java.util.Calendar

@Composable
fun TransactionRoute(
    viewModel: TransactionViewModel = hiltViewModel(),
    setting: Setting = Setting()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    var transactionGroupList by remember { mutableStateOf(emptyList<TransactionGroup>()) }

    //searchDialog
    var openSearchDialog by remember { mutableStateOf(false) }
    if (openSearchDialog){
        TransactionSearchDialog(
            setting = setting,
            onDismissRequest = { openSearchDialog = false },
            transactionGroupList = transactionGroupList,
        )
    }

    TransactionScreen(
        uiState = uiState,
        setting = setting,
        onSearchClick = {
            transactionGroupList = it
            openSearchDialog = true
        },
        onYearMonthItemClick = {
            viewModel.getTransactionsByYearMonth(year = it.year, month = it.month)
        },
        onDelete = viewModel::onTractionDelete,
        clearError = viewModel::clearException
    )
}

@Composable
internal fun TransactionScreen(
    uiState : UIState<TransactionUIData> = UIState(data = TransactionUIData()),
    setting: Setting = Setting(),
    onSearchClick: ( List<TransactionGroup>) -> Unit = {},
    onYearMonthItemClick : (YearMonthItem) -> Unit = {},
    onDelete: (Transaction) -> Unit = {},
    clearError: () -> Unit = {}
) {
    val uiDataState = uiState.data

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        TransactionScreenContent(
            uiDataState = uiDataState,
            setting = setting,
            onYearMonthItemClick = onYearMonthItemClick,
            onSearchClick = {
                onSearchClick.invoke(uiDataState.transactionList)
            },
            onDelete = onDelete
        )
        if (uiState.loading){
            LoadingCompose()
        }

        uiState.exception?.let {
            ExceptionErrorDialog(
                exception = it,
                onDismissRequest = clearError,
                onRetryRequest = {
                    uiDataState.transaction?.let{
                        onDelete.invoke(it)
                    }
                },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TransactionScreenContent(
    uiDataState: TransactionUIData = TransactionUIData(),
    setting: Setting = Setting(),
    onYearMonthItemClick : (YearMonthItem) -> Unit = {},
    onSearchClick: () -> Unit = {},
    onDelete: (Transaction) -> Unit = {},
){
    Scaffold (
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            MmaTopBar(
                modifier = Modifier.shadow(elevation = MaterialTheme.dimens.dimen4),
                title = stringResource(id = R.string.feature_transaction_title),
                actions = {
                    // RowScope here, so these icons will be placed horizontally
                    IconButton(onClick = onSearchClick) {
                        Icon(Icons.Filled.Search, contentDescription = null)
                    }
                },
                showBack = false
            )
        }
    ) { paddingValues ->

        Column (modifier = Modifier
            .padding(paddingValues)
            .padding(MaterialTheme.dimens.dimen16))
        {
            LazyRow {
                uiDataState.listOfYearMonth.forEach {
                    item {
                        SpendFrequencyButton(
                            modifier = Modifier.wrapContentSize(),
                            text = it.month.toString() + "/" + it.year.toString(),
                            selected = it.selected,
                            onClick = {
                                onYearMonthItemClick.invoke(it)
                            }
                        )
                        Spacer(modifier = Modifier.width(MaterialTheme.dimens.dimen8))
                    }
                }
            }
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen8))

            TransactionsList(
                setting = setting,
                transactionData = uiDataState.transactionList,
                onDelete = onDelete
            )
        }

    }
}


@DevicePreviews
@Composable
private fun TransactionScreenContentPreview() {
    val bundle = Bundle()
    MoneyTrackerTheme {
        TransactionScreenContent(
            uiDataState = TransactionUIData(
                listOfYearMonth = listOf(
                    YearMonthItem(
                        year = 2024,
                        month = 1,
                        selected  = false,
                    ),
                    YearMonthItem(
                        year = 2024,
                        month = 2,
                        selected  = true,
                    )
                ),
                transactionList = mockTransactionList,
            )
        )
    }

}

val mockTransactionList = listOf(
    TransactionGroup(
        date = Calendar.getInstance().timeInMillis,
        totalAmount = 1.0,
        transactions = listOf(
            Transaction(
                category = Category(type = CategoryType.GIFTS),
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
                category = Category(type = CategoryType.SALARY),
                id = 2,
                type = TransactionType.INCOME,
                amount = 2.0,
                description = "description2",
                date =  Calendar.getInstance().timeInMillis,
                hour = 2,
                minute = 1,
                uri = ""
            )
        )
    ),
    TransactionGroup(
        date = Calendar.getInstance().timeInMillis,
        totalAmount = -2.0,
        transactions = listOf(
            Transaction(
                category = Category(type = CategoryType.GIFTS),
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
                category = Category(type = CategoryType.SALARY),
                id = 2,
                type = TransactionType.INCOME,
                amount = 2.0,
                description = "description2",
                date =  Calendar.getInstance().timeInMillis,
                hour = 2,
                minute = 1,
                uri = ""
            )
        )
    ),
    TransactionGroup(
        date = Calendar.getInstance().timeInMillis,
        totalAmount = 3.0,
        transactions = listOf(
            Transaction(
                category = Category(type = CategoryType.GIFTS),
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
                category = Category(type = CategoryType.SALARY),
                id = 2,
                type = TransactionType.INCOME,
                amount = 2.0,
                description = "description2",
                date =  Calendar.getInstance().timeInMillis,
                hour = 2,
                minute = 1,
                uri = ""
            )
        )
    ),
    TransactionGroup(
        date = Calendar.getInstance().timeInMillis,
        totalAmount = 4.0,
        transactions = listOf(
            Transaction(
                category = Category(type = CategoryType.GIFTS),
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
                category = Category(type = CategoryType.SALARY),
                id = 2,
                type = TransactionType.INCOME,
                amount = 2.0,
                description = "description2",
                date =  Calendar.getInstance().timeInMillis,
                hour = 2,
                minute = 1,
                uri = ""
            )
        )
    ),
    TransactionGroup(
        date = Calendar.getInstance().timeInMillis,
        totalAmount = 5.0,
        transactions = listOf(
            Transaction(
                category = Category(type = CategoryType.GIFTS),
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
                category = Category(type = CategoryType.SALARY),
                id = 2,
                type = TransactionType.INCOME,
                amount = 2.0,
                description = "description2",
                date =  Calendar.getInstance().timeInMillis,
                hour = 2,
                minute = 1,
                uri = ""
            )
        )
    ),
    TransactionGroup(
        date = Calendar.getInstance().timeInMillis,
        totalAmount = 6.0,
        transactions = listOf(
            Transaction(
                category = Category(type = CategoryType.GIFTS),
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
                category = Category(type = CategoryType.SALARY),
                id = 2,
                type = TransactionType.INCOME,
                amount = 2.0,
                description = "description2",
                date =  Calendar.getInstance().timeInMillis,
                hour = 2,
                minute = 1,
                uri = ""
            )
        )
    ),
    TransactionGroup(
        date = Calendar.getInstance().timeInMillis,
        totalAmount = -70.0,
        transactions = listOf(
            Transaction(
                category = Category(type = CategoryType.GIFTS),
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
                category = Category(type = CategoryType.SALARY),
                id = 2,
                type = TransactionType.INCOME,
                amount = 2.0,
                description = "description2",
                date =  Calendar.getInstance().timeInMillis,
                hour = 2,
                minute = 1,
                uri = ""
            )
        )
    ),
)