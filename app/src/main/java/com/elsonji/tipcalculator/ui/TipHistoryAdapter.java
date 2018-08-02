package com.elsonji.tipcalculator.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.elsonji.tipcalculator.R;
import com.elsonji.tipcalculator.entity.TipHistory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TipHistoryAdapter extends RecyclerView.Adapter<TipHistoryAdapter.TipHistoryViewHolder> {

    private List<TipHistory> mTipHistoryList;
    private Context mContext;

    public TipHistoryAdapter(Context context) {
        mContext = context;

    }

    @NonNull
    @Override
    public TipHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FrameLayout itemView = (FrameLayout) LayoutInflater.from(mContext)
                .inflate(R.layout.tip_history_item, parent, false);
        TipHistoryViewHolder tipHistoryViewHolder = new TipHistoryViewHolder(itemView);
        return tipHistoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TipHistoryViewHolder holder, int position) {
        holder.mDateTextView.setText(mTipHistoryList.get(position).getDate());
        holder.mBillAmountTextView.setText(String.valueOf(mTipHistoryList.get(position).getBillAmount()));
        holder.mTipPercentTextView.setText(mTipHistoryList.get(position).getTipPercent());
        holder.mTipAmountTextView.setText(String.valueOf(mTipHistoryList.get(position).getTipPerPerson()));
        holder.mTipTotalTextView.setText(String.valueOf(mTipHistoryList.get(position).getTotalPerPerson()));
    }

    @Override
    public int getItemCount() {
        if (mTipHistoryList != null) {
            return mTipHistoryList.size();
        } else {
            return 0;
        }
    }

    public class TipHistoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_date_text_view)
        TextView mDateTextView;
        @BindView(R.id.item_bill_text_view)
        TextView mBillAmountTextView;
        @BindView(R.id.item_tip_percent_text_view)
        TextView mTipPercentTextView;
        @BindView(R.id.item_tip_amount_text_view)
        TextView mTipAmountTextView;
        @BindView(R.id.item_total_text_view)
        TextView mTipTotalTextView;

        public TipHistoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
