package com.example.benchmarks.models.benchmark

import org.junit.Assert.*
import org.junit.Test

class OperationsCollectionsUnitTest {

    private val benchmark: Benchmark = OperationsCollections()

    @Test
    fun testCreatingBenchmarkListReturnsNotNull() {
        val testList = benchmark.createBenchmarkList(false)
        assertNotNull(testList)
    }

    @Test
    fun testCreatingBenchmarkListIsNotEmpty() {
        val testList = benchmark.createBenchmarkList(false)
        assertTrue(testList.isNotEmpty())
    }

    @Test
    fun testMeasureTimeMoreThan0() {
        val testList = benchmark.createBenchmarkList(false)
        for (item in testList) {
            assertTrue(benchmark.measureTime(5000, item) > 0)
        }
    }

    @Test
    fun testMeasureTimeThrowsException() {
        val wrongItem = BenchmarkItem(1, 1, 0L, false)
        assertThrows(RuntimeException::class.java) { benchmark.measureTime(10, wrongItem) }
    }

    @Test
    fun testGetSpansCount() {
        assertEquals(3, benchmark.getSpansCount())
    }
}