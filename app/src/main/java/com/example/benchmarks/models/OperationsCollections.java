package com.example.benchmarks.models;

import com.example.benchmarks.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.inject.Inject;

public class OperationsCollections implements Benchmark {

    @Inject
    public OperationsCollections() {
    }

    @Override
    public long markDurationOfOperation(int sizeOfCollection, BenchmarkItem item) {
        final List<Integer> list = createCollection(sizeOfCollection, item.nameOfCollection);
        final int valueForSearching = 200;
        if (item.nameOfOperation == R.string.search_by_value) {
            list.set(new Random().nextInt(sizeOfCollection), valueForSearching);
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

    @Override
    public List<BenchmarkItem> createBenchmarkList(boolean isProgressBarRunning) {
        final List<BenchmarkItem> list = new ArrayList<>();
        int index = 0;
        for (int  operation: getOperationNames()) {
            for (int collection : getCollectionsNames()) {
                list.add(new BenchmarkItem(collection, operation, null, isProgressBarRunning, index++));
            }
        }
        return list;
    }

    @Override
    public int getSpansCount(){
        return getCollectionsNames().size();
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
