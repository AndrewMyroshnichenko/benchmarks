package com.example.benchmarks.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.benchmarks.R;
import com.example.benchmarks.utils.ItemProcessHolder;
import com.example.benchmarks.utils.ItemProcessAdapter;

import java.util.ArrayList;
import java.util.List;


public class CollectionsFragment extends Fragment {

    private ItemProcessAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ItemProcessAdapter(fillRecyclerView());
        RecyclerView recyclerView = view.findViewById(R.id.rv_main);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 3));
        recyclerView.setAdapter(adapter);
    }

    private List<ItemProcessHolder> fillRecyclerView(){
        List <ItemProcessHolder> list = new ArrayList<>();
        String[] operations = getResources().getStringArray(R.array.operations);

        for (int i = 0; i < operations.length; i++) {
            list.add(new ItemProcessHolder( operations[i] + getString(R.string.str_array_ms), true));
            list.add(new ItemProcessHolder( operations[i] + getString(R.string.str_linked_ms),true));
            list.add(new ItemProcessHolder( operations[i] + getString(R.string.str_cow_ms), true));
        }

        return list;
    }

}