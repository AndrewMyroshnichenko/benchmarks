package com.example.benchmarks.models;

import com.example.benchmarks.R;
import com.example.benchmarks.ui.benchmark.BenchmarksViewModel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class OperationsCollections {

    public final static List<Integer> idOfCollections = fillIdOfCollectionsList();
    public final static List<Integer> idOfOperations = fillIdOfOperationsList();
    private final long sizeOfCollection;
    private final BenchmarksViewModel benchmarksViewModel;
    private List<Integer> list;

    public OperationsCollections(BenchmarksViewModel benchmarksViewModel) {
        this.benchmarksViewModel = benchmarksViewModel;
        sizeOfCollection = benchmarksViewModel.getTestSizeLiveData().getValue();
    }

    private void createCollection(int indexOfCollection) {
        int id = idOfCollections.get(indexOfCollection);
        switch (id) {
            case R.string.array_list:
                list = new ArrayList<Integer>();
                fillCollection(list);
                break;
            case R.string.linked_list:
                list = new LinkedList<Integer>();
                fillCollection(list);
                break;
            case R.string.copy_on_write_array_list:
                list = new CopyOnWriteArrayList<Integer>();
                fillCollection(list);
                break;
        }
    }

    public long markDurationOfOperation(int indexOfOperation, int indexOfCollection) {
        createCollection(indexOfCollection);
        int id = idOfOperations.get(indexOfOperation);
        final int valueForSearching = 200;
        long startTime = System.currentTimeMillis();

        switch (id) {
            case R.string.adding_in_the_beginning:
                list.add(0, (int) (Math.random() * 100));
                break;
            case R.string.adding_in_the_middle:
                list.add(list.size() / 2, valueForSearching);
                break;
            case R.string.adding_in_the_end:
                list.add((int) (Math.random() * 100));
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
        }
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    private void fillCollection(List<Integer> listOfCollection) {
        for (int i = 0; i < sizeOfCollection; i++) {
            listOfCollection.add((int) (Math.random() * 100));
        }
    }

    private static List<Integer> fillIdOfCollectionsList(){
        List<Integer> list = new ArrayList<>();
        list.add(R.string.array_list);
        list.add(R.string.linked_list);
        list.add(R.string.copy_on_write_array_list);
        return list;
    }

    private static List<Integer> fillIdOfOperationsList(){
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
