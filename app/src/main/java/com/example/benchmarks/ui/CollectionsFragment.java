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
import android.widget.EditText;

import com.example.benchmarks.R;
import com.example.benchmarks.utils.ItemProcessHolder;
import com.example.benchmarks.utils.ItemProcessAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;


public class CollectionsFragment extends Fragment implements View.OnClickListener{

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
        inputFragment.show(getChildFragmentManager(), null);
        adapter.setItems(fillRecyclerView());
    }

    private List fillRecyclerView(){
        final List <ItemProcessHolder> list = new ArrayList<>();
        String[] operations = getResources().getStringArray(R.array.operations);
        for (String operation : operations) {
            list.add(new ItemProcessHolder(operation + getString(R.string.str_array_ms), true));
            list.add(new ItemProcessHolder(operation + getString(R.string.str_linked_ms), true));
            list.add(new ItemProcessHolder(operation + getString(R.string.str_cow_ms), true));
        }
        return list;
    }

    @Override
    public void onClick(View view) {
        inputFragment.show(getChildFragmentManager(), null);
    }
}