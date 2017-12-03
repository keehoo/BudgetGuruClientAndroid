package com.keehoo.kree.budgetguru.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.keehoo.kree.budgetguru.R;
import com.keehoo.kree.budgetguru.activities.adapters.LocalDatabaseAdapter;
import com.keehoo.kree.budgetguru.data_models.BudgetEntryModel;
import com.keehoo.kree.budgetguru.database.AppDatabase;

import java.util.List;

public class LocalDatabaseActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_database);

        recyclerView = (RecyclerView) findViewById(R.id.local_database_recycler_view);

        AppDatabase appDatabase = AppDatabase.getAppDatabase(this);
        List<BudgetEntryModel> lista = appDatabase.budgetEntryDao().getAll();

        LocalDatabaseAdapter adapter = new LocalDatabaseAdapter(this, lista );

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

}
