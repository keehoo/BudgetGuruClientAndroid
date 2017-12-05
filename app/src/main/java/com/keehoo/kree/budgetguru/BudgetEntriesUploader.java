package com.keehoo.kree.budgetguru;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.keehoo.kree.budgetguru.activities.LocalDatabaseActivity;
import com.keehoo.kree.budgetguru.daos.BudgetEntryDao;
import com.keehoo.kree.budgetguru.data_models.BudgetEntryModel;
import com.keehoo.kree.budgetguru.database.AppDatabase;
import com.keehoo.kree.budgetguru.rest.RestInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BudgetEntriesUploader extends IntentService {

    BudgetEntryDao dao;
    private RestInterface restInterface;

    public BudgetEntriesUploader() {
        super("BudgetEntriesUploader");
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d("ON START", "Yup... service did start");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d("ON HANDLE INTENT", "SERVICE");
        if (intent != null) {
            if (intent.getAction().equals(LocalDatabaseActivity.UPLOAD_TO_SERVER)) {
                AppDatabase appDatabase = AppDatabase.getAppDatabase(this);
                List<BudgetEntryModel> listToBeUploaded = appDatabase.budgetEntryDao().getAll();

                restInterface = RestInterface.retrofit.create(RestInterface.class);

                for (BudgetEntryModel e : listToBeUploaded) {
                    e.setId(0L);
                    Call<Void> uploadCall = restInterface.addBudget(e);
                    uploadCall.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(BudgetEntriesUploader.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(BudgetEntriesUploader.this, "FAILURE", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }
    }
}
