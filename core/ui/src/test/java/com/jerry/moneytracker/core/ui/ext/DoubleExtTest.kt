package com.jerry.moneytracker.core.ui.ext

import com.jerry.moneytracker.core.model.data.Setting
import com.jerry.moneytracker.core.testing.tubs.SettingDataTestTubs
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DoubleExtTest {

    private lateinit var setting: Setting

    @BeforeEach
    fun setUp() {
        setting = SettingDataTestTubs.mockSetting
    }

    @Test
    fun `test formatAmount with amount larger than 0 with expected` (){
        val amount = 100.0
        val actual = amount.formatAmount(
            setting = setting,
            withPlus = false
        )
        Assertions.assertEquals("HK$ 1.00", actual)
    }

    @Test
    fun `test formatAmount with amount larger than 0 with plus with expected` (){
        val amount = 100.0
        val actual = amount.formatAmount(
            setting = setting,
            withPlus = true
        )
        Assertions.assertTrue(actual.startsWith("+"))
        Assertions.assertEquals("+ HK$ 1.00", actual)
    }

    @Test
    fun `test formatAmount with amount smaller than 0 with expected` (){
        val amount = -100.0
        val actual = amount.formatAmount(
            setting = setting
        )
        Assertions.assertTrue(actual.startsWith("-"))
        Assertions.assertEquals("- HK$ 1.00" , actual)
    }

}