package com.example.benchmarks.models.benchmark;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class BenchmarkItemUnitTest {
    @Test
    public void testUpdateBenchmarkItem() {
        BenchmarkItem item = new BenchmarkItem(1, 2, 0L, true);
        BenchmarkItem newItem = item.updateBenchmarkItem(200L);
        assertEquals(1, newItem.getNameOfCollection());
        assertEquals(2, newItem.getNameOfOperation());
        assertEquals(200L, (long) newItem.getDurationOfOperation());
        assertFalse(newItem.isProgressBarRunning());
    }
}
