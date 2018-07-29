package com.elsonji.tipcalculator;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SharedPreferences.
        OnSharedPreferenceChangeListener {

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
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private int personCount = 1;
    private double billAmount;
    private double tipAmountPercent;
    private double totalAmount;
    private double totalPersonAmount;
    private double tipAmount;
    private double tipPersonAmount;
    private boolean roundTip;

    private InputMethodManager mManager;

    private TipViewModel mTipViewModel;

    private boolean mFabClickedStatus = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        //Register the listener.
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
        ;

        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        Intent settingsActivityIntent = new Intent(getApplication(), SettingsActivity.class);
                        startActivity(settingsActivityIntent);
                        return true;
                    }
                }
        );

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
        personCountTextView.setText(String.valueOf(personCount));
    }

    private void calculateTip(double billAmount, double tipAmountPercent) {
        double roundedTipAmount, roundedTipPersonAmount, roundedTotalAmount, roundedTotalPersonAmount;

        tipAmount = billAmount * tipAmountPercent / 100;
        tipPersonAmount = tipAmount / personCount;
        totalAmount = billAmount + tipAmount;
        totalPersonAmount = totalAmount / personCount;

        roundedTipAmount = Math.round(tipAmount * 100d) / 100d;
        roundedTipPersonAmount = Math.round(tipPersonAmount * 100d) / 100d;
        roundedTotalAmount = Math.round(totalAmount * 100d) / 100d;
        roundedTotalPersonAmount = Math.round(totalPersonAmount * 100d) / 100d;

        if (!roundTip) {
            tipAmountTextView.setText(String.valueOf(roundedTipAmount));
            tipPersonAmountTextView.setText(String.valueOf(roundedTipPersonAmount));
            totalAmountTextView.setText(String.valueOf(roundedTotalAmount));
            totalPersonAmountTextView.setText(String.valueOf(roundedTotalPersonAmount));
        } else {
            int integerTotalEach = (int) Math.round(roundedTotalPersonAmount);
            double difference = integerTotalEach - roundedTotalPersonAmount;
            double roundedDiff = Math.round(difference * 100d) / 100d;
            roundedTipPersonAmount = roundedTipPersonAmount + roundedDiff;

            tipAmountTextView.setText(String.valueOf(roundedTipAmount));
            tipPersonAmountTextView.setText(String.valueOf(roundedTipPersonAmount));
            totalAmountTextView.setText(String.valueOf(roundedTotalAmount));
            totalPersonAmountTextView.setText(String.valueOf(integerTotalEach));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemClicked = item.getItemId();

        if (itemClicked == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_default_tip_key))) {
            String defaultTip = sharedPreferences.getString(getString(R.string.pref_default_tip_key), "");
            tipAmountEditText.setText(defaultTip);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String defaultTip = sharedPref.getString(getString(R.string.pref_default_tip_key), "");
        tipAmountEditText.setText(defaultTip);

        roundTip = sharedPref.getBoolean(getString(R.string.pref_round_tip_key), false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister VisualizerActivity as an OnPreferenceChangedListener to avoid any memory leaks.
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }


}
