package com.example.benchmarks.ui.input;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.benchmarks.R;
import com.example.benchmarks.models.BenchmarksViewModel;

public class InputFragment extends DialogFragment implements TextWatcher, View.OnClickListener {

    private EditText editText;
    private PopupWindow errorView;
    private BenchmarksViewModel viewModel;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.fragment_input);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        editText = dialog.findViewById(R.id.ed_dialog_fragment);
        editText.addTextChangedListener(this);
        Button button = dialog.findViewById(R.id.bt_input);
        button.setOnClickListener(this);
        setCancelable(false);
        viewModel = new ViewModelProvider(requireActivity()).get(BenchmarksViewModel.class);
        return dialog;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        editText.setTextSize(TextUtils.isEmpty(editText.getText()) ? 14 : 20);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onClick(View view) {
        if (TextUtils.isEmpty(editText.getText())) {
            editText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.et_error_backgroumd, null));
            showPopupError();
        } else {
            editText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.et_standart_background, null));
            dismiss();
        }
    }

    private void showPopupError(){
        errorView = new PopupWindow(
                getLayoutInflater().inflate(R.layout.error_message, null),
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,
                true
        );
        errorView.showAsDropDown(editText, 80, 0);
    }
}