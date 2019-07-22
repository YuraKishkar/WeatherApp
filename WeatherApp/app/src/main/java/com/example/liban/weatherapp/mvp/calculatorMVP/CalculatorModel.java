package com.example.liban.weatherapp.mvp.calculatorMVP;

import java.util.regex.Pattern;

public class CalculatorModel implements ICalculatorModel {


    @Override
    public int onPlus(int first, int second) {

        return first + second;
    }

    @Override
    public int onMinus(int first, int second) {
        return 0;
    }

    @Override
    public boolean validation(String txt) {
        Pattern p = Pattern.compile("^\\d+$");
        for (int i = 0; i < txt.length(); i++) {
            if (!Character.isDigit(txt.charAt(i)))
                return false;
        }
        return true;
    }
}
