package com.elsonji.tipcalculator.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.elsonji.tipcalculator.dao.TipHistoryDao;
import com.elsonji.tipcalculator.entity.TipHistory;

@Database(entities = {TipHistory.class}, version = 1)
public abstract class TipHistoryRoomDatabase extends RoomDatabase {

    public abstract TipHistoryDao getTipHistoryDao();
    private static TipHistoryRoomDatabase sInstance;

    public static TipHistoryRoomDatabase getDatabase(final Context context) {
        if (sInstance == null) {
            synchronized (TipHistoryRoomDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            TipHistoryRoomDatabase.class, "tip_history_database")
                            .build();
                }
            }
        }
        return sInstance;
    }
}
