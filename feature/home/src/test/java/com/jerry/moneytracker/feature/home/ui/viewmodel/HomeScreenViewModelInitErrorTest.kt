package com.jerry.moneytracker.feature.home.ui.viewmodel

import app.cash.turbine.test
import com.jerry.moneytracker.core.model.data.AccountBalanceDataType
import com.jerry.moneytracker.core.testing.tubs.ExceptionTestTubs
import com.jerry.moneytracker.core.testing.tubs.TransactionsDataTestTubs

import io.mockk.coEvery
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class HomeScreenViewModelInitErrorTest : BaseHomeScreenViewModelTest()  {

    override fun assignInitResult(){
        coEvery { transactionUseCase.getLatestTransaction() } returns flow{
            throw ExceptionTestTubs.NormalException
        }
        coEvery { transactionUseCase.getSumAmountGroupedByType(AccountBalanceDataType.TOTAL) } returns flowOf(
            TransactionsDataTestTubs.transactionSummary
        )
    }

    @Test
    fun `test HomeScreenViewModel UIState when init(getDataFromDB) collect error`() = runTest {
        viewModel.uiState.test {
            //verify
            val originItem = awaitItem()
            Assertions.assertEquals(false,originItem.loading)
            Assertions.assertEquals(HomeUiData().type, originItem.data.type)
            Assertions.assertEquals(HomeUiData().totalIncome, originItem.data.totalIncome)
            Assertions.assertEquals(HomeUiData().totalExpenses, originItem.data.totalExpenses)
            Assertions.assertEquals(
                HomeUiData().latestTransaction.size,
                originItem.data.latestTransaction.size
            )

            val loadingItem = awaitItem()
            Assertions.assertEquals(true, loadingItem.loading)

            val errorItem = awaitItem()
            Assertions.assertEquals(false, errorItem.loading)
            Assertions.assertEquals(ExceptionTestTubs.exceptionStr,errorItem.exception?.message)
        }
    }


}