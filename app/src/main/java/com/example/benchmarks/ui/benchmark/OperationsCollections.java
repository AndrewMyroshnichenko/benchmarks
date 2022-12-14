package com.example.benchmarks.ui.benchmark;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* This class is not finished
I am only thinking how to realize logic of creating Threads and mark duration of operations
 */

public class OperationsCollections implements Runnable {

    private List<Integer> list;
    private final long sizeOfCollection;
    private final BenchmarksViewModel benchmarksViewModel;
    private String nameOfItem;
    private static int numberOfOperation = 0;
    private final static int MAX_COUNT_OF_OPERATIONS = 21;

    public OperationsCollections(BenchmarksViewModel benchmarksViewModel, String nameOfItem) {
        this.benchmarksViewModel = benchmarksViewModel;
        this.nameOfItem = nameOfItem;
        sizeOfCollection = benchmarksViewModel.getTestSizeLiveData().getValue();
    }

    public void createCollection(String nameOfItem) {
        if (nameOfItem.contains("ArrayList")) {
            list = new ArrayList<Integer>();
            fillCollection(list);
        } else if (nameOfItem.contains("LinkedList")) {
            list = new LinkedList<Integer>();
            fillCollection(list);
        } else {
            list = new CopyOnWriteArrayList<Integer>();
            fillCollection(list);
        }
    }

    public void markDurationOfOperation(String nameOfItem) {

        final int valueForSearching = 200;
        long startTime = System.currentTimeMillis();

        if (nameOfItem.contains("Adding in the beginning")) {
            list.add(0, (int) (Math.random() * 100));
        } else if (nameOfItem.contains("Adding in the middle")) {
            list.add(list.size() / 2, valueForSearching);
        } else if (nameOfItem.contains("Adding in the end")) {
            list.add((int) (Math.random() * 100));
        } else if (nameOfItem.contains("Search by value")) {
            list.indexOf(valueForSearching);
        } else if (nameOfItem.contains("Removing in the beginning")) {
            list.remove(0);
        } else if (nameOfItem.contains("Removing in the middle")) {
            list.remove(list.size() / 2);
        } else {
            list.remove(list.size() - 1);
        }
        long endTime = System.currentTimeMillis();
        benchmarksViewModel.updateDurationOperation(endTime - startTime, nameOfItem, BenchmarksDataClass.listOfCollections);
    }

    private void fillCollection(List<Integer> listOfCollection) {
        for (int i = 0; i < sizeOfCollection; i++) {
            listOfCollection.add((int) (Math.random() * 100));
        }
    }

    private synchronized void additionalNumberOfOperation(){
        if(numberOfOperation < MAX_COUNT_OF_OPERATIONS) {
            numberOfOperation++;
        } else {
            numberOfOperation = 0;
        }
    }

    @Override
    public void run() {
        createCollection(nameOfItem);
        markDurationOfOperation(nameOfItem);
    }
}
