package com.example.liban.weatherapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.liban.weatherapp.R;
import com.example.liban.weatherapp.mvp.calculatorMVP.CalculatorPresenter;
import com.example.liban.weatherapp.mvp.calculatorMVP.CalculatorView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalculatorFragment extends Fragment implements CalculatorView {


    private EditText mEditTextFirst;
    private EditText mEditTextSecond;
    private TextView mTextView;
    private Button mButtonPlus;
    private Button mButtonMinus;
    private Button mButtonResult;
    private CalculatorPresenter mCalculatorPresenter;

    private static int ID_PLUS = 1;

    public CalculatorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calculator, container, false);
        mEditTextFirst = view.findViewById(R.id.et_first_id);
        mEditTextSecond = view.findViewById(R.id.et_second_id);
        mTextView = view.findViewById(R.id.result_tv_id);
        mButtonPlus = view.findViewById(R.id.plus_bt_id);
        mButtonMinus = view.findViewById(R.id.minus_bt_id);
        mButtonResult = view.findViewById(R.id.result_bt_id);
        mCalculatorPresenter = new CalculatorPresenter(this);

        mButtonResult.setOnClickListener(v -> {
        });

        mButtonPlus.setOnClickListener(v -> {
            mCalculatorPresenter.calculator(mEditTextFirst.getText().toString(),
                    mEditTextSecond.getText().toString(), ID_PLUS);
        });
        return view;
    }


    @Override
    public void onResult(String rslt) {
        mTextView.setText(rslt);
    }

    @Override
    public void onError(String error) {
        mTextView.setText(error);
    }


}
