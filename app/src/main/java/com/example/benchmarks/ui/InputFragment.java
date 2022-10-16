package com.example.benchmarks.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.benchmarks.R;
import com.google.android.material.textfield.TextInputEditText;

public class InputFragment extends Fragment implements TextWatcher, View.OnClickListener {

    private EditText editText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_input, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.ed_dialog_fragment);
        editText.addTextChangedListener(this);
        Button button = view.findViewById(R.id.bt_input);
        button.setOnClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(!TextUtils.isEmpty(editText.getText())){
            editText.setTextSize(20);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onClick(View view) {
        PopupWindow errorView = new PopupWindow(getLayoutInflater().inflate(R.layout.error_message, null), LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);

        if(TextUtils.isEmpty(editText.getText().toString())){
            editText.setBackground(getResources().getDrawable(R.drawable.et_error_backgroumd));
            errorView.showAsDropDown(editText, 125, 0);
        } else {
            errorView.dismiss();
            editText.setBackground(getResources().getDrawable(R.drawable.et_standart_background));
        }
    }
}