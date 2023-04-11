package com.example.benchmarks.models.benchmark

class TestOperationsMaps : OperationsMaps() {
    override fun measureTime(sizeOfCollection: Int, item: BenchmarkItem): Long {
        try {
            Thread.sleep(Tests.DELAY)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return Tests.MEASURE_TIME
    }
}