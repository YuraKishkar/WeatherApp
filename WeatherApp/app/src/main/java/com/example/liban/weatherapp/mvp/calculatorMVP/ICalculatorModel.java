package com.example.liban.weatherapp.mvp.calculatorMVP;

public interface ICalculatorModel {
    int onPlus(int first, int second);

    int onMinus(int first, int second);

    boolean validation(String txt);
}
