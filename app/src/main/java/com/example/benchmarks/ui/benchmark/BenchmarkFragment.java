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

import com.example.benchmarks.R;
import com.example.benchmarks.databinding.FragmentBenchmarkBinding;
import com.example.benchmarks.ui.input.InputFragment;

public class BenchmarkFragment extends Fragment implements View.OnClickListener, FragmentResultListener {

    private static final String POSITION_KEY = "POSITION";
    private final BenchmarksAdapter adapter = new BenchmarksAdapter();
    private final InputFragment inputFragment = new InputFragment();
    private BenchmarksViewModel viewModel;
    private FragmentBenchmarkBinding bind;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            BenchMarkViewModelFactory factory = new BenchMarkViewModelFactory(getArguments().getInt(POSITION_KEY));
            viewModel = new ViewModelProvider(this, factory).get(BenchmarksViewModel.class);
            viewModel.onCreate();
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_benchmark, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind = FragmentBenchmarkBinding.bind(view);
        bind.edCollectionsFragment.setOnClickListener(this);
        bind.btCollections.setOnClickListener(this);
        bind.rvMain.setLayoutManager(new GridLayoutManager(this.getContext(), (getArguments().getInt(POSITION_KEY) == 0) ? 3 : 2));
        bind.rvMain.setAdapter(adapter);

        viewModel.getItemsLiveData().observe(getViewLifecycleOwner(), adapter::submitList);
        viewModel.getCalculationStartLiveData().observe(getViewLifecycleOwner(), aBoolean -> bind.btCollections.setText(aBoolean ? R.string.bt_stop : R.string.bt_start));
        getChildFragmentManager().setFragmentResultListener(InputFragment.INPUT_REQUEST_KEY, this, this);
    }

    @Override
    public void onClick(View view) {
        if (view == bind.edCollectionsFragment) {
            inputFragment.show(getChildFragmentManager(), null);
        } else if (view == bind.btCollections) {
            viewModel.onButtonToggle();
        }
    }

    @Override
    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
        viewModel.setSizeCollectionLiveData(result.getInt(InputFragment.LONG_COLLECTION_SIZE_KEY));
        String size = result.getString(InputFragment.STRING_COLLECTION_SIZE_KEY);
        bind.edCollectionsFragment.setText(size);
    }

    public static BenchmarkFragment createFragment(int position) {
        final BenchmarkFragment benchmarkFragment = new BenchmarkFragment();
        final Bundle bundle = new Bundle();
        bundle.putInt(POSITION_KEY, position);
        benchmarkFragment.setArguments(bundle);
        return benchmarkFragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind = null;
    }
}
