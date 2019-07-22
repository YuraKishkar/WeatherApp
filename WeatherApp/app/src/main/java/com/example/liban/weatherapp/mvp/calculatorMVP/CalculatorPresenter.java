package com.example.liban.weatherapp.mvp.calculatorMVP;

public class CalculatorPresenter implements ICalculatorPresenter {

    private ICalculatorModel mICalculatorModel = new CalculatorModel();
    private CalculatorView mCalculatorView;

    public CalculatorPresenter(CalculatorView calculatorView) {
        mCalculatorView = calculatorView;
    }

    @Override
    public void calculator(String first, String second, int id) {
        if (!mICalculatorModel.validation(first) && !mICalculatorModel.validation(second)) {
            mCalculatorView.onError("Error");
            return;
        }
        if (id == 1) {
            mCalculatorView.onResult(String.valueOf(mICalculatorModel.onPlus(Integer.parseInt(first),
                    Integer.parseInt(second))));
        }
    }
}

