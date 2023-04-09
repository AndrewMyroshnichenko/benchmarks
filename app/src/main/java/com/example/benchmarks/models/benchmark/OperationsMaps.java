package com.example.benchmarks.models.benchmark;

import com.example.benchmarks.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

public class OperationsMaps implements Benchmark {

    @Inject
    public OperationsMaps() {
    }

    @Override
    public long measureTime(int sizeOfCollection, BenchmarkItem item) {
        final Map<Integer, Integer> map = createMap(sizeOfCollection, item.getNameOfCollection());
        long startTime = System.nanoTime();

        switch (item.getNameOfOperation()) {
            case R.string.adding_new_in:
                map.put(map.size(), 1);
                break;
            case R.string.search_by_key_in:
                map.get(map.size() / 2);
                break;
            case R.string.removing_in:
                map.remove(map.size() / 2);
                break;
            default:
                throw new RuntimeException("This is ID of operation doesn't exist");
        }
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    @Override
    public List<BenchmarkItem> createBenchmarkList(boolean isProgressBarRunning) {
        final List<BenchmarkItem> list = new ArrayList<>();
        for (int operation : getOperationNames()) {
            for (int collection : getMapsNames()) {
                list.add(new BenchmarkItem(collection, operation, null, isProgressBarRunning));
            }
        }
        return list;
    }

    @Override
    public int getSpansCount() {
        return getMapsNames().size();
    }

    private Map<Integer, Integer> createMap(int sizeOfCollection, int nameOfMap) {
        Map<Integer, Integer> map;
        switch (nameOfMap) {
            case R.string.tree_map:
                map = new TreeMap<>();
                fillMap(map, sizeOfCollection);
                break;
            case R.string.hash_map:
                map = new HashMap<>();
                fillMap(map, sizeOfCollection);
                break;
            default:
                throw new RuntimeException("This is ID of maps doesn't exist");
        }
        return map;
    }

    private void fillMap(Map<Integer, Integer> mapList, int sizeOfMap) {
        for (int i = 0; i < sizeOfMap; i++) {
            mapList.put(i, sizeOfMap);
        }
    }

    private List<Integer> getMapsNames() {
        List<Integer> list = new ArrayList<>();
        list.add(R.string.tree_map);
        list.add(R.string.hash_map);
        return list;
    }

    private List<Integer> getOperationNames() {
        List<Integer> list = new ArrayList<>();
        list.add(R.string.adding_new_in);
        list.add(R.string.search_by_key_in);
        list.add(R.string.removing_in);
        return list;
    }
}
