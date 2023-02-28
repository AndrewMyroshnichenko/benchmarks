package com.example.benchmarks;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.example.benchmarks.models.Benchmark;
import com.example.benchmarks.models.BenchmarkItem;
import com.example.benchmarks.models.OperationsCollections;
import com.example.benchmarks.ui.benchmark.BenchmarksViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BenchmarksViewModelTest {

    private static final int SIZE_OF_LIST = 100;

    BenchmarksViewModel viewModel;
    Observer<List<BenchmarkItem>> itemsLiveData;
    Observer<Integer> testSizeLiveData;
    Observer<Boolean> calculationStartLiveData;
    Benchmark mockBenchmark;

    @Rule
    public final InstantTaskExecutorRule executorRule = new InstantTaskExecutorRule();

    @Before
    public void set() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxJavaPlugins.setComputationSchedulerHandler(scheduler -> Schedulers.trampoline());

        mockBenchmark = mock(OperationsCollections.class);

        viewModel = new BenchmarksViewModel(mockBenchmark);

        itemsLiveData = mock(Observer.class);
        testSizeLiveData = mock(Observer.class);
        calculationStartLiveData = mock(Observer.class);

        viewModel.getItemsLiveData().observeForever(itemsLiveData);
        viewModel.getCalculationStartLiveData().observeForever(calculationStartLiveData);
        viewModel.getTestSizeLiveData().observeForever(testSizeLiveData);

    }

    @Test
    public void testOnCreate(){
        viewModel.onCreate();
        verify(itemsLiveData).onChanged(mockBenchmark.createBenchmarkList(false));
    }

    @Test
    public void testOnStartProcess(){
        viewModel.onButtonToggle(); //Call onStartProcess to testing calculationStartLiveData -> true
        verify(calculationStartLiveData, times(1)).onChanged(true);
    }

    @Test
    public void testOnStopProcess(){
        viewModel.onButtonToggle(); //Call this to startProcess
        viewModel.onButtonToggle(); ///Call this to stop Process and to testing calculationStartLiveData -> false
        verify(calculationStartLiveData, times(3)).onChanged(false);
    }

    @Test
    public void testSetSizeCollection(){
        viewModel.setSizeCollectionLiveData(SIZE_OF_LIST);
        verify(testSizeLiveData).onChanged(SIZE_OF_LIST);
    }

    @After
    public void clear() {
        RxAndroidPlugins.reset();
        RxJavaPlugins.reset();
    }

}
