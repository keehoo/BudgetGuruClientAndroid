package com.keehoo.kree.budgetguru.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.keehoo.kree.budgetguru.data_models.BudgetEntryModel;

import java.util.List;

/**
 * Created by krzysztof on 03.12.2017.
 */

@Dao
public interface BudgetEntryDao {

    @Insert
    void insert(BudgetEntryModel budgetEntry);

    @Query("SELECT * FROM BudgetEntry")
    List<BudgetEntryModel> getAll();
}
