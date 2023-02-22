package com.example.benchmarks;

import static org.junit.Assert.*;

import com.example.benchmarks.models.Benchmark;
import com.example.benchmarks.models.BenchmarkItem;
import com.example.benchmarks.models.OperationsCollections;

import org.junit.Test;

import java.util.List;

public class OperationsCollectionsUnitTest {

    Benchmark benchmark = new OperationsCollections();
    List<BenchmarkItem> testList = benchmark.createBenchmarkList(false);

    @Test
    public void testCreatingBenchmarkListReturnsNotNull() {
        assertNotNull(testList);
    }

    @Test
    public void testCreatingBenchmarkListIsNotEmpty(){
        assertTrue(testList.size() > 0);
    }

    @Test
    public void testMeasureTimeMoreThan0(){
        for (BenchmarkItem item : testList) {
            assertTrue(benchmark.measureTime(5000, item) > 0);
        }
    }

    @Test
    public void testGetSpansCount(){
        assertEquals(3, benchmark.getSpansCount());
    }
}