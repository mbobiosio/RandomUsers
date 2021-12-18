package com.test.randomusers.utils

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class ExtensionsTest {
    @Test
    fun stringTesting() {
        // sentence case: empty string should return empty string
        assertThat("".toSentenceCase(), `is`(""))

        // sentence case: full name should be equal
        assertThat("JESSIKA NICKEL".toSentenceCase(), `is`("Jessika Nickel"))

        // sentence case: full name with trailing space should be equal
        assertThat("jessika nickel ".toSentenceCase(), `is`("Jessika Nickel"))
    }
}