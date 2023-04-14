package com.example.benchmarks.ui.benchmark

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.benchmarks.MainThreadRule
import com.example.benchmarks.R
import com.example.benchmarks.models.benchmark.Benchmark
import com.example.benchmarks.models.benchmark.BenchmarkItem
import com.example.benchmarks.ui.benchmark.BenchmarksViewModel.Companion.isNumberCorrect
import com.example.benchmarks.utils.DispatchersHolder
import com.example.benchmarks.utils.DispatchersHolderImpl
import com.example.benchmarks.utils.Pair
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class BenchmarksViewModelTest {

    companion object {
        private const val SPANS_COUNT = 3
        private const val COLLECTION_SIZE = 10
    }

    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainThreadRule = MainThreadRule()

    private lateinit var viewModel: BenchmarksViewModel
    private lateinit var mockItemsLiveData: Observer<List<BenchmarkItem>>
    private lateinit var mockCalculationStartLiveData: Observer<Boolean>
    private lateinit var mockBenchmark: Benchmark
    private lateinit var dispatchers: DispatchersHolder

    @Before
    fun setup() {
        dispatchers = DispatchersHolderImpl()
        mockBenchmark = mock(Benchmark::class.java)
        mockItemsLiveData = mock(Observer::class.java) as Observer<List<BenchmarkItem>>
        mockCalculationStartLiveData = mock(Observer::class.java) as Observer<Boolean>
        viewModel = BenchmarksViewModel(mockBenchmark, dispatchers)
    }

    private fun setWhenAndObserveForever(list: List<BenchmarkItem>) {
        `when`(mockBenchmark.createBenchmarkList(false)).thenReturn(list)
        `when`(mockBenchmark.createBenchmarkList(true)).thenReturn(list)
        `when`(mockBenchmark.getSpansCount()).thenReturn(SPANS_COUNT)
        `when`(mockBenchmark.measureTime(COLLECTION_SIZE, list[0])).thenAnswer { invocation ->
            Thread.sleep(1000)
            10L
        }

        viewModel.getItemsLiveData().observeForever(mockItemsLiveData)
        viewModel.getCalculationStartLiveData().observeForever(mockCalculationStartLiveData)
    }

    private fun commonVerifyNoMoreInteractions() {
        verifyNoMoreInteractions(mockCalculationStartLiveData)
        verifyNoMoreInteractions(mockItemsLiveData)
        verifyNoMoreInteractions(mockBenchmark)
    }

    @Test
    fun testOnCreate() {
        val listOfItems = mutableListOf<BenchmarkItem>()
        val item = BenchmarkItem(R.string.array_list, R.string.adding_in_the_beginning, 0L, false)
        listOfItems.add(item)

        setWhenAndObserveForever(listOfItems)

        viewModel.onCreate()

        verify(mockBenchmark).createBenchmarkList(false)
        verify(mockItemsLiveData).onChanged(listOfItems)
        verify(mockCalculationStartLiveData).onChanged(false)
        assertEquals(listOfItems, viewModel.getItemsLiveData().value)

        commonVerifyNoMoreInteractions()
    }

    @Test
    fun testOnButtonToggle() {
        val listOfItems = mutableListOf<BenchmarkItem>()
        val item = BenchmarkItem(R.string.array_list, R.string.adding_in_the_beginning, 0L, false)
        listOfItems.add(item)
        setWhenAndObserveForever(listOfItems)

        viewModel.setSizeCollectionLiveData(COLLECTION_SIZE)
        viewModel.onCreate()
        viewModel.onButtonToggle()

        verify(mockCalculationStartLiveData).onChanged(true)
        verify(mockCalculationStartLiveData, times(1)).onChanged(false)
        verify(mockBenchmark).createBenchmarkList(true)
        verify(mockBenchmark).createBenchmarkList(false)
        verify(mockBenchmark).measureTime(COLLECTION_SIZE, item)
        verify(mockItemsLiveData, times(2)).onChanged(anyList())

        assertNotSame(listOfItems, viewModel.getItemsLiveData().value)
        assertFalse(viewModel.getCalculationStartLiveData().value!!)
        assertFalse(viewModel.getItemsLiveData().value!![0].isProgressBarRunning)
        commonVerifyNoMoreInteractions()

    }

    @Test
    fun testGetCountOfSpans() {
        val listOfItems: MutableList<BenchmarkItem> = ArrayList()
        val item = BenchmarkItem(R.string.array_list, R.string.adding_in_the_beginning, 0L, false)
        listOfItems.add(item)
        setWhenAndObserveForever(listOfItems)
        assertEquals(viewModel.getCountOfSpans().toLong(), SPANS_COUNT.toLong())
        verify(mockBenchmark).getSpansCount()
        verify(mockCalculationStartLiveData).onChanged(anyBoolean())
        commonVerifyNoMoreInteractions()
    }

    @Test
    fun testIsNumberCorrect() {
        assertTrue(isNumberCorrect("10")!!.first)
        assertFalse(isNumberCorrect("-10")!!.first)
        assertFalse(isNumberCorrect("Hello")!!.first)
        assertEquals(Pair(true, 10).second, isNumberCorrect("10")!!.second)
        assertEquals(Pair(false, -10).second, isNumberCorrect("-10")!!.second)
        assertEquals(Pair(false, 0).second, isNumberCorrect("Hello")!!.second)
    }

    @Test
    fun testOnStopProcess() {
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.newThread() }
        val listOfItems: MutableList<BenchmarkItem> = java.util.ArrayList()
        val item = BenchmarkItem(R.string.array_list, R.string.adding_in_the_beginning, 0L, false)
        listOfItems.add(item)
        setWhenAndObserveForever(listOfItems)
        viewModel.setSizeCollectionLiveData(COLLECTION_SIZE)
        viewModel.onCreate()
        viewModel.onButtonToggle()
        try {
            Thread.sleep(100)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        viewModel.onButtonToggle()
        verify(mockCalculationStartLiveData, times(1)).onChanged(true)
        verify(mockCalculationStartLiveData, times(2)).onChanged(false)
        verify(mockBenchmark).createBenchmarkList(false)
        verify(mockBenchmark, times(1)).createBenchmarkList(true)
        verify(mockItemsLiveData, times(2)).onChanged(anyList())
        verify(mockBenchmark, times(1)).measureTime(COLLECTION_SIZE, item)
        assertFalse(viewModel.getCalculationStartLiveData().value!!)
        commonVerifyNoMoreInteractions()
    }

    @After
    fun clear() {
        viewModel.getItemsLiveData().removeObserver(mockItemsLiveData)
        viewModel.getCalculationStartLiveData().removeObserver(mockCalculationStartLiveData)
    }


}