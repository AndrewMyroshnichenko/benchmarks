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
    private final int operationType;
    private final int collectionType;
    private final BenchmarksViewModel benchmarksViewModel;
    private String hashOfList;
    private String hashOfOperation;

    public OperationsCollections(BenchmarksViewModel benchmarksViewModel, int operationType, int collectionType) {
        this.benchmarksViewModel = benchmarksViewModel;
        this.operationType = operationType;
        this.collectionType = collectionType;
        sizeOfCollection = benchmarksViewModel.testSizeLiveData.getValue();
    }

    public void createCollection(int numberOfCollection){

        switch (numberOfCollection){
            case 0:
                list = new ArrayList<Integer>();
                fillCollection(list);
                hashOfList = BenchmarksDataClass.namesOfCollections.get(numberOfCollection);
                break;
            case 1:
                list = new LinkedList<Integer>();
                fillCollection(list);
                hashOfList = BenchmarksDataClass.namesOfCollections.get(numberOfCollection);
                break;
            case 2:
                list = new CopyOnWriteArrayList<Integer>();
                fillCollection(list);
                hashOfList = BenchmarksDataClass.namesOfCollections.get(numberOfCollection);
                break;
        }
    }

    public void markDurationOfOperation(int numberOfOperation){
        final int valueForSearching = 200;
        long startTime = System.currentTimeMillis();
        switch (numberOfOperation){
            case 0:
                list.add(0, (int) (Math.random() * 100));
                hashOfOperation = BenchmarksDataClass.operationsOfCollections.get(numberOfOperation);
                break;
            case 1:
                list.add(list.size() / 2, valueForSearching);
                hashOfOperation = BenchmarksDataClass.operationsOfCollections.get(numberOfOperation);
                break;
            case 2:
                list.add((int) (Math.random() * 100));
                hashOfOperation = BenchmarksDataClass.operationsOfCollections.get(numberOfOperation);
                break;
            case 3:
                list.indexOf(valueForSearching);
                hashOfOperation = BenchmarksDataClass.operationsOfCollections.get(numberOfOperation);
                break;
            case 4:
                list.remove(0);
                hashOfOperation = BenchmarksDataClass.operationsOfCollections.get(numberOfOperation);
                break;
            case 5:
                list.remove(list.size() / 2);
                hashOfOperation = BenchmarksDataClass.operationsOfCollections.get(numberOfOperation);
                break;
            case 6:
                list.remove(list.size() - 1);
                hashOfOperation = BenchmarksDataClass.operationsOfCollections.get(numberOfOperation);
                break;
        }
        long endTime = System.currentTimeMillis();;
        benchmarksViewModel.updateDurationOperation(endTime - startTime, hashOfList, hashOfOperation, BenchmarksDataClass.operationsOfCollections, BenchmarksDataClass.namesOfCollections);
    }

    private void fillCollection(List<Integer> listOfCollection){
        for (int i = 0; i < sizeOfCollection; i++) {
            listOfCollection.add((int) (Math.random() * 100));
        }
    }

    @Override
    public void run() {
        createCollection(collectionType);
        markDurationOfOperation(operationType);
    }
}
