package com.elsonji.tipcalculator.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.elsonji.tipcalculator.R;
import com.elsonji.tipcalculator.entity.TipHistory;
import com.elsonji.tipcalculator.viewmodel.TipHistoryViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TipHistoryActivity extends AppCompatActivity {
    @BindView(R.id.tip_history_recycler_view)
    RecyclerView mTipHistoryRecyclerView;
    LinearLayoutManager mLinearLayoutManager;

    TipHistoryAdapter mAdapter;
    private TipHistoryViewModel mTipHistoryViewModel;
    private TipHistoryAdapter mTipHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_history);
        ButterKnife.bind(this);

        mAdapter = new TipHistoryAdapter(this);

        mTipHistoryRecyclerView.setAdapter(mAdapter);
        mTipHistoryRecyclerView.setLayoutManager(mLinearLayoutManager);

        mTipHistoryViewModel = ViewModelProviders.of(this).get(TipHistoryViewModel.class);
        mTipHistoryAdapter = new TipHistoryAdapter(this);
        mTipHistoryViewModel.getAllTipHistories()
                .observe(this, new Observer<List<TipHistory>>() {
            @Override
            public void onChanged(@Nullable List<TipHistory> tipHistoryList) {
                mTipHistoryAdapter.setTipHistories(tipHistoryList);
            }
        });
    }
}
