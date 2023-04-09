package com.example.benchmarks.models.benchmark

interface Benchmark {

    fun getSpansCount(): Int

    fun  createBenchmarkList(isProgressBarRunning: Boolean): List<BenchmarkItem>

    fun measureTime(sizeOfCollection: Int, item: BenchmarkItem): Long

}