package com.example.benchmarks;

import static org.junit.Assert.*;

import com.example.benchmarks.models.Benchmark;
import com.example.benchmarks.models.BenchmarkItem;
import com.example.benchmarks.models.OperationsMaps;

import org.junit.Test;

import java.util.List;


public class OperationsMapsUnitTest {

    Benchmark benchmark = new OperationsMaps();
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
    public void testMeasureTimeThrowsException(){
        BenchmarkItem wrongItem = new BenchmarkItem(1, 1, 0L, false);
        assertThrows(RuntimeException.class, () -> benchmark.measureTime(10, wrongItem));
    }

    @Test
    public void testGetSpansCount(){
        assertEquals(2, benchmark.getSpansCount());
    }

}
