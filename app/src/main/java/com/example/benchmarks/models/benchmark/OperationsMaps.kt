package com.example.benchmarks.models.benchmark

import com.example.benchmarks.R
import java.util.*
import javax.inject.Inject

open class OperationsMaps @Inject constructor() : Benchmark {

    override fun measureTime(sizeOfCollection: Int, item: BenchmarkItem): Long {
        val map = createMap(sizeOfCollection, item.nameOfCollection)
        val startTime = System.nanoTime()
        when (item.nameOfOperation) {
            R.string.adding_new_in -> map[map.size] = 1
            R.string.search_by_key_in -> map[map.size / 2]
            R.string.removing_in -> map.remove(map.size / 2)
            else -> throw RuntimeException("This is ID of operation doesn't exist")
        }
        val endTime = System.nanoTime()
        return endTime - startTime
    }

    override fun createBenchmarkList(isProgressBarRunning: Boolean): List<BenchmarkItem> {
        val list: MutableList<BenchmarkItem> = ArrayList()
        for (operation in operationNames) {
            for (collection in mapsNames) {
                list.add(BenchmarkItem(collection, operation, null, isProgressBarRunning))
            }
        }
        return list
    }

    override fun getSpansCount(): Int {
        return mapsNames.size
    }

    private fun createMap(sizeOfCollection: Int, nameOfMap: Int): MutableMap<Int, Int> {
        val map: MutableMap<Int, Int>
        when (nameOfMap) {
            R.string.tree_map -> {
                map = TreeMap()
                fillMap(map, sizeOfCollection)
            }
            R.string.hash_map -> {
                map = HashMap()
                fillMap(map, sizeOfCollection)
            }
            else -> throw RuntimeException("This is ID of maps doesn't exist")
        }
        return map
    }

    private fun fillMap(mapList: MutableMap<Int, Int>, sizeOfMap: Int) {
        for (i in 0 until sizeOfMap) {
            mapList[i] = sizeOfMap
        }
    }

    private val mapsNames: List<Int>
        get() {
            val list: MutableList<Int> = ArrayList()
            list.add(R.string.tree_map)
            list.add(R.string.hash_map)
            return list
        }
    private val operationNames: List<Int>
        get() {
            val list: MutableList<Int> = ArrayList()
            list.add(R.string.adding_new_in)
            list.add(R.string.search_by_key_in)
            list.add(R.string.removing_in)
            return list
        }
}
