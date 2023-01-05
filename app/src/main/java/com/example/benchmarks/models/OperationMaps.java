package com.example.benchmarks.models;

import com.example.benchmarks.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class OperationMaps implements Benchmark{

    public long markDurationOfOperation(int sizeOfCollection, BenchmarkItem item) {
        final Map<Integer, Integer> map = createMap(sizeOfCollection, item.nameOfCollection);
        long startTime = System.nanoTime();

        switch (item.nameOfOperation) {
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

    public List<BenchmarkItem> createBenchmarkList() {
        final List<BenchmarkItem> list = new ArrayList<>();
        for (int collection : getMapsNames()) {
            for (int operation : getOperationNames()) {
                list.add(new BenchmarkItem(collection, operation, 0));
            }
        }
        return list;
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
