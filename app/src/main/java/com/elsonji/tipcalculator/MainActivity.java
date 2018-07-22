package com.elsonji.tipcalculator;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.billAmountEditText)
    EditText billAmountEditText;
    @BindView(R.id.tipAmountEditText)
    EditText tipAmountEditText;
    @BindView(R.id.button_minus)
    ImageButton minusButton;
    @BindView(R.id.button_plus)
    ImageButton plusButton;
    @BindView(R.id.personCountTextView)
    TextView personCountTextView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.totalAmountTextView)
    TextView totalAmountTextView;
    @BindView(R.id.totalPersonAmountTextView)
    TextView totalPersonAmountTextView;
    @BindView(R.id.tipAmountTextView)
    TextView tipAmountTextView;
    @BindView(R.id.tipPersonAmountTextView)
    TextView tipPersonAmountTextView;

    private int personCount = 1;
    private double billAmount;
    private double tipAmountPercent;
    private double totalAmount;
    private double totalPersonAmount;
    private double tipAmount;
    private double tipPersonAmount;
    private float defaultTip;

    private InputMethodManager mManager;

    private TipViewModel mTipViewModel;

    private boolean mFabClickedStatus = false;

    private SharedPreferences mDefaultTipPref;
    private SharedPreferences.Editor mDefaultTipEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mDefaultTipPref = getSharedPreferences(getString(R.string.preference_default_tip_key), MODE_PRIVATE);
        defaultTip = mDefaultTipPref.getFloat(getString(R.string.default_tip_key), 0);
        if (defaultTip != 0) {
            tipAmountEditText.setText(String.valueOf(defaultTip));
        }
        
        mTipViewModel = ViewModelProviders.of(this).get(TipViewModel.class);
        displayPersonCount();

        mFabClickedStatus = mTipViewModel.getFabClickedStatus();
        if (mFabClickedStatus) {
            double savedBillAmount = mTipViewModel.getBillAmount();
            double savedTipPercent = mTipViewModel.getTipAmountPercent();
            calculateTip(savedBillAmount, savedTipPercent);
        }

        mManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ++personCount;
                mTipViewModel.setPersonCount(personCount);
                personCountTextView.setText(String.valueOf(personCount));
            }
        });

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (personCount > 1) {
                    --personCount;
                    mTipViewModel.setPersonCount(personCount);
                    personCountTextView.setText(String.valueOf(personCount));
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTipViewModel.setFabClickedStatus(true);

                String billAmountString = billAmountEditText.getText().toString().trim();
                String tipAmountString = tipAmountEditText.getText().toString().trim();

                if (!billAmountString.isEmpty() && !tipAmountString.isEmpty()) {
                    mManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }

                if (billAmountString.matches("[0-9.]+")) {
                    billAmount = Double.parseDouble(billAmountString);
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter a number", Toast.LENGTH_LONG).show();
                }

                if (tipAmountString.matches("[0-9.]+")) {
                    tipAmountPercent = Double.parseDouble(tipAmountString);
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter a number", Toast.LENGTH_LONG).show();
                }

                mTipViewModel.setBillAmount(billAmount);
                mTipViewModel.setTipAmountPercent(tipAmountPercent);

                calculateTip(billAmount, tipAmountPercent);
            }

        });

    }

    private void displayPersonCount() {
        personCount = mTipViewModel.getPersonCount();
        personCountTextView.setText(String.valueOf(personCount) );
    }

    private void calculateTip(double billAmount, double tipAmountPercent) {

        tipAmount = billAmount * tipAmountPercent / 100;
        tipPersonAmount = tipAmount / personCount;
        totalAmount = billAmount + tipAmount;
        totalPersonAmount = totalAmount / personCount;

        tipAmountTextView.setText(String.valueOf(Math.round(tipAmount * 100d) / 100d));
        tipPersonAmountTextView.setText(String.valueOf(Math.round(tipPersonAmount * 100d) / 100d));
        totalAmountTextView.setText(String.valueOf(Math.round(totalAmount * 100d) / 100d));
        totalPersonAmountTextView.setText(String.valueOf(Math.round(totalPersonAmount * 100d) / 100d));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemClicked = item.getItemId();
        if (itemClicked == R.id.action_default_tip) {
            Intent defaultTipActivityIntent = new Intent(this, DefaultTipActivity.class);
            startActivity(defaultTipActivityIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
