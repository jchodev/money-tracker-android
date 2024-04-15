package com.jerry.moneytracker.core.data.repository

import com.jerry.moneytracker.core.datastore.datasource.SettingPreferenceDataSource
import com.jerry.moneytracker.core.datastore.model.SettingPreference
import com.jerry.moneytracker.core.datastore.model.toSetting
import com.jerry.moneytracker.core.model.data.Setting
import com.jerry.moneytracker.core.data.repository.SettingPreferenceRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
@MockKExtension.ConfirmVerification
class SettingPreferenceRepositoryImplTest {

    @MockK
    private lateinit var settingPreferenceDataSource: SettingPreferenceDataSource

    private lateinit var settingPreferenceRepositoryImpl: SettingPreferenceRepositoryImpl

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        settingPreferenceRepositoryImpl = SettingPreferenceRepositoryImpl(
            settingPreferenceDataSource = settingPreferenceDataSource,
            ioDispatcher = Dispatchers.IO
        )
    }


    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test SettingPreferenceRepositoryImpl deleteAllTransaction success`() = runTest {

        //assign
        coJustRun { settingPreferenceDataSource.saveSettingPreference(any()) }

        val setting = Setting()
        //action
        settingPreferenceRepositoryImpl.saveSetting(setting)

        coVerify(exactly = 1) { settingPreferenceDataSource.saveSettingPreference(any()) }
    }

    @Test
    fun `test SettingPreferenceRepositoryImpl getSetting success`() = runTest {
        val mockSettingPreference = SettingPreference().copy(use24HourFormat = false)
        //assign
        coEvery { settingPreferenceDataSource.getSettingPreference() } returns flowOf(mockSettingPreference)

        //action
        val result = settingPreferenceRepositoryImpl.getSetting().first()

        Assertions.assertEquals(mockSettingPreference.toSetting(), result)
    }
}