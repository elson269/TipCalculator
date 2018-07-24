package com.elsonji.tipcalculator;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DefaultTipActivity extends AppCompatActivity {
    @BindView(R.id.default_tip)
    EditText defaultTipEditText;

    @BindView(R.id.tip_confirm)
    Button tipConfirmButton;

    float mDefaultTip;
    SharedPreferences mTipSharedPref;
    SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_tip);
        ButterKnife.bind(this);

        mTipSharedPref = getSharedPreferences(
                getString(R.string.preference_default_tip_key), MODE_PRIVATE);
        mEditor = mTipSharedPref.edit();

        float savedDefaultTip = getDefaultTip();
        if (savedDefaultTip != 0) {
            defaultTipEditText.setText(String.valueOf(savedDefaultTip));
        }

        tipConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String defaultTip = defaultTipEditText.getText().toString().trim();
                if (defaultTip.matches("[0-9.]+")) {
                    mDefaultTip = Float.parseFloat(defaultTip);
                    if (mDefaultTip != 0) {
                        mEditor.putFloat(getString(R.string.default_tip_key), mDefaultTip);
                        mEditor.apply();
                        Toast.makeText(getApplicationContext(), "Tip Saved.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter a tip percentage.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    float getDefaultTip() {
        return mTipSharedPref.getFloat(getString(R.string.default_tip_key), 0);
    }
}
