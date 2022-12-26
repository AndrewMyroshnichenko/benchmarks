package com.example.benchmarks.ui.input;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Pair;
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

import com.example.benchmarks.R;
import com.example.benchmarks.ui.benchmark.BenchmarksViewModel;

public class InputFragment extends DialogFragment implements TextWatcher, View.OnClickListener {

    public final static String STRING_COLLECTION_SIZE_KEY = "KEY_COLLECTION";
    public final static String INPUT_REQUEST_KEY = "STING_KEY_INPUT_FRAGMENT";
    public static final String LONG_COLLECTION_SIZE_KEY = "LONG_KEY_COLLECTION";

    private EditText editText;
    private PopupWindow errorView;

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
        errorView = new PopupWindow(
                getLayoutInflater().inflate(R.layout.error_message, null),
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,
                true
        );
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
        final String collectionSize = editText.getText().toString();
        final Pair<Boolean, Integer> result = BenchmarksViewModel.isNumberCorrect(collectionSize);
        if (result.first) {
            editText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.et_standart_background, null));
            sendCollectionSize(collectionSize, result.second);
            dismiss();
        } else {
            editText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.et_error_backgroumd, null));
            showPopupError();
        }
    }

    private void showPopupError() {
        errorView.showAsDropDown(editText, 80, 0);
    }

    private void sendCollectionSize(String textSize, Integer size) {
        final Bundle result = new Bundle();
        result.putString(STRING_COLLECTION_SIZE_KEY, textSize);
        result.putInt(LONG_COLLECTION_SIZE_KEY, size);
        getParentFragmentManager().setFragmentResult(INPUT_REQUEST_KEY, result);
    }
}