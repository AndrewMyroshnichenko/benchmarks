package com.example.benchmarks.models.benchmark

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class BenchmarkItemUnitTest {
    @Test
    fun testUpdateBenchmarkItem() {
        val item = BenchmarkItem(1, 2, 0L, true)
        val newItem = item.updateBenchmarkItem(200L)
        assertEquals(1, newItem.nameOfCollection)
        assertEquals(2, newItem.nameOfOperation)
        assertEquals(200L, newItem.durationOfOperation)
        assertFalse(newItem.isProgressBarRunning)
    }
}



