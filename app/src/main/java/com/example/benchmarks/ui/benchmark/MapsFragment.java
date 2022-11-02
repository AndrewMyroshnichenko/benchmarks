package com.example.benchmarks.ui.benchmark;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.benchmarks.R;
import com.example.benchmarks.models.BenchmarkItem;
import com.example.benchmarks.models.BenchmarksViewModel;
import com.example.benchmarks.ui.input.InputFragment;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;


public class MapsFragment extends Fragment implements View.OnClickListener, FragmentResultListener {

    private final BenchmarksAdapter adapter = new BenchmarksAdapter();
    private final InputFragment inputFragment = new InputFragment();
    private TextInputEditText editText;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.ed_collections_fragment);
        editText.setOnClickListener(this);
        RecyclerView recyclerView = view.findViewById(R.id.rv_main);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 3));
        recyclerView.setAdapter(adapter);
        adapter.submitList(fillRecyclerView());
        getChildFragmentManager().setFragmentResultListener(InputFragment.INPUT_REQUEST_KEY, this, this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BenchmarksViewModel viewModel = new ViewModelProvider(requireActivity()).get(BenchmarksViewModel.class);
    }

    private List<BenchmarkItem> fillRecyclerView() {
        final List<BenchmarkItem> list = new ArrayList<>();
        final String[] mapsOperations = getResources().getStringArray(R.array.maps_operations);
        for (String maps_operation : mapsOperations) {
            list.add(new BenchmarkItem(maps_operation + getString(R.string.str_tree_map_ms), false, 0));
            list.add(new BenchmarkItem(maps_operation + getString(R.string.str_hash_map_ms), true, 0));
        }
        return list;
    }

    @Override
    public void onClick(View view) {
        inputFragment.show(getChildFragmentManager(), null);
    }

    @Override
    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
        editText.setText(result.getString(InputFragment.COLLECTION_SIZE_KEY));
    }
}