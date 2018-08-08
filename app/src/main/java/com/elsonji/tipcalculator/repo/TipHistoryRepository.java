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

    public void insertAll(List<TipHistory> tipHistoryList) {
        new InsertAllAsyncTask(mTipHistoryDao).execute(tipHistoryList);
    }

    public void deleteAll() {
        new DeleteAsyncTask(mTipHistoryDao).execute();
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

    private static class DeleteAsyncTask extends AsyncTask<Void, Void, Void> {
        private TipHistoryDao mAsyncTaskDao;

        DeleteAsyncTask(TipHistoryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class InsertAllAsyncTask extends AsyncTask<List<TipHistory>, Void, Void> {
        private TipHistoryDao mAllAsyncTaskDao;

        InsertAllAsyncTask(TipHistoryDao dao) {
            mAllAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(List<TipHistory>... lists) {
            mAllAsyncTaskDao.insertAllHistories(lists[0]);
            return null;
        }
    }
}

