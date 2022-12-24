package com.example.benchmarks.models;

import android.content.Context;

import com.example.benchmarks.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BenchmarksDataClass {
    public static final List<String> listOfCollectionsNames = new ArrayList<>();
    public static final List<String> listOfCollectionsOperations = new ArrayList<>();
    public static final List<String> listOfMapsNames = new ArrayList<>();
    public static final List<String> listOfMapsOperations = new ArrayList<>();

    public static void fillLists(Context context) {
        listOfCollectionsNames.addAll(Arrays.asList(context.getResources().getStringArray(R.array.name_collections)));
        listOfCollectionsOperations.addAll(Arrays.asList(context.getResources().getStringArray(R.array.collections_operations)));
        listOfMapsNames.addAll(Arrays.asList(context.getResources().getStringArray(R.array.name_maps)));
        listOfMapsOperations.addAll(Arrays.asList(context.getResources().getStringArray(R.array.maps_operations)));
    }
}
