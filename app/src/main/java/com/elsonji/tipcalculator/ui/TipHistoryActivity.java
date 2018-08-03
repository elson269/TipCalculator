package com.elsonji.tipcalculator.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.elsonji.tipcalculator.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TipHistoryActivity extends AppCompatActivity {
    @BindView(R.id.tip_history_recycler_view)
    RecyclerView mTipHistoryRecyclerView;

    LinearLayoutManager mLinearLayoutManager;

    TipHistoryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_history);
        ButterKnife.bind(this);
        mAdapter = new TipHistoryAdapter(this);
        mTipHistoryRecyclerView.setLayoutManager(mLinearLayoutManager);

        mTipHistoryRecyclerView.setAdapter(mAdapter);
    }
}
