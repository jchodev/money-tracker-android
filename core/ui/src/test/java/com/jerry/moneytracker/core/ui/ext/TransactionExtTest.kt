package com.jerry.moneytracker.core.ui.ext

import com.jerry.moneytracker.core.model.data.Setting
import com.jerry.moneytracker.core.model.data.TimeFormatType
import com.jerry.moneytracker.core.model.data.Transaction
import com.jerry.moneytracker.core.model.data.TransactionType
import com.jerry.moneytracker.core.testing.tubs.TransactionsDataTestTubs
import com.jerry.moneytracker.core.ui.constants.ColorConstant
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
class TransactionExtTest {

    @Test
    fun `convertMillisToDate should format the date correctly with yyyy-MM-dd format`() {
        val millis = TransactionsDataTestTubs.mockTransactionDateMillis // 2022-06-14 (timestamp in milliseconds)
        val expectedDate = "2022-06-14"
        val actualDate = millis.convertMillisToDate("yyyy-MM-dd")

        assertEquals(expectedDate, actualDate)
    }

    @Test
    fun `convertMillisToDate should format the date correctly with custom format`() {
        val millis = TransactionsDataTestTubs.mockTransactionDateMillis // 2022-06-14 (timestamp in milliseconds)
        val expectedDate = "2022-06-14"
        val actualDate = millis.convertMillisToDate("dd/MM/yyyy")

        assertEquals(expectedDate, actualDate)
    }

    @Test
    fun `displayDateTime should show only time for the current date`() {
        val transaction = Transaction(date =  TransactionsDataTestTubs.mockTransactionDateMillis, hour = 10, minute = 0)
        val setting = Setting(timeFormatType = TimeFormatType.HOUR_12) // Assuming default time format
        val expectedTime = "10:00 AM" // Replace with appropriate time for testing

        val actualDateTime = transaction.displayDateTime(setting)

        assertEquals(expectedTime, actualDateTime)
    }

    @Test
    fun `displayDateTime should show full date and time for past date`() {
        val transaction = Transaction(date =  TransactionsDataTestTubs.mockTransactionDateMillis, hour = 10, minute = 0)
        val setting = Setting(timeFormatType = TimeFormatType.HOUR_12, dateFormat = "yyyy-MM-dd" ) // Assuming default time format

        var expectedDateTime = "2022-06-14 10:00 AM" // Replace with appropriate date and time for testing
        var actualDateTime = transaction.displayDateTime(setting, showTimeOnly = false)
        assertEquals(expectedDateTime, actualDateTime)
    }

    @Test
    fun `formatAmount should format income amount with a plus sign`() {
        val transaction = Transaction(type = TransactionType.INCOME, amount = 100000.00)
        val setting = Setting(countryCode = "HK") // Assuming default currency format
        val expectedAmount = "+ HK$ 1,000.00" // Replace with appropriate currency symbol based on setting

        val actualAmount = transaction.formatAmount(setting, withPlus = true)

        assertEquals(expectedAmount, actualAmount)
    }

    @Test
    fun `formatAmount should format income amount without a plus sign`() {
        val transaction = Transaction(type = TransactionType.INCOME, amount = 100000.00)
        val setting = Setting(countryCode = "HK") // Assuming default currency format
        val expectedAmount = "HK$ 1,000.00" // Replace with appropriate currency symbol based on setting

        val actualAmount = transaction.formatAmount(setting, withPlus = false)

        assertEquals(expectedAmount, actualAmount)
    }

    @Test
    fun `formatAmount should format expense amount`() {
        val transaction = Transaction(type = TransactionType.EXPENSES, amount = 100000.00)
        val setting = Setting(countryCode = "HK") // Assuming default currency format
        val expectedAmount = "- HK$ 1,000.00" // Replace with appropriate currency symbol based on setting

        val actualAmount = transaction.formatAmount(setting, withPlus = true)

        assertEquals(expectedAmount, actualAmount)
    }

    @Test
    fun `getColors should return income colors for income transaction type`() {
        val incomeTransactionType = TransactionType.INCOME
        val expectedColors = ColorConstant.IncomeColors

        val actualColors = incomeTransactionType.getColors()

        assertEquals(expectedColors, actualColors)
    }

    @Test
    fun `getColors should return expense colors for non-income transaction type`() {
        val expenseTransactionType = TransactionType.EXPENSES
        val expectedColors = ColorConstant.ExpensesColors

        val actualColors = expenseTransactionType.getColors()

        assertEquals(expectedColors, actualColors)
    }

    @Test
    fun `displayHourMinute should format time in 24-hour format with leading zero for minutes`() {
        val setting = Setting(timeFormatType = TimeFormatType.HOUR_24)
        val transaction = Transaction(hour = 10, minute = 5)

        val expectedTime = "10:05"

        val actualTime = transaction.displayHourMinute(setting)

        assertEquals(expectedTime, actualTime)
    }

    @Test
    fun `displayHourMinute should format time in 12-hour format with leading zero for minutes`() {
        val setting = Setting(timeFormatType = TimeFormatType.HOUR_12)
        val transaction = Transaction(hour = 10, minute = 5)

        val expectedTime = "10:05 AM"

        val actualTime = transaction.displayHourMinute(setting)

        assertEquals(expectedTime, actualTime)
    }

}