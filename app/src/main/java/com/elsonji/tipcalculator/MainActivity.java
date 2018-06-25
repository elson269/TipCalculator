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

        //final TextView tipTextView = findViewById(R.id.tipTextView);

        final TextView personCountTextView = findViewById(R.id.personCountTextView);

       // final TextView billTotalPerPersonTextView = findViewById(R.id.totalPerPerson);

        final double[] billTotalAfterTip = new double[1];

        final double[] totalBeforeTip = new double[1];



        Button addPersonButton = findViewById(R.id.button_plus);
        addPersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                personCountTextView.setText(String.valueOf(++personCount));
                //billTotalPerPersonTextView.setText(String.valueOf(billTotalAfterTip[0] / personCount));
                //tipTextView.setText(String.valueOf(totalBeforeTip[0] * 15 / 100 / personCount));
            }
        });


        Button minusPersonButton = findViewById(R.id.button_minus);
        minusPersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (personCount > 1) {
                    personCountTextView.setText(String.valueOf(--personCount));
                   // billTotalPerPersonTextView.setText(String.valueOf(billTotalAfterTip[0] / personCount));
                }
            }
        });
    }
}
