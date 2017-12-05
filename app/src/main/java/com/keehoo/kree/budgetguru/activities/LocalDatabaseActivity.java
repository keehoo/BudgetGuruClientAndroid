package com.keehoo.kree.budgetguru.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.keehoo.kree.budgetguru.BudgetEntriesUploader;
import com.keehoo.kree.budgetguru.R;
import com.keehoo.kree.budgetguru.activities.adapters.LocalDatabaseAdapter;
import com.keehoo.kree.budgetguru.data_models.BudgetEntryModel;
import com.keehoo.kree.budgetguru.database.AppDatabase;

import java.util.List;

public class LocalDatabaseActivity extends AppCompatActivity {

    public static final String UPLOAD_TO_SERVER = "UPLOAD_TO_SERVER";
    RecyclerView recyclerView;
    private List<BudgetEntryModel> listOfNotYetUploadedBudgetEntries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_database);
        setUpList();

        uploadIfPossible();



    }

    private void uploadIfPossible() {
        Intent intent = new Intent(this, BudgetEntriesUploader.class);
        intent.setAction(UPLOAD_TO_SERVER);
        startService(intent);
    }

    private void setUpList() {
        recyclerView = (RecyclerView) findViewById(R.id.local_database_recycler_view);

        AppDatabase appDatabase = AppDatabase.getAppDatabase(this);
        listOfNotYetUploadedBudgetEntries = appDatabase.budgetEntryDao().getAll();

        LocalDatabaseAdapter adapter = new LocalDatabaseAdapter(this, listOfNotYetUploadedBudgetEntries);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

}
