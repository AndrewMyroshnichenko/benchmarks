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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.benchmarks.R;
import com.example.benchmarks.ui.input.InputFragment;
import com.google.android.material.textfield.TextInputEditText;


public class MapsFragment extends Fragment implements View.OnClickListener, FragmentResultListener {

    private final BenchmarksAdapter adapter = new BenchmarksAdapter();
    private final InputFragment inputFragment = new InputFragment();
    private Button startStop;
    private TextInputEditText editText;
    private BenchmarksViewModel viewModel;
    private final String KEY_OF_MAPS_FRAGMENT = "MapsFragment";

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
        adapter.submitList(viewModel.fillRecyclerView(BenchmarksDataClass.operationsOfMaps, BenchmarksDataClass.namesOfMaps));
        viewModel.itemsLiveData.observe(getViewLifecycleOwner(), adapter::submitList);
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
                startStop.setText(viewModel.onButtonToggle(BenchmarksDataClass.namesOfMaps, BenchmarksDataClass.operationsOfMaps, KEY_OF_MAPS_FRAGMENT) ?
                        getResources().getString(R.string.bt_stop) : getResources().getString(R.string.bt_start));
                break;
        }
    }

    @Override
    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
        String size = result.getString(InputFragment.COLLECTION_SIZE_KEY);
        editText.setText(size);
        viewModel.testSizeLiveData.postValue(Long.parseLong(size));
    }
}