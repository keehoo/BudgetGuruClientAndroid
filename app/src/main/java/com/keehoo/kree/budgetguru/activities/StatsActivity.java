package com.keehoo.kree.budgetguru.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.keehoo.kree.budgetguru.Data;
import com.keehoo.kree.budgetguru.R;
import com.keehoo.kree.budgetguru.activities.adapters.StatisticsAdapter;
import com.keehoo.kree.budgetguru.data_models.BudgetEntryModel;
import com.keehoo.kree.budgetguru.repositories.SessionData;

import java.util.ArrayList;
import java.util.List;

public class StatsActivity extends AppCompatActivity implements StatsFiller{

    RecyclerView statisticsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        statisticsRecyclerView = (RecyclerView) findViewById(R.id.stats_recycler_view);
        Data data = new Data(this);
        data.getAllBudgetEntries();
    }


    @Override
    public void fillStatisticsList(List<BudgetEntryModel> list) {
        List<BudgetEntryModel> filteredList= new ArrayList<>();
        long currentUserId = new SessionData(this).getLoggedUserId();
        for (BudgetEntryModel b : list) {
            if (b.getUser() == currentUserId) {
                filteredList.add(b);
            }
        }
        StatisticsAdapter adapter = new StatisticsAdapter(this, filteredList);
        statisticsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        statisticsRecyclerView.setAdapter(adapter);
    }
}
