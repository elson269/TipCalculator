package com.elsonji.tipcalculator;

import android.arch.lifecycle.ViewModel;

public class TipViewModel extends ViewModel {
    private boolean fabClickedStatus = false;
    private int personCount = 1;
    private double billAmount;
    private double tipAmountPercent;

    public void setPersonCount(int count) {
        personCount = count;
    }

    public int getPersonCount() {
        return personCount;
    }

    public void setFabClickedStatus(boolean status) {
        fabClickedStatus = status;
    }

    public boolean getFabClickedStatus() {
        return fabClickedStatus;
    }

    public void setTipAmountPercent(double tipPercent) {
        tipAmountPercent = tipPercent;
    }

    public double getTipAmountPercent() {
        return tipAmountPercent;
    }

    public void setBillAmount(double billAmount) {
        this.billAmount = billAmount;
    }

    public double getBillAmount() {
        return billAmount;
    }
}
