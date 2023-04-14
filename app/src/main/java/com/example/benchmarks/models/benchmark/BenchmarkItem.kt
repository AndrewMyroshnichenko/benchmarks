package com.example.benchmarks.models.benchmark

import java.util.*

data class BenchmarkItem(
    val nameOfCollection: Int,
    val nameOfOperation: Int,
    val durationOfOperation: Long?,
    val isProgressBarRunning: Boolean
) {
 //   fun updateBenchmarkItem(duration: Long): BenchmarkItem {
 //       return copy(durationOfOperation =  duration, isProgressBarRunning =  false)
//}

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as BenchmarkItem
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
