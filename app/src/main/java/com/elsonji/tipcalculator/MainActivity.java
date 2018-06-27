package com.elsonji.tipcalculator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.billAmountEditText) EditText billAmountEditText;
    @BindView(R.id.tipAmountEditText) EditText tipAmountEditText;
    @BindView(R.id.button_minus) Button minusButton;
    @BindView(R.id.button_plus) Button plusButton;
    @BindView(R.id.personCountTextView) TextView personCountTextView;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.totalAmountTextView) TextView totalAmountTextView;
    @BindView(R.id.totalPersonAmountTextView) TextView totalPersonAmountTextView;
    @BindView(R.id.tipAmountTextView) TextView tipAmountTextView;
    @BindView(R.id.tipPersonAmountTextView) TextView tipPersonAmountTextView;

    int personCount = 1;
    double billAmount;
    double tipAmountPercent;
    double totalAmount;
    double totalPersonAmount;
    double tipAmount;
    double tipPersonAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                personCountTextView.setText(String.valueOf(++personCount));
            }
        });

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (personCount > 1)
                personCountTextView.setText(String.valueOf(--personCount));
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String billAmountString = billAmountEditText.getText().toString().trim();
                if (billAmountString.matches("[0-9]+")) {
                    billAmount = Double.parseDouble(billAmountString);
                } else {
                    Toast.makeText(getApplicationContext(), "Numbers only!", Toast.LENGTH_LONG).show();
                }

                String tipAmountString = tipAmountEditText.getText().toString().trim();
                if (tipAmountString.matches("[0-9]+")) {
                    tipAmountPercent = Double.parseDouble(tipAmountString);
                } else {
                    Toast.makeText(getApplicationContext(), "Numbers only!", Toast.LENGTH_LONG).show();
                }
                 tipAmount = billAmount * tipAmountPercent / 100;
                 tipPersonAmount = tipAmount / personCount;
                 totalAmount = billAmount + tipAmount;
                 totalPersonAmount = totalAmount / personCount;

                 tipAmountTextView.setText(String.valueOf(tipAmount));
                 tipPersonAmountTextView.setText(String.valueOf(tipPersonAmount));
                 totalAmountTextView.setText(String.valueOf(totalAmount));
                 totalPersonAmountTextView.setText(String.valueOf(totalPersonAmount));
            }
        });

    }
}
