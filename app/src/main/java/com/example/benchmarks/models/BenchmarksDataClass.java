package com.example.benchmarks.models;

import android.content.Context;

import com.example.benchmarks.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BenchmarksDataClass {

    public static List<Integer> fillIdOfCollectionsList(){
        List<Integer> list = new ArrayList<>();
        list.add(R.string.array_list);
        list.add(R.string.linked_list);
        list.add(R.string.copy_on_write_array_list);
        return list;
    }

    public static List<Integer> fillIdOfOperationsList(){
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

    public static List<Integer> fillIdOfCollectionsMap(){
        List<Integer> list = new ArrayList<>();
        list.add(R.string.tree_map);
        list.add(R.string.hash_map);
        return list;
    }

    public static List<Integer> fillIdOfOperationsMap(){
        List<Integer> list = new ArrayList<>();
        list.add(R.string.adding_new_in);
        list.add(R.string.search_by_key_in);
        list.add(R.string.removing_in);
        return list;
    }
}
