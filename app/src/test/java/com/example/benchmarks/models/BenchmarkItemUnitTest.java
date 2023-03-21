package com.example.benchmarks.models;

import com.example.benchmarks.models.BenchmarkItem;

import static org.junit.Assert.*;

import org.junit.Test;

public class BenchmarkItemUnitTest {
    @Test
    public void testUpdateBenchmarkItem(){
        BenchmarkItem item = new BenchmarkItem(1, 2, 0L, true);
        BenchmarkItem newItem = item.updateBenchmarkItem(200L);
        assertEquals(1, newItem.nameOfCollection);
        assertEquals(2, newItem.nameOfOperation);
        assertEquals(200L, (long) newItem.durationOfOperation);
        assertFalse(newItem.isProgressBarRunning);
    }
}
