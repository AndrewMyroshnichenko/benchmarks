package com.example.benchmarks.ui.input

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import com.example.benchmarks.R
import com.example.benchmarks.databinding.FragmentInputBinding
import com.example.benchmarks.ui.benchmark.BenchmarksViewModel
import com.example.benchmarks.ui.input.InputFragment.Constants.INPUT_REQUEST_KEY
import com.example.benchmarks.ui.input.InputFragment.Constants.LONG_COLLECTION_SIZE_KEY
import com.example.benchmarks.ui.input.InputFragment.Constants.STRING_COLLECTION_SIZE_KEY

class InputFragment : DialogFragment(), TextWatcher, View.OnClickListener {

    object Constants {
        const val STRING_COLLECTION_SIZE_KEY = "KEY_COLLECTION"
        const val INPUT_REQUEST_KEY = "STING_KEY_INPUT_FRAGMENT"
        const val LONG_COLLECTION_SIZE_KEY = "LONG_KEY_COLLECTION"
    }

    private var bind: FragmentInputBinding? = null
    private var errorView: PopupWindow? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bind = FragmentInputBinding.inflate(layoutInflater)

        val dialog = Dialog(requireContext())
        dialog.setContentView(bind?.root ?: View(requireContext()))
        dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT)
        dialog.setContentView(bind?.root ?: View(requireContext()))
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT)
        dialog.setCancelable(false)
        bind?.edDialogFragment?.addTextChangedListener(this)
        bind?.btDialogFragment?.setOnClickListener(this)

        errorView = PopupWindow(
            layoutInflater.inflate(R.layout.error_message, null),
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            false
        )

        return dialog
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        bind?.edDialogFragment?.textSize = if (TextUtils.isEmpty(bind?.edDialogFragment?.text)) {
            14.0F
        } else {
            20.0F
        }
    }

    override fun afterTextChanged(s: Editable?) {

    }

    override fun onClick(view: View?) {
        val collectionSize = bind?.edDialogFragment?.text.toString()
        val result = BenchmarksViewModel.isNumberCorrect(collectionSize)
        if(result?.first == true) {
            bind?.edDialogFragment?.background = ResourcesCompat.getDrawable(
                resources, R.drawable.et_standart_background, null
            )
            sendCollectionSize(collectionSize, result.second)
            dismiss()
        } else {
            bind?.edDialogFragment?.background = ResourcesCompat.getDrawable(
                resources, R.drawable.et_error_backgroumd, null
            )
            errorView?.showAsDropDown(bind?.edDialogFragment, 80, 0)
        }
    }

    private fun sendCollectionSize(textSize: String, size: Int?) {
        val result = Bundle()
        result.putString(STRING_COLLECTION_SIZE_KEY, textSize)
        size?.let { result.putInt(LONG_COLLECTION_SIZE_KEY, it) }
        parentFragmentManager.setFragmentResult(INPUT_REQUEST_KEY, result)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        errorView = null
    }
}
