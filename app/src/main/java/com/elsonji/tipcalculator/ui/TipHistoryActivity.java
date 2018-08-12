package com.elsonji.tipcalculator.ui;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.elsonji.tipcalculator.R;
import com.elsonji.tipcalculator.entity.TipHistory;
import com.elsonji.tipcalculator.viewmodel.TipHistoryViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TipHistoryActivity extends AppCompatActivity implements
        TipHistoryAdapter.OnHistoryItemClickListener {
    @BindView(R.id.tip_history_recycler_view)
    RecyclerView mTipHistoryRecyclerView;

    private TipHistoryViewModel mTipHistoryViewModel;
    private TipHistoryAdapter mTipHistoryAdapter;
    //public LifecycleOwner mOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_history);
        ButterKnife.bind(this);

        mTipHistoryViewModel = ViewModelProviders.of(this).get(TipHistoryViewModel.class);
        mTipHistoryAdapter = new TipHistoryAdapter(this, this);
        mTipHistoryRecyclerView.setAdapter(mTipHistoryAdapter);
        mTipHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTipHistoryRecyclerView.addItemDecoration(new DividerItemDecoration(mTipHistoryRecyclerView
                .getContext(), DividerItemDecoration.VERTICAL));
        //mOwner = this;
        mTipHistoryViewModel.getAllTipHistories()
                .observe(this, new Observer<List<TipHistory>>() {
                    @Override
                    public void onChanged(@Nullable List<TipHistory> tipHistoryList) {
                        mTipHistoryAdapter.setTipHistories(tipHistoryList);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.history_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemClicked = item.getItemId();
        if (itemClicked == R.id.action_delete_all) {
            showDeleteConfirmationDialog();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(View view, int position) {
        mTipHistoryViewModel.delete(position);
        final TipHistory deletedTipHistory = mTipHistoryViewModel.getAllTipHistories().getValue().get(position);
        Snackbar.make(findViewById(R.id.tip_history_coordinator_layout),
                "History deleted", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mTipHistoryViewModel.insert(deletedTipHistory);
                    }
                }).show();
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure to delete all the records?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int id) {
                final List<TipHistory> tipHistoryList = mTipHistoryViewModel.getAllTipHistories().getValue();
                if (tipHistoryList != null && tipHistoryList.size() != 0) {
                    mTipHistoryViewModel.deleteAll();
                    Snackbar.make(findViewById(R.id.tip_history_coordinator_layout),
                            "All history deleted", Snackbar.LENGTH_LONG)
                            .setAction("UNDO", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    mTipHistoryViewModel.insertAll(tipHistoryList);
                                }
                            }).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int id) {
                if (dialogInterface != null) {
                    dialogInterface.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
