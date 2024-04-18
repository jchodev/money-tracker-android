package com.jerry.moneytracker.feature.transaction.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jerry.moneytracker.core.common.result.asResult
import com.jerry.moneytracker.core.common.uistate.UIState
import com.jerry.moneytracker.core.domain.usecase.TransactionUseCase
import com.jerry.moneytracker.feature.transaction.model.TransactionUIData

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

import com.jerry.moneytracker.core.common.result.Result
import com.jerry.moneytracker.core.model.data.Transaction
import com.jerry.moneytracker.feature.transaction.model.TransactionGroup
import com.jerry.moneytracker.feature.transaction.model.YearMonthItem

import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val transactionUseCase: TransactionUseCase
): ViewModel(){

    private val _uiState = MutableStateFlow(UIState(data = TransactionUIData()))
    val uiState = _uiState.asStateFlow()

    init {
        getDataFromDB()
    }

    private fun getDataFromDB(){
        viewModelScope.launch {
            var latestYear = 0
            var latestMonth = 0
            transactionUseCase.getListOfYearMonth().asResult().collectLatest{ result ->
                when (result) {
                    is Result.Loading -> {
                        updateUI (
                            uiState = uiState.value.copy(
                                loading = true
                            )
                        )
                    }
                    is Result.Error -> {
                        updateUI (
                            uiState = uiState.value.copy(
                                loading = false,
                                exception = result.exception
                            )
                        )
                    }
                    is Result.Success -> {
                        val listOfYearMonth =  result.data.mapIndexed  {index, yearMonth ->
                            val selected = (index == result.data.lastIndex)
                            if (selected) {
                                latestYear = yearMonth.year
                                latestMonth = yearMonth.month
                            }
                            YearMonthItem(
                                year = yearMonth.year,
                                month = yearMonth.month,
                                selected = selected
                            )
                        }

                        updateUI (
                            uiState = uiState.value.copy(
                                data = uiState.value.data.copy(
                                    listOfYearMonth = listOfYearMonth
                                )
                            )
                        )
                        if (latestYear > 0){
                            getTransactionsByYearMonth(year = latestYear, month = latestMonth)
                        }
                    }
                }
            }
        }
    }

    fun getTransactionsByYearMonth(year: Int, month: Int) {
        viewModelScope.launch {
            transactionUseCase.getAllTransactionByYearMonth(year = year, month = month).asResult()
                .collectLatest { result->
                    when (result) {
                        is Result.Loading -> {
                            updateUI (
                                uiState = uiState.value.copy(
                                    loading = true
                                )
                            )
                        }
                        is Result.Error -> {
                            updateUI (
                                uiState = uiState.value.copy(
                                    loading = false,
                                    exception = result.exception
                                )
                            )
                        }
                        is Result.Success -> {
                            val transactionList = result.data.map { (date, transactions) ->
                                val totalAmount = transactions.sumOf { it.amount }
                                TransactionGroup(date, totalAmount, transactions)
                            }
                            updateUI (
                                uiState = uiState.value.copy(
                                    loading = false,
                                    data = uiState.value.data.copy(
                                        transactionList = transactionList,
                                        listOfYearMonth = uiState.value.data.listOfYearMonth.map { item->
                                            if (item.year == year && item.month == month) {
                                                item.copy(selected = true) // Create a new item with selected set to true
                                            } else {
                                                item.copy(selected = false) // Create a new item with selected set to false (optional)
                                            }
                                        }
                                    )
                                )
                            )
                        }
                    }

                }
        }
    }

    fun clearException(){
        updateUI (
            uiState = uiState.value.copy(
                exception = null
            )
        )
    }

    fun onTractionDelete(transaction: Transaction){
        updateUI (
            uiState = uiState.value.copy(
                data = uiState.value.data.copy(
                    transaction = transaction
                )
            )
        )
        viewModelScope.launch {
            transactionUseCase.deleteTransactionById(id = transaction.id).asResult().collectLatest{ result ->
                when (result) {
                    is Result.Loading -> {
                        updateUI (
                            uiState = uiState.value.copy(
                                loading = true
                            )
                        )
                    }
                    is Result.Error -> {
                        updateUI (
                            uiState = uiState.value.copy(
                                loading = false,
                                exception = result.exception
                            )
                        )
                    }
                    is Result.Success -> {
                        getDataFromDB()
                    }
                }
            }
        }
    }

    private fun updateUI(
        uiState: UIState<TransactionUIData>
    ){
        _uiState.value = uiState
    }
}

