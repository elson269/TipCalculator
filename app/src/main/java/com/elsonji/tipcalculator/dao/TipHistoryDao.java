package com.elsonji.tipcalculator.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.elsonji.tipcalculator.entity.TipHistory;

import java.util.List;

@Dao
public interface TipHistoryDao {
    @Query("SELECT * FROM tip_history_table")
    LiveData<List<TipHistory>> getAllHistories();

    @Insert
    void insertHistory(TipHistory... tipHistory);

    @Delete
    void deleteHistories(TipHistory...tipHistories);

}
