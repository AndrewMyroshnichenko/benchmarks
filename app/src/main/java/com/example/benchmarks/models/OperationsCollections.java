package com.example.benchmarks.models;

import com.example.benchmarks.R;

import java.util.ArrayList;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class OperationsCollections implements Benchmark {

    public long markDurationOfOperation(int sizeOfCollection, BenchmarkItem item) {
        List<Integer> list = createCollection(sizeOfCollection, item.nameOfCollection);
        final int valueForSearching = 200;
        if (item.nameOfOperation == R.string.search_by_value) {
            list.add(valueForSearching);
        }
        long startTime = System.nanoTime();

        switch (item.nameOfOperation) {
            case R.string.adding_in_the_beginning:
                list.add(0, 1);
                break;
            case R.string.adding_in_the_middle:
                list.add(list.size() / 2, valueForSearching);
                break;
            case R.string.adding_in_the_end:
                list.add(1);
                break;
            case R.string.search_by_value:
                list.indexOf(valueForSearching);
                break;
            case R.string.removing_in_the_beginning:
                list.remove(0);
                break;
            case R.string.removing_in_the_middle:
                list.remove(list.size() / 2);
                break;
            case R.string.removing_in_the_end:
                list.remove(list.size() - 1);
                break;
            default:
                throw new RuntimeException("This is ID of operation doesn't exist");
        }
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    public List<BenchmarkItem> createBenchmarkList() {
        final List<BenchmarkItem> list = new ArrayList<>();
        for (int collection : getCollectionsNames()) {
            for (int operation : getOperationNames()) {
                list.add(new BenchmarkItem(collection, operation, 0));
            }
        }
        return list;
    }

    private List<Integer> createCollection(int sizeOfCollection, int nameOfCollection) {
        List<Integer> list = null;
        switch (nameOfCollection) {
            case R.string.array_list:
                list = new ArrayList<>(Collections.nCopies(sizeOfCollection, 0));
                break;
            case R.string.linked_list:
                list = new LinkedList<>(Collections.nCopies(sizeOfCollection, 0));
                break;
            case R.string.copy_on_write_array_list:
                list = new CopyOnWriteArrayList<>(Collections.nCopies(sizeOfCollection, 0));
                break;
            default:
                throw new RuntimeException("This is ID of collection doesn't exist");
        }
        return list;
    }

    private List<Integer> getCollectionsNames() {
        List<Integer> list = new ArrayList<>();
        list.add(R.string.array_list);
        list.add(R.string.linked_list);
        list.add(R.string.copy_on_write_array_list);
        return list;
    }

    private List<Integer> getOperationNames() {
        List<Integer> list = new ArrayList<>();
        list.add(R.string.adding_in_the_beginning);
        list.add(R.string.adding_in_the_middle);
        list.add(R.string.adding_in_the_end);
        list.add(R.string.search_by_value);
        list.add(R.string.removing_in_the_beginning);
        list.add(R.string.removing_in_the_middle);
        list.add(R.string.removing_in_the_end);
        return list;
    }
}
