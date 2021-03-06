package com.test.randomusers.utils

import com.test.randomusers.data.coroutines.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class MainCoroutineRule(
    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}

@ExperimentalCoroutinesApi
fun MainCoroutineRule.runBlockingTest(block: suspend () -> Unit) {
    this.testDispatcher.runBlockingTest {
        block()
    }
}

@ExperimentalCoroutinesApi
fun MainCoroutineRule.CoroutineScope(): CoroutineScope = CoroutineScope(testDispatcher)

@OptIn(ExperimentalCoroutinesApi::class)
val MainCoroutineRule.testDispatcherProvider
    get() = object : DispatcherProvider {
        override fun main(): CoroutineDispatcher = testDispatcher

        override fun default(): CoroutineDispatcher = testDispatcher

        override fun io(): CoroutineDispatcher = testDispatcher

        override fun unconfined(): CoroutineDispatcher = testDispatcher
    }