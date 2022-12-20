package com.example.benchmarks.ui.benchmark;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.benchmarks.R;
import com.example.benchmarks.databinding.FragmentMainBinding;
import com.example.benchmarks.models.BenchmarksDataClass;
import com.example.benchmarks.ui.input.InputFragment;


public class MapsFragment extends Fragment implements View.OnClickListener, FragmentResultListener {

    private final BenchmarksAdapter adapter = new BenchmarksAdapter();
    private final InputFragment inputFragment = new InputFragment();
    private final String KEY_OF_MAPS_FRAGMENT = "MapsFragment";
    private BenchmarksViewModel viewModel;
    private FragmentMainBinding bind;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(BenchmarksViewModel.class);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind = FragmentMainBinding.bind(view);
        bind.edCollectionsFragment.setOnClickListener(this);
        bind.btCollections.setOnClickListener(this);
        RecyclerView recyclerView = view.findViewById(R.id.rv_main);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 3));
        recyclerView.setAdapter(adapter);
        //adapter.submitList(viewModel.fillRecyclerView(BenchmarksDataClass.operationsOfMaps, BenchmarksDataClass.namesOfMaps));
        viewModel.getItemsLiveData().observe(getViewLifecycleOwner(), adapter::submitList);
        viewModel.getCalculationStartLiveData().observe(getViewLifecycleOwner(), aBoolean -> bind.btCollections.setText(aBoolean ? getResources().getString(R.string.bt_stop) : getResources().getString(R.string.bt_start)));
        getChildFragmentManager().setFragmentResultListener(InputFragment.INPUT_REQUEST_KEY, this, this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ed_collections_fragment:
                inputFragment.show(getChildFragmentManager(), null);
                break;
            case R.id.bt_collections:
                viewModel.onButtonToggle(BenchmarksDataClass.listOfMaps, KEY_OF_MAPS_FRAGMENT);
                break;
        }
    }

    @Override
    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
        viewModel.setSizeCollectionLiveData(result.getLong(InputFragment.LONG_COLLECTION_SIZE_KEY));
        String size = result.getString(InputFragment.STRING_COLLECTION_SIZE_KEY);
        bind.edCollectionsFragment.setText(size);
    }
}
