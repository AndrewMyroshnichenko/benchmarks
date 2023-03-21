package com.example.benchmarks.ui.benchmark;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.example.benchmarks.MainThreadRule;
import com.example.benchmarks.R;
import com.example.benchmarks.models.Benchmark;
import com.example.benchmarks.models.BenchmarkItem;
import com.example.benchmarks.utils.Pair;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BenchmarksViewModelTest {

    @Rule
    public MainThreadRule mainThreadRule = new MainThreadRule();

    private final static int SPANS_COUNT = 3;
    private final static int COLLECTION_SIZE = 10;

    private BenchmarksViewModel viewModel;
    private Observer<List<BenchmarkItem>> mockItemsLiveData;
    private Observer<Boolean> mockCalculationStartLiveData;
    private Benchmark mockBenchmark;

    @Rule
    public final InstantTaskExecutorRule executorRule = new InstantTaskExecutorRule();

    @Before
    public void set() {
        mockBenchmark = mock(Benchmark.class);
        mockItemsLiveData = mock(Observer.class);
        mockCalculationStartLiveData = mock(Observer.class);
        viewModel = new BenchmarksViewModel(mockBenchmark);

    }

    private void setWhenAndObserveForever(List<BenchmarkItem> list) {
        when(mockBenchmark.createBenchmarkList(false)).thenReturn(list);
        when(mockBenchmark.createBenchmarkList(true)).thenReturn(list);
        when(mockBenchmark.getSpansCount()).thenReturn(SPANS_COUNT);

        viewModel.getItemsLiveData().observeForever(mockItemsLiveData);
        viewModel.getCalculationStartLiveData().observeForever(mockCalculationStartLiveData);
    }

    private void commonVerifyNoMoreInteractions(){
        verifyNoMoreInteractions(mockCalculationStartLiveData);
        verifyNoMoreInteractions(mockItemsLiveData);
        verifyNoMoreInteractions(mockBenchmark);
    }

    @Test
    public void testOnCreate() {

        final List<BenchmarkItem> listOfItems = new ArrayList<>();
        BenchmarkItem item = new BenchmarkItem(R.string.array_list, R.string.adding_in_the_beginning, 0L, false);
        listOfItems.add(item);

        setWhenAndObserveForever(listOfItems);

        viewModel.onCreate();
        verify(mockBenchmark).createBenchmarkList(false);
        verify(mockItemsLiveData).onChanged(listOfItems);
        verify(mockCalculationStartLiveData).onChanged(false);
        assertEquals(listOfItems, viewModel.getItemsLiveData().getValue());

        commonVerifyNoMoreInteractions();
    }

    @Test
    public void testOnButtonToggle() {
        final List<BenchmarkItem> listOfItems = new ArrayList<>();
        BenchmarkItem item = new BenchmarkItem(R.string.array_list, R.string.adding_in_the_beginning, 0L, false);
        listOfItems.add(item);
        setWhenAndObserveForever(listOfItems);

        viewModel.setSizeCollectionLiveData(COLLECTION_SIZE);
        viewModel.onCreate();
        viewModel.onButtonToggle();

        verify(mockCalculationStartLiveData).onChanged(true);
        verify(mockCalculationStartLiveData, times(2)).onChanged(false);
        verify(mockBenchmark).createBenchmarkList(true);
        verify(mockBenchmark).createBenchmarkList(false);
        verify(mockBenchmark).measureTime(COLLECTION_SIZE, item);
        verify(mockItemsLiveData, times(2)).onChanged(anyList());

        assertNotSame(listOfItems, viewModel.getItemsLiveData().getValue());
        assertFalse(viewModel.getCalculationStartLiveData().getValue());
        assertFalse(viewModel.getItemsLiveData().getValue().get(0).isProgressBarRunning);
        commonVerifyNoMoreInteractions();
    }

    @Test
    public void testGetCountOfSpans() {
        final List<BenchmarkItem> listOfItems = new ArrayList<>();
        BenchmarkItem item = new BenchmarkItem(R.string.array_list, R.string.adding_in_the_beginning, 0L, false);
        listOfItems.add(item);

        setWhenAndObserveForever(listOfItems);

        assertEquals(viewModel.getCountOfSpans(), SPANS_COUNT);
        verify(mockBenchmark).getSpansCount();
        verify(mockCalculationStartLiveData).onChanged(anyBoolean());

        commonVerifyNoMoreInteractions();
    }

    @Test
    public void testIsNumberCorrect(){
        assertTrue(BenchmarksViewModel.isNumberCorrect("10").first);
        assertFalse(BenchmarksViewModel.isNumberCorrect("-10").first);
        assertFalse(BenchmarksViewModel.isNumberCorrect("Hello").first);
        assertEquals(new Pair<>(true, 10).second, BenchmarksViewModel.isNumberCorrect("10").second);
        assertEquals(new Pair<>(false, -10).second, BenchmarksViewModel.isNumberCorrect("-10").second);
        assertEquals(new Pair<>(false, 0).second, BenchmarksViewModel.isNumberCorrect("Hello").second);
    }

    @After
    public void clear(){
        viewModel.getItemsLiveData().removeObserver(mockItemsLiveData);
        viewModel.getCalculationStartLiveData().removeObserver(mockCalculationStartLiveData);
    }
}
