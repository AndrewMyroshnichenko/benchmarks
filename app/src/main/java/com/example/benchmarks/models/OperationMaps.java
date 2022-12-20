package com.example.benchmarks.models;

import com.example.benchmarks.ui.benchmark.BenchmarksViewModel;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class OperationMaps implements Runnable {

    private final long sizeOfMap;
    private final BenchmarksViewModel benchmarksViewModel;
    private final String nameOfItem;
    private Map<Integer, Integer> map;

    public OperationMaps(BenchmarksViewModel benchmarksViewModel, String nameOfItem) {
        this.benchmarksViewModel = benchmarksViewModel;
        this.nameOfItem = nameOfItem;
        sizeOfMap = benchmarksViewModel.getTestSizeLiveData().getValue();
    }

    public void createMaps(String nameOfItem) {
        if (nameOfItem.contains("TreeMap")) {
            map = new TreeMap<>();
            fillMap(map);
        } else {
            map = new HashMap<>();
            fillMap(map);
        }
    }

    public void markDurationOfOperation(String nameOfItem) {

        long startTime = System.currentTimeMillis();

        if (nameOfItem.contains("Adding new in")) {
            map.put(map.size(), (int) (Math.random() * 100));
        } else if (nameOfItem.contains("Search by key in")) {
            map.get(map.size() / 2);
        } else {
            map.remove(map.size() / 2);
        }

        long endTime = System.currentTimeMillis();
        benchmarksViewModel.updateDurationOperation(endTime - startTime, nameOfItem, BenchmarksDataClass.listOfMaps);
    }

    private void fillMap(Map<Integer, Integer> mapList) {
        for (int i = 0; i < sizeOfMap; i++) {
            mapList.put(i, (int) (Math.random() * 100));
        }
    }

    @Override
    public void run() {
        createMaps(nameOfItem);
        markDurationOfOperation(nameOfItem);
    }
}
