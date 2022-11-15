package com.example.benchmarks.ui.benchmark;

import android.content.Context;

import com.example.benchmarks.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BenchmarksDataClass {
    public static final List<String> namesOfCollections = new ArrayList<>();
    public static final List<String> namesOfMaps = new ArrayList<>();
    public static final List<String> operationsOfCollections = new ArrayList<>();
    public static final List<String> operationsOfMaps = new ArrayList<>();

    public static void fillLists(Context context){
        namesOfCollections.addAll(Arrays.asList(context.getResources().getStringArray(R.array.name_collections)));
        namesOfMaps.addAll(Arrays.asList(context.getResources().getStringArray(R.array.name_maps)));
        operationsOfCollections.addAll(Arrays.asList(context.getResources().getStringArray(R.array.collections_operations)));
        operationsOfMaps.addAll(Arrays.asList(context.getResources().getStringArray(R.array.maps_operations)));
    }


}
