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
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;


public class MapsFragment extends Fragment implements View.OnClickListener {

    private final ItemProcessAdapter adapter = new ItemProcessAdapter();
    private final InputFragment inputFragment = new InputFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextInputEditText editText = view.findViewById(R.id.ed_collections_fragment);
        editText.setOnClickListener(this);
        RecyclerView recyclerView = view.findViewById(R.id.rv_main);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 3));
        recyclerView.setAdapter(adapter);
        fillRecyclerView();
    }

    private void fillRecyclerView(){
        final List <ItemProcessHolder> list = new ArrayList<>();
        String[] mapsOperations = getResources().getStringArray(R.array.maps_operations);
        for (String maps_operation : mapsOperations) {
            list.add(new ItemProcessHolder(maps_operation + getString(R.string.str_tree_map_ms), true));
            list.add(new ItemProcessHolder(maps_operation + getString(R.string.str_hash_map_ms), true));
        }
        adapter.fillAdapterList(list);
    }

    @Override
    public void onClick(View view) {
        inputFragment.show(getChildFragmentManager(), null);
    }
}