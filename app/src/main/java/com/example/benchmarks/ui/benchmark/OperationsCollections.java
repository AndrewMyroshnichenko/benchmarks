package com.example.benchmarks.ui.benchmark;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* This class is not finished
I am only thinking how to realize logic of creating Threads and mark duration of operations
 */

public class OperationsCollections implements Runnable {

    private final long sizeOfCollection;
    String s;
    public OperationsCollections(long sizeOfCollection) {
        this.sizeOfCollection = sizeOfCollection;
    }

    public List<Integer> createCollection(int numberOfCollection){
        List<Integer> list;

        switch (numberOfCollection){
            case 1:
                list = new LinkedList<Integer>();
                fillCollection(list);
                s = "Lin";
                break;
            case 2:
                list = new CopyOnWriteArrayList<Integer>();
                fillCollection(list);
                s = "Copy";
                break;
            default:
                list = new ArrayList<Integer>();
                fillCollection(list);
                s = "Arraylist";
                break;
        }
        return list;
    }

    public long markTheTimeOperation(List<Integer> list, int numberOfOperation){
        long time = 0;
        final int valueForSearching = 200;
        long startTime = System.nanoTime();
        switch (numberOfOperation){
            case 0:
                list.add(0, (int) (Math.random() * 100));
                break;
            case 1:
                list.add(list.size() / 2, valueForSearching);
                break;
            case 2:
                list.add((int) (Math.random() * 100));
                break;
            case 3:
                list.indexOf(valueForSearching);
                break;
            case 4:
                list.remove(0);
                break;
            case 5:
                list.remove(list.size() / 2);
                break;
            case 6:
                list.remove(list.size() - 1);
                break;
        }
        long endTime = System.nanoTime();

        return endTime - startTime;
    }

    private void fillCollection(List<Integer> listOfCollection){
        for (int i = 0; i < sizeOfCollection; i++) {
            listOfCollection.add((int) (Math.random() * 100));
        }
    }

    @Override
    public void run() {

    }
}
