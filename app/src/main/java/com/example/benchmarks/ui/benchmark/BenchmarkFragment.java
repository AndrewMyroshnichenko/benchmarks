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
import com.example.benchmarks.databinding.FragmentBenchmarkBinding;
import com.example.benchmarks.ui.input.InputFragment;

import java.util.ArrayList;
import java.util.List;


public class BenchmarkFragment extends Fragment implements View.OnClickListener, FragmentResultListener {

    private static final String POSITION_KEY = "POSITION";
    private final List<Integer> idOfFragments = fillIdOfFragmentsList();
    private final BenchmarksAdapter adapter = new BenchmarksAdapter();
    private final InputFragment inputFragment = new InputFragment();
    private static int positionOfFragment = 0;
    private BenchmarksViewModel viewModel;
    private FragmentBenchmarkBinding bind;

    public static BenchmarkFragment createFragment(int position) {
        final BenchmarkFragment benchmarkFragment = new BenchmarkFragment();
        final Bundle bundle = new Bundle();
        bundle.putInt(POSITION_KEY, position);
        return benchmarkFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(BenchmarksViewModel.class);
        if (getArguments() != null) {
            positionOfFragment = getArguments().getInt(POSITION_KEY);
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
        final RecyclerView recyclerView = view.findViewById(R.id.rv_main);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 3));
        recyclerView.setAdapter(adapter);

        viewModel.getItemsLiveData().observe(getViewLifecycleOwner(), adapter::submitList);
        viewModel.getCalculationStartLiveData().observe(getViewLifecycleOwner(), aBoolean -> bind.btCollections.setText(aBoolean ? getResources().getString(R.string.bt_stop) : getResources().getString(R.string.bt_start)));
        getChildFragmentManager().setFragmentResultListener(InputFragment.INPUT_REQUEST_KEY, this, this);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(bind.edCollectionsFragment)) {
            inputFragment.show(getChildFragmentManager(), null);
        } else if (view.equals(bind.btCollections)) {
            viewModel.onButtonToggle(idOfFragments.get(positionOfFragment));
        }
    }

    @Override
    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
        viewModel.setSizeCollectionLiveData(result.getInt(InputFragment.LONG_COLLECTION_SIZE_KEY));
        String size = result.getString(InputFragment.STRING_COLLECTION_SIZE_KEY);
        bind.edCollectionsFragment.setText(size);
    }

    private List<Integer> fillIdOfFragmentsList(){
        List<Integer> list = new ArrayList<>();
        list.add(R.string.collections);
        list.add(R.string.maps);
        return list;
    }
}
