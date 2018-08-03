package com.elsonji.tipcalculator.repo;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.elsonji.tipcalculator.dao.TipHistoryDao;
import com.elsonji.tipcalculator.database.TipHistoryRoomDatabase;
import com.elsonji.tipcalculator.entity.TipHistory;

import java.util.List;

public class TipHistoryRepository {
    private TipHistoryDao mTipHistoryDao;
    private LiveData<List<TipHistory>> mAllTipHistories;

    public TipHistoryRepository(Application application) {
        TipHistoryRoomDatabase db = TipHistoryRoomDatabase.getDatabase(application);
        mTipHistoryDao = db.getTipHistoryDao();
        mAllTipHistories = mTipHistoryDao.getAllHistories();
    }

    public LiveData<List<TipHistory>> getAllTipHistories() {
        return mAllTipHistories;
    }

    public void insert(TipHistory tipHistory) {
        new InsertAsyncTask(mTipHistoryDao).execute(tipHistory);
    }

    private static class InsertAsyncTask extends AsyncTask<TipHistory, Void, Void> {
        private TipHistoryDao mAsyncTaskDao;

        InsertAsyncTask(TipHistoryDao dao) {
            mAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(TipHistory... tipHistories) {
            mAsyncTaskDao.insertHistory(tipHistories[0]);
            return null;
        }
    }
}
