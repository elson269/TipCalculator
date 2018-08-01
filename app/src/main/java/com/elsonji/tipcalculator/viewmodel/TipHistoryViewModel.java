package com.elsonji.tipcalculator.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.elsonji.tipcalculator.entity.TipHistory;
import com.elsonji.tipcalculator.repo.TipHistoryRepository;

import java.util.List;

public class TipHistoryViewModel extends AndroidViewModel {
    private TipHistoryRepository mRepository;
    private LiveData<List<TipHistory>> mAllTipHistories;

    public TipHistoryViewModel(Application application) {
        super(application);
        mRepository = new TipHistoryRepository(application);
        mAllTipHistories = mRepository.getAllTipHistories();
    }

    public LiveData<List<TipHistory>> getAllTipHistories() {
        return mAllTipHistories;
    }

    public void insert(TipHistory tipHistory) {
        mRepository.insert(tipHistory);
    }
}
