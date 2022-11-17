package com.example.benchmarks.ui.benchmark;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class OperationMaps implements Runnable{

    private Map<Integer, Integer> map;
    private final long sizeOfMap;
    private final int operationType;
    private final int mapType;
    private final BenchmarksViewModel benchmarksViewModel;
    private String hashOfMap;
    private String hashOfOperation;

    public OperationMaps(BenchmarksViewModel benchmarksViewModel, int operationType, int mapType) {
        this.benchmarksViewModel = benchmarksViewModel;
        this.operationType = operationType;
        this.mapType = mapType;
        sizeOfMap = benchmarksViewModel.testSizeLiveData.getValue();
    }

    public void createMaps(int numberOfMap) {

        switch (numberOfMap) {
            case 0:
                map = new TreeMap<>();
                fillMap(map);
                hashOfMap = BenchmarksDataClass.namesOfMaps.get(numberOfMap);
                break;
            case 1:
                map = new HashMap<>();
                fillMap(map);
                hashOfMap = BenchmarksDataClass.namesOfMaps.get(numberOfMap);
                break;

        }
    }

    public void markDurationOfOperation(int numberOfOperation){
        long startTime = System.currentTimeMillis();
        switch (numberOfOperation){
            case 0:
                map.put(map.size(), (int) (Math.random() * 100));
                hashOfOperation = BenchmarksDataClass.operationsOfMaps.get(numberOfOperation);
                break;
            case 1:
                map.get(map.size() / 2);
                hashOfOperation = BenchmarksDataClass.operationsOfMaps.get(numberOfOperation);
                break;
            case 2:
                map.remove(map.size() / 2);
                hashOfOperation = BenchmarksDataClass.operationsOfMaps.get(numberOfOperation);
                break;
        }
        long endTime = System.currentTimeMillis();
        benchmarksViewModel.updateDurationOperation(endTime - startTime, hashOfMap, hashOfOperation, BenchmarksDataClass.operationsOfMaps, BenchmarksDataClass.namesOfMaps);
    }

        private void fillMap(Map<Integer, Integer> mapList){
            for (int i = 0; i < sizeOfMap; i++) {
                mapList.put(i, (int) (Math.random() * 100));
            }
        }

    @Override
    public void run() {
        createMaps(mapType);
        markDurationOfOperation(operationType);
    }
}
