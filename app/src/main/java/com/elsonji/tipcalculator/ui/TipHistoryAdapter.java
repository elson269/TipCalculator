package com.elsonji.tipcalculator.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elsonji.tipcalculator.R;
import com.elsonji.tipcalculator.entity.TipHistory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TipHistoryAdapter extends RecyclerView.Adapter<TipHistoryAdapter.TipHistoryViewHolder> {

    private List<TipHistory> mTipHistoryList;
    private Context mContext;
    private OnHistoryItemClickListener mListener;

    public TipHistoryAdapter(Context context, OnHistoryItemClickListener listener) {
        mContext = context;
        mListener = listener;
    }

    @NonNull
    @Override
    public TipHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout itemView = (LinearLayout) LayoutInflater.from(mContext)
                .inflate(R.layout.tip_history_item, parent, false);
        TipHistoryViewHolder tipHistoryViewHolder = new TipHistoryViewHolder(itemView);
        return tipHistoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final TipHistoryViewHolder holder, int position) {
        holder.mDateTextView.setText(mTipHistoryList.get(position).getDate());
        //holder.mBillAmountTextView.setText(String.valueOf(mTipHistoryList.get(position).getBillAmount()));
        holder.mTipPercentTextView.setText(String.valueOf(mTipHistoryList.get(position).getTipPercent()));
        holder.mTipAmountTextView.setText(String.valueOf(mTipHistoryList.get(position).getTipPerPerson()));
        holder.mTipTotalTextView.setText(String.valueOf(mTipHistoryList.get(position).getTotalPerPerson()));
        holder.mDeleteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int positionClicked = holder.getAdapterPosition();
                mListener.onItemClick(view, positionClicked);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mTipHistoryList != null) {
            return mTipHistoryList.size();
        } else {
            return 0;
        }
    }

    public void setTipHistories(List<TipHistory> tipHistoryList) {
        mTipHistoryList = tipHistoryList;
        notifyDataSetChanged();
    }

    public class TipHistoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_date_text_view)
        TextView mDateTextView;
        //@BindView(R.id.item_bill_text_view)
        //TextView mBillAmountTextView;
        @BindView(R.id.item_tip_percent_text_view)
        TextView mTipPercentTextView;
        @BindView(R.id.item_tip_amount_text_view)
        TextView mTipAmountTextView;
        @BindView(R.id.item_total_text_view)
        TextView mTipTotalTextView;
        @BindView(R.id.delete_button)
        ImageButton mDeleteImageButton;

        TipHistoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnHistoryItemClickListener {
        void onItemClick(View view, int position);
    }
}
