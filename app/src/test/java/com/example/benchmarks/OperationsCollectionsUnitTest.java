package com.example.benchmarks;

import static org.junit.Assert.*;

import com.example.benchmarks.models.Benchmark;
import com.example.benchmarks.models.BenchmarkItem;
import com.example.benchmarks.models.OperationsCollections;

import org.junit.Test;

import java.util.List;

public class OperationsCollectionsUnitTest {

    private final Benchmark benchmark = new OperationsCollections();

    @Test
    public void testCreatingBenchmarkListReturnsNotNull() {
        final List<BenchmarkItem> testList = benchmark.createBenchmarkList(false);
        assertNotNull(testList);
    }

    @Test
    public void testCreatingBenchmarkListIsNotEmpty(){
        final List<BenchmarkItem> testList = benchmark.createBenchmarkList(false);
        assertTrue(testList.size() > 0);
    }

    @Test
    public void testMeasureTimeMoreThan0(){
        final List<BenchmarkItem> testList = benchmark.createBenchmarkList(false);
        for (BenchmarkItem item : testList) {
            assertTrue(benchmark.measureTime(5000, item) > 0);
        }
    }

    @Test
    public void testMeasureTimeThrowsException(){
        BenchmarkItem wrongItem = new BenchmarkItem(1, 1, 0L, false);
        assertThrows(RuntimeException.class, () -> benchmark.measureTime(10, wrongItem));
    }

    @Test
    public void testGetSpansCount(){
        assertEquals(3, benchmark.getSpansCount());
    }
}