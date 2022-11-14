package com.example.benchmarks.ui.benchmark;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
import com.example.benchmarks.ui.input.InputFragment;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;


public class CollectionsFragment extends Fragment implements View.OnClickListener, FragmentResultListener {

    private final BenchmarksAdapter adapter = new BenchmarksAdapter();
    private final InputFragment inputFragment = new InputFragment();
    private Button startStop;
    private TextInputEditText editText;
    private BenchmarksViewModel viewModel;

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
        startStop = view.findViewById(R.id.bt_collections);
        startStop.setOnClickListener(this);
        RecyclerView recyclerView = view.findViewById(R.id.rv_main);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 3));
        recyclerView.setAdapter(adapter);
        inputFragment.show(getChildFragmentManager(), null);
        adapter.submitList(viewModel.fillCollectionsRecyclerView());
        viewModel.collectionsList.observe(getViewLifecycleOwner(), adapter::submitList);
        getChildFragmentManager().setFragmentResultListener(InputFragment.INPUT_REQUEST_KEY, this, this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(BenchmarksViewModel.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ed_collections_fragment:
                inputFragment.show(getChildFragmentManager(), null);
                break;
            case R.id.bt_collections:
                if(!viewModel.isStartButtonPressed){
                    viewModel.startCollectionProcess();
                    startStop.setText(getResources().getString(R.string.bt_stop));
                    viewModel.isStartButtonPressed = true;
                    break;
                } else {
                    viewModel.onStopProcess();
                    startStop.setText(getResources().getString(R.string.bt_start));
                    viewModel.isStartButtonPressed = false;
                    break;
                }

        }

    }

    @Override
    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
        editText.setText(result.getString(InputFragment.COLLECTION_SIZE_KEY));
    }
}