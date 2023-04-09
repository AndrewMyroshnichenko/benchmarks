package com.example.benchmarks.models.benchmark


import javax.inject.Inject
import com.example.benchmarks.R
import java.lang.RuntimeException
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList

open class OperationsCollections @Inject constructor() : Benchmark {

    override fun measureTime(sizeOfCollection: Int, item: BenchmarkItem): Long {
        val list = createCollection(sizeOfCollection, item.nameOfCollection)
        val valueForSearching = 200
        if (item.nameOfOperation == R.string.search_by_value) {
            list[Random().nextInt(sizeOfCollection)] = valueForSearching
        }
        val startTime = System.nanoTime()
        when (item.nameOfOperation) {
            R.string.adding_in_the_beginning -> list.add(0, 1)
            R.string.adding_in_the_middle -> list.add(list.size / 2, valueForSearching)
            R.string.adding_in_the_end -> list.add(1)
            R.string.search_by_value -> list.indexOf(valueForSearching)
            R.string.removing_in_the_beginning -> list.removeAt(0)
            R.string.removing_in_the_middle -> list.removeAt(list.size / 2)
            R.string.removing_in_the_end -> list.removeAt(list.size - 1)
            else -> throw RuntimeException("This is ID of operation doesn't exist")
        }
        val endTime = System.nanoTime()
        return endTime - startTime
    }

    override fun createBenchmarkList(isProgressBarRunning: Boolean): List<BenchmarkItem> {
        val list: MutableList<BenchmarkItem> = ArrayList()
        for (operation in operationNames) {
            for (collection in collectionsNames) {
                list.add(BenchmarkItem(collection, operation, null, isProgressBarRunning))
            }
        }
        return list
    }

    override fun getSpansCount(): Int {
        return collectionsNames.size
    }

    private fun createCollection(sizeOfCollection: Int, nameOfCollection: Int): MutableList<Int> {
        var list: MutableList<Int>? = null
        list = when (nameOfCollection) {
            R.string.array_list -> ArrayList(Collections.nCopies(sizeOfCollection, 0))
            R.string.linked_list -> LinkedList(Collections.nCopies(sizeOfCollection, 0))
            R.string.copy_on_write_array_list -> CopyOnWriteArrayList(Collections.nCopies(sizeOfCollection,0))
            else -> throw RuntimeException("This is ID of collection doesn't exist")
        }
        return list
    }

    private val collectionsNames: List<Int>
        private get() {
            val list: MutableList<Int> = ArrayList()
            list.add(R.string.array_list)
            list.add(R.string.linked_list)
            list.add(R.string.copy_on_write_array_list)
            return list
        }
    private val operationNames: List<Int>
        private get() {
            val list: MutableList<Int> = ArrayList()
            list.add(R.string.adding_in_the_beginning)
            list.add(R.string.adding_in_the_middle)
            list.add(R.string.adding_in_the_end)
            list.add(R.string.search_by_value)
            list.add(R.string.removing_in_the_beginning)
            list.add(R.string.removing_in_the_middle)
            list.add(R.string.removing_in_the_end)
            return list
        }
}