package com.elsonji.tipcalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText billTotalEditText = findViewById(R.id.bill_amount_edit_text);


        final TextView tipTextView = findViewById(R.id.tipTextView);

        Button button15Percent = findViewById(R.id.button_15);
        button15Percent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double billTotal  = Double.parseDouble(billTotalEditText.getText().toString().trim());
                tipTextView.setText(String.valueOf(billTotal * 15 / 100));
            }
        });

        Button button18Percent = findViewById(R.id.button_18);
        button18Percent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double billTotal  = Double.parseDouble(billTotalEditText.getText().toString().trim());
                tipTextView.setText(String.valueOf(billTotal * 18 / 100));
            }
        });

        Button button20Percent = findViewById(R.id.button_20);
        button20Percent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double billTotal  = Double.parseDouble(billTotalEditText.getText().toString().trim());
                tipTextView.setText(String.valueOf(billTotal * 20 / 100));
            }
        });
    }
}
