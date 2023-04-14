package com.example.benchmarks.models.benchmark

class TestOperationsCollections : OperationsCollections() {
    override fun measureTime(sizeOfCollection: Int, item: BenchmarkItem): Long {
        try {
            Thread.sleep(Tests.DELAY)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return Tests.MEASURE_TIME
    }
}