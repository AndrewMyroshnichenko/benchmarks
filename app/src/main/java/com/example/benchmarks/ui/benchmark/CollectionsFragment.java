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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CollectionsFragment extends Fragment implements View.OnClickListener, FragmentResultListener {

    private final BenchmarksAdapter adapter = new BenchmarksAdapter();
    private final InputFragment inputFragment = new InputFragment();
    private Button startStop;
    private TextInputEditText editText;
    private BenchmarksViewModel viewModel;
    public static final String KEY_OF_COLLECTION_FRAGMENT = "CollectionsFragment";

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(view.getResources().getStringArray(R.array.fragment_collections)));

        editText = view.findViewById(R.id.ed_collections_fragment);
        editText.setOnClickListener(this);
        startStop = view.findViewById(R.id.bt_collections);
        startStop.setOnClickListener(this);
        RecyclerView recyclerView = view.findViewById(R.id.rv_main);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 3));
        recyclerView.setAdapter(adapter);
        inputFragment.show(getChildFragmentManager(), null);
        adapter.submitList(viewModel.fillRecyclerView(BenchmarksDataClass.operationsOfCollections, BenchmarksDataClass.namesOfCollections));
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
                startStop.setText(viewModel.onButtonToggle(BenchmarksDataClass.namesOfCollections, BenchmarksDataClass.operationsOfCollections, KEY_OF_COLLECTION_FRAGMENT) ?
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