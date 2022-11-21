package com.example.benchmarks.ui.benchmark;

import android.content.Context;

import com.example.benchmarks.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BenchmarksDataClass {
    public static final List<String> listOfCollections = new ArrayList<>();
    public static final List<String> listOfMaps = new ArrayList<>();

    public static void fillLists(Context context) {
        listOfCollections.addAll(Arrays.asList(context.getResources().getStringArray(R.array.list_of_collections)));
        listOfMaps.addAll(Arrays.asList(context.getResources().getStringArray(R.array.list_of_maps)));
    }
}
