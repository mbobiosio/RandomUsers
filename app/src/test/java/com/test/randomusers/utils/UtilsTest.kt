package com.test.randomusers.utils

import com.test.randomusers.utils.Utils.convertDateToRegisteredFormat
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UtilsTest {

    @Test
    fun `verify that date parsing fails the user registered format`() {
        val serverDate = "2002-06-07T10:00:37.688Z"
        val userSinceDate = "June 07, 2001"
        assertFalse(convertDateToRegisteredFormat(serverDate) == userSinceDate)
    }

    @Test
    fun `verify that date parsing equals the user registered format`() {
        val serverDate = "2002-06-07T10:00:37.688Z"
        val userSinceDate = "June 07, 2002"
        assertEquals(convertDateToRegisteredFormat(serverDate), userSinceDate)
    }
}