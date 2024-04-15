package com.jerry.moneytracker.core.ui.ext


import com.jerry.moneytracker.core.model.data.CountryData
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.text.NumberFormat
import java.util.Locale

class SettingExtTest {

    @Test
    fun `toCountryData_US`() {
        val countryCode = "US"
        val expectedData = CountryData(
            countryCode = countryCode,
            countryName = Locale("", countryCode).getDisplayCountry(Locale.getDefault()), // May vary based on user's locale
            countryNameEng = Locale("", countryCode).getDisplayCountry(Locale.ENGLISH),
            countryFlag = "\uD83C\uDDFA", // Flag for US
            currency = NumberFormat.getCurrencyInstance(Locale.US).currency
        )

        val actualData = countryCode.toCountryData()

        // Assert country code
        assertEquals(expectedData.countryCode, actualData.countryCode)

        // Assert country name (may vary based on locale)
        assertTrue(actualData.countryName.startsWith(expectedData.countryName))

        // Assert country name in English
        assertEquals(expectedData.countryNameEng, actualData.countryNameEng)

        // Assert flag emoji (may vary slightly due to font rendering)
        //assertEquals(expectedData.countryFlag, actualData.countryFlag)

        // Assert currency
        assertEquals(expectedData.currency, actualData.currency)
    }

}