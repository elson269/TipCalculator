package com.elsonji.tipcalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int personCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText billTotalEditText = findViewById(R.id.bill_amount_edit_text);

        final TextView tipTextView = findViewById(R.id.tipTextView);

        final TextView personCountTextView = findViewById(R.id.personCountTextView);

        final TextView billTotalTextView = findViewById(R.id.billTotalTextView);

        final TextView billTotalPerPersonTextView = findViewById(R.id.totalPerPerson);

        final double[] billTotalAfterTip = new double[1];

        Button button15Percent = findViewById(R.id.button_15);
        button15Percent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amountString = billTotalEditText.getText().toString().trim();
                if (amountString.length() > 0) {
                    double totalBeforeTip = Double.parseDouble(billTotalEditText.getText().toString().trim());
                    tipTextView.setText(String.valueOf(totalBeforeTip * 15 / 100));
                    billTotalAfterTip[0] = totalBeforeTip + totalBeforeTip * 15 / 100;
                    billTotalTextView.setText(String.valueOf(totalBeforeTip + totalBeforeTip * 15 / 100));
                    billTotalPerPersonTextView.setText(String.valueOf(totalBeforeTip + totalBeforeTip * 15 / 100));
                }
            }
        });

        Button button18Percent = findViewById(R.id.button_18);
        button18Percent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (billTotalEditText.getText().length() > 0) {
                    double totalBeforeTip = Double.parseDouble(billTotalEditText.getText().toString().trim());
                    tipTextView.setText(String.valueOf(totalBeforeTip * 18 / 100));
                    billTotalAfterTip[0] = totalBeforeTip + totalBeforeTip * 18 / 100;
                    billTotalTextView.setText(String.valueOf(totalBeforeTip + totalBeforeTip * 18 / 100));
                    billTotalPerPersonTextView.setText(String.valueOf(totalBeforeTip + totalBeforeTip * 18 / 100));
                }
            }
        });

        Button button20Percent = findViewById(R.id.button_20);
        button20Percent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (billTotalEditText.getText().length() > 0) {
                    double totalBeforeTip = Double.parseDouble(billTotalEditText.getText().toString().trim());
                    tipTextView.setText(String.valueOf(totalBeforeTip * 20 / 100));
                    billTotalAfterTip[0] = totalBeforeTip + totalBeforeTip * 20 / 100;
                    billTotalTextView.setText(String.valueOf(totalBeforeTip + totalBeforeTip * 20 / 100));
                    billTotalPerPersonTextView.setText(String.valueOf(totalBeforeTip + totalBeforeTip * 20 / 100));
                }
            }
        });

        Button addPersonButton = findViewById(R.id.button_plus);
        addPersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                personCountTextView.setText(String.valueOf(++personCount));
                billTotalPerPersonTextView.setText(String.valueOf(billTotalAfterTip[0] / personCount));
            }
        });


        Button minusPersonButton = findViewById(R.id.button_minus);
        minusPersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (personCount > 1) {
                    personCountTextView.setText(String.valueOf(--personCount));
                    billTotalPerPersonTextView.setText(String.valueOf(billTotalAfterTip[0] / personCount));
                }
            }
        });
    }
}
