package com.jerry.moneytracker.feature.setting.ui.viewmodel

import app.cash.turbine.test
import com.jerry.moneytracker.core.domain.usecase.SettingUseCase
import com.jerry.moneytracker.core.domain.usecase.TransactionUseCase
import com.jerry.moneytracker.core.model.data.ThemeType
import com.jerry.moneytracker.core.model.data.TimeFormatType
import com.jerry.moneytracker.core.testing.tubs.ExceptionTestTubs
import com.jerry.moneytracker.core.testing.tubs.SettingDataTestTubs

import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.every

import io.mockk.mockkClass
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import timber.log.Timber

@OptIn(ExperimentalCoroutinesApi::class)
class SettingViewModelTest {

    private lateinit var viewModel : SettingViewModel
    private lateinit var settingUseCase: SettingUseCase
    private lateinit var transactionUseCase: TransactionUseCase

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        settingUseCase = mockkClass(SettingUseCase::class)
        transactionUseCase = mockkClass(TransactionUseCase::class)
        mockkStatic(Timber::class)

        coJustRun { settingUseCase.saveSetting(any()) }

        viewModel = SettingViewModel(
            settingUseCase = settingUseCase,
            transactionUseCase = transactionUseCase,
        )
    }

    @AfterEach
    fun after() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `test SettingViewModel fetchSetting with success`() = runTest {
        //assign
        coEvery { settingUseCase.getSetting() } returns flowOf(SettingDataTestTubs.mockSetting)

        viewModel.fetchSettingState.test {
            //action
            viewModel.fetchSetting()

            //verify
            assertEquals(FetchSettingDataState.Loading, awaitItem())

            assertEquals(FetchSettingDataState.Success, awaitItem())
        }
    }

    @Test
    fun `test SettingViewModel fetchSetting with error`() = runTest {
        //assign
        coEvery { settingUseCase.getSetting() } returns flow{
            throw ExceptionTestTubs.NormalException
        }

        //action
        viewModel.fetchSetting()

        viewModel.fetchSettingState.test {

            //verify
            Assertions.assertEquals(FetchSettingDataState.Loading, awaitItem())
            when (val errorResult = awaitItem()) {
                is FetchSettingDataState.Error -> {
                    Assertions.assertEquals(
                        ExceptionTestTubs.exceptionStr,
                        errorResult.exception.message,
                    )
                }
                else -> {}
            }
        }
    }



    @Test
    fun `test SettingViewModel clear data with success`() = runTest {
        //assign
        coEvery { transactionUseCase.deleteAllTransaction() } returns flowOf(Unit)

        viewModel.clearDataUIState.test {
            //action
            viewModel.clearData()

            //verify
            assertEquals(ClearDataUIState.Initial, awaitItem())
            assertEquals(ClearDataUIState.Loading, awaitItem())
            assertEquals(ClearDataUIState.Success, awaitItem())
        }
    }

    @Test
    fun `test SettingViewModel clear data with error`() = runTest {
        //assign
        coEvery { transactionUseCase.deleteAllTransaction() } returns flow{
            throw ExceptionTestTubs.NormalException
        }

        //action
        viewModel.clearData()

        viewModel.clearDataUIState.test {
            //verify
            assertEquals(ClearDataUIState.Initial, awaitItem())
            assertEquals(ClearDataUIState.Loading, awaitItem())
            when (val errorResult = awaitItem()) {
                is ClearDataUIState.Error -> {
                    Assertions.assertEquals(
                        ExceptionTestTubs.exceptionStr,
                        errorResult.exception.message,
                    )
                }
                else -> {}
            }
        }
    }


    @Test
    fun `test SettingViewModel getCountryList`(){
        every { settingUseCase.getCountryList() } returns emptyList()

        //action
        val actualResult = viewModel.getCountryList()

        //verify
        Assertions.assertTrue( actualResult.isEmpty())
    }

    @Test
    fun `test SettingViewModel getDateFormatList`(){
        every { settingUseCase.getDateFormatList() } returns emptyList()

        //action
        val actualResult = viewModel.getDateFormatList()

        //verify
        Assertions.assertTrue( actualResult.isEmpty())
    }

    @Test
    fun `test SettingViewModel getTimeFormatList`(){
        every { settingUseCase.getTimeFormatList() } returns emptyList()

        //action
        val actualResult = viewModel.getTimeFormatList()

        //verify
        Assertions.assertTrue( actualResult.isEmpty())
    }

    @Test
    fun `test SettingViewModel getThemeList`(){
        every { settingUseCase.getThemeList() } returns emptyList()

        //action
        val actualResult = viewModel.getThemeList()

        //verify
        Assertions.assertTrue( actualResult.isEmpty())
    }

    @Test
    fun `test SettingViewModel onCountryDataSelected`() = runTest{
        //assign
        val newValue = ""
        //action
        viewModel.onCountryDataSelected(newValue)
        viewModel.settingState.test {
            val actual = awaitItem()
            //verifiy
            Assertions.assertEquals(newValue, actual.countryCode)
        }
    }

    @Test
    fun `test SettingViewModel onDateFormatSelected`() = runTest{
        //assign
        val newValue = ""
        //action
        viewModel.onDateFormatSelected(newValue)
        viewModel.settingState.test {
            val actual = awaitItem()
            //verifiy
            Assertions.assertEquals(newValue, actual.dateFormat)
        }
    }

    @Test
    fun `test SettingViewModel onTimeFormatSelected`() = runTest{
        //assign
        val newValue = TimeFormatType.HOUR_12
        //action
        viewModel.onTimeFormatSelected(newValue)
        viewModel.settingState.test {
            val actual = awaitItem()
            //verifiy
            Assertions.assertEquals(newValue, actual.timeFormatType)
        }
    }

    @Test
    fun `test SettingViewModel onThemeSelected`() = runTest{
        //assign
        val newValue = ThemeType.DEVICE_THEME
        //action
        viewModel.onThemeSelected(newValue)
        viewModel.settingState.test {
            val actual = awaitItem()
            //verifiy
            Assertions.assertEquals(newValue, actual.themeType)
        }
    }
}