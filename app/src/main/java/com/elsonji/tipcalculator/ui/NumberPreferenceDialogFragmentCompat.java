package com.elsonji.tipcalculator.ui;

import android.os.Bundle;
import android.support.v7.preference.DialogPreference;
import android.support.v7.preference.PreferenceDialogFragmentCompat;
import android.view.View;

import android.widget.NumberPicker;

import com.elsonji.tipcalculator.R;

import static com.elsonji.tipcalculator.ui.NumberPickerPreference.MAX_VALUE;
import static com.elsonji.tipcalculator.ui.NumberPickerPreference.MIN_VALUE;
import static com.elsonji.tipcalculator.ui.NumberPickerPreference.WRAP_SELECTOR_WHEEL;

public class NumberPreferenceDialogFragmentCompat extends PreferenceDialogFragmentCompat {
    private NumberPicker mNumberPicker;
    public static NumberPreferenceDialogFragmentCompat newInstance (String key) {
        final NumberPreferenceDialogFragmentCompat fragment =
                new NumberPreferenceDialogFragmentCompat();
        final Bundle bundle = new Bundle(1);
        bundle.putString(ARG_KEY, key);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        mNumberPicker = (NumberPicker)view.findViewById(R.id.edit);

        // Exception when there is no TimePicker
        if (mNumberPicker == null) {
            throw new IllegalStateException("Dialog view must contain" +
                    " a NumberPicker with id 'edit'");
        }

        Integer tipPercent = null;
        DialogPreference preference = getPreference();
        if (preference instanceof NumberPickerPreference){
            tipPercent = ((NumberPickerPreference) preference).getValue();
        }

        if (tipPercent != null) {
            mNumberPicker.setMinValue(MIN_VALUE);
            mNumberPicker.setMaxValue(MAX_VALUE);
            mNumberPicker.setWrapSelectorWheel(WRAP_SELECTOR_WHEEL);
            mNumberPicker.setValue(tipPercent);
        }
    }

    @Override
    public void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            mNumberPicker.clearFocus();
            int tipPercent = mNumberPicker.getValue();
            DialogPreference preference = getPreference();
            if (preference instanceof NumberPickerPreference) {
                NumberPickerPreference numberPickerPreference =
                        (NumberPickerPreference) preference;
                if (numberPickerPreference.callChangeListener(tipPercent)) {
                    numberPickerPreference.setValue(tipPercent);
                }
            }
        }
    }

}
