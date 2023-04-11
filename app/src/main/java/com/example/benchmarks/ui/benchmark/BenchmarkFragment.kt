package com.example.benchmarks.ui.benchmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.benchmarks.R
import com.example.benchmarks.databinding.FragmentBenchmarkBinding
import com.example.benchmarks.ui.input.InputFragment

class BenchmarkFragment : Fragment(), View.OnClickListener, FragmentResultListener {

    private val adapter = BenchmarksAdapter()
    private val inputFragment = InputFragment()
    private var viewModel: BenchmarksViewModel? = null
    private var bind: FragmentBenchmarkBinding? = null

    companion object {
        const val POSITION_KEY = "POSITION"

        fun createFragment(position: Int): BenchmarkFragment {
            val benchmarkFragment = BenchmarkFragment()
            val bundle = Bundle()
            bundle.putInt(POSITION_KEY, position)
            benchmarkFragment.arguments = bundle
            return benchmarkFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            val factory = BenchMarkViewModelFactory(requireArguments().getInt(POSITION_KEY))
            viewModel = ViewModelProvider(this, factory)[BenchmarksViewModel::class.java]
            viewModel?.onCreate()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_benchmark, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind = FragmentBenchmarkBinding.bind(view)
        bind?.edCollectionsFragment?.setOnClickListener(this)
        bind?.btCollections?.setOnClickListener(this)
        bind?.rvMain?.layoutManager = viewModel?.let { GridLayoutManager(context, it.getCountOfSpans()) }
        bind?.rvMain?.adapter = adapter

        viewModel?.getItemsLiveData()?.observe(viewLifecycleOwner, adapter::submitList)
        viewModel?.getCalculationStartLiveData()?.observe(viewLifecycleOwner
        ) { aBoolean: Boolean ->
            bind?.btCollections?.setText(
                if (aBoolean) R.string.bt_stop else R.string.bt_start
            )
        }
        childFragmentManager.setFragmentResultListener(
            InputFragment.Constants.INPUT_REQUEST_KEY,this, this)
    }

    override fun onClick(view: View) {
        if (view === bind?.edCollectionsFragment) {
            inputFragment.show(childFragmentManager, null)
        } else if (view === bind?.btCollections) {
            if (!bind?.edCollectionsFragment?.text.toString().matches(Regex("^\\d+$"))) {
                bind?.btCollections?.setText(R.string.et_fragment_text)
            } else {
                viewModel?.onButtonToggle()
            }
        }
    }

    override fun onFragmentResult(requestKey: String, result: Bundle) {
        viewModel?.setSizeCollectionLiveData(result.getInt(InputFragment.Constants.LONG_COLLECTION_SIZE_KEY))
        val size = result.getString(InputFragment.Constants.STRING_COLLECTION_SIZE_KEY)
        bind?.edCollectionsFragment?.setText(size)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bind = null
    }

}