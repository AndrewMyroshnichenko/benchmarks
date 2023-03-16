package com.example.benchmarks;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.example.benchmarks.models.Benchmark;
import com.example.benchmarks.models.BenchmarkItem;
import com.example.benchmarks.ui.benchmark.BenchmarksViewModel;

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

    private final List<BenchmarkItem> listOfItems = new ArrayList<>();
    private BenchmarkItem item;

    @Rule
    public final InstantTaskExecutorRule executorRule = new InstantTaskExecutorRule();

    @Before
    public void set() {
        item = new BenchmarkItem(R.string.array_list, R.string.adding_in_the_beginning, 0L, false);
        listOfItems.add(item);

        mockBenchmark = mock(Benchmark.class);
        mockItemsLiveData = mock(Observer.class);
        mockCalculationStartLiveData = mock(Observer.class);
        viewModel = new BenchmarksViewModel(mockBenchmark);

    }

    private void setWhenAndObserveForever() {
        when(mockBenchmark.createBenchmarkList(false)).thenReturn(listOfItems);
        when(mockBenchmark.createBenchmarkList(true)).thenReturn(listOfItems);
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
        setWhenAndObserveForever();

        viewModel.onCreate();
        verify(mockBenchmark).createBenchmarkList(false);
        verify(mockItemsLiveData).onChanged(listOfItems);
        verify(mockCalculationStartLiveData).onChanged(false);
        assertEquals(listOfItems, viewModel.getItemsLiveData().getValue());

        commonVerifyNoMoreInteractions();
    }

    @Test
    public void testOnButtonToggle() {
        setWhenAndObserveForever();

        viewModel.setSizeCollectionLiveData(COLLECTION_SIZE);
        viewModel.onButtonToggle();

        verify(mockCalculationStartLiveData).onChanged(true);
        verify(mockCalculationStartLiveData, times(2)).onChanged(false);
        verify(mockBenchmark).createBenchmarkList(true);
        verify(mockBenchmark).measureTime(COLLECTION_SIZE, item);

        assertFalse(viewModel.getCalculationStartLiveData().getValue());
        commonVerifyNoMoreInteractions();
    }

    @Test
    public void testGetCountOfSpans() {
        setWhenAndObserveForever();

        assertEquals(viewModel.getCountOfSpans(), SPANS_COUNT);
        verify(mockBenchmark).getSpansCount();
        verify(mockCalculationStartLiveData).onChanged(anyBoolean());

        commonVerifyNoMoreInteractions();
    }

    @After
    public void clear(){
        viewModel.getItemsLiveData().removeObserver(mockItemsLiveData);
        viewModel.getCalculationStartLiveData().removeObserver(mockCalculationStartLiveData);
    }
}
