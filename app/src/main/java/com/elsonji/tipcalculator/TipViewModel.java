package com.elsonji.tipcalculator;

import android.arch.lifecycle.ViewModel;

public class TipViewModel extends ViewModel {
    private int personCount = 0;

    public void setPersonCount(int count) {
        personCount = count;
    }

    public int getPersonCount() {
        return personCount;
    }
}
