package com.elsonji.tipcalculator.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "tip_history_table")
public class TipHistory {
    @PrimaryKey(autoGenerate = true)
    public int mId;

    @ColumnInfo(name = "date")
    private String mDate;

    @ColumnInfo(name = "bill_amount")
    private double mBillAmount;

    @ColumnInfo(name = "tip_percent")
    private int mTipPercent;

    @ColumnInfo(name = "tip_per_person")
    private double mTipPerPerson;

    @ColumnInfo(name = "total_per_person")
    private double mTotalPerPerson;

    public TipHistory( String date, double billAmount, int tipPercent, double tipPerPerson,
                      double totalPerPerson) {
        mDate = date;
        mBillAmount = billAmount;
        mTipPercent = tipPercent;
        mTipPerPerson = tipPerPerson;
        mTotalPerPerson = totalPerPerson;
    }

    public int getId() {
        return mId;
    }

    public String getDate() {
        return mDate;
    }

    public double getBillAmount() {
        return mBillAmount;
    }

    public int getTipPercent() {
        return mTipPercent;
    }

    public double getTipPerPerson() {
        return mTipPerPerson;
    }

    public double getTotalPerPerson() {
        return mTotalPerPerson;
    }

}
