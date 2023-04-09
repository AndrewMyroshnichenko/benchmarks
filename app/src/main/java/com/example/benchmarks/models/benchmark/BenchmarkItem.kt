package com.example.benchmarks.models.benchmark

import java.util.*

class BenchmarkItem(
    val nameOfCollection: Int,
    val nameOfOperation: Int,
    val durationOfOperation: Long?,
    val isProgressBarRunning: Boolean
) {
    fun updateBenchmarkItem(duration: Long): BenchmarkItem {
        return BenchmarkItem(nameOfCollection, nameOfOperation, duration, false)
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as BenchmarkItem
        return nameOfCollection == that.nameOfCollection && nameOfOperation == that.nameOfOperation && isProgressBarRunning == that.isProgressBarRunning && durationOfOperation == that.durationOfOperation
    }

    override fun hashCode(): Int {
        return Objects.hash(
            nameOfCollection,
            nameOfOperation,
            durationOfOperation,
            isProgressBarRunning
        )
    }
}