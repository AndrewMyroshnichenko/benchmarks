package com.example.benchmarks.models;

import com.example.benchmarks.R;
import com.example.benchmarks.ui.benchmark.BenchmarksViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class OperationsCollections {

    private List<Integer> createCollection(int sizeOfCollection, int indexOfCollection) {
        List<Integer> list = null;
        int id = BenchmarksDataClass.fillIdOfCollectionsList().get(indexOfCollection);
        switch (id) {
            case R.string.array_list:
                return list = new ArrayList<>(Collections.nCopies(sizeOfCollection, 0));
            case R.string.linked_list:
                return list = new LinkedList<>(Collections.nCopies(sizeOfCollection, 0));
            case R.string.copy_on_write_array_list:
                return  list = new CopyOnWriteArrayList<>(Collections.nCopies(sizeOfCollection, 0));
        }
        return list;
    }

    public long markDurationOfOperation(int sizeOfCollection, int indexOfOperation, int indexOfCollection) {
        List<Integer> list = createCollection(sizeOfCollection, indexOfCollection);
        int id = BenchmarksDataClass.fillIdOfOperationsList().get(indexOfOperation);
        final int valueForSearching = 200;
        long startTime = System.currentTimeMillis();

        switch (id) {
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
        }
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}
