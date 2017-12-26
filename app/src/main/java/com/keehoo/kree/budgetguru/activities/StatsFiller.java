package com.keehoo.kree.budgetguru.activities;

import com.keehoo.kree.budgetguru.data_models.BudgetEntryModel;

import java.util.List;

/**
 * Created by krzysztof on 26.12.2017.
 */

public interface StatsFiller {

    void fillStatisticsList(List<BudgetEntryModel> list);

}
