package com.keehoo.kree.budgetguru.activities;

import com.keehoo.kree.budgetguru.data_models.BudgetEntryModel;

import java.util.List;

/**
 * Created by krzysztof on 09.12.2017.
 */

public interface ChartFiller {

    public void fillChart(List<BudgetEntryModel> list);

}
