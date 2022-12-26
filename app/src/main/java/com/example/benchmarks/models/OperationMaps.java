package com.example.benchmarks.models;

import com.example.benchmarks.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class OperationMaps {

    private Map<Integer, Integer> createMap(int sizeOfCollection, int indexOfCollection) {
        Map<Integer, Integer> map = null;
        int id = fillIdOfCollectionsMap().get(indexOfCollection);
        switch (id) {
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

    public long markDurationOfOperation(int sizeOfCollection, int indexOfOperation, int indexOfCollection) {
        Map<Integer, Integer> map = createMap(sizeOfCollection, indexOfCollection);
        int id = fillIdOfOperationsMap().get(indexOfOperation);
        long startTime = System.currentTimeMillis();

        switch (id) {
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
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    private void fillMap(Map<Integer, Integer> mapList, int sizeOfMap) {
        for (int i = 0; i < sizeOfMap; i++) {
            mapList.put(i, (int) sizeOfMap);
        }
    }

    public static List<Integer> fillIdOfCollectionsMap() {
        List<Integer> list = new ArrayList<>();
        list.add(R.string.tree_map);
        list.add(R.string.hash_map);
        return list;
    }

    public static List<Integer> fillIdOfOperationsMap() {
        List<Integer> list = new ArrayList<>();
        list.add(R.string.adding_new_in);
        list.add(R.string.search_by_key_in);
        list.add(R.string.removing_in);
        return list;
    }
}
