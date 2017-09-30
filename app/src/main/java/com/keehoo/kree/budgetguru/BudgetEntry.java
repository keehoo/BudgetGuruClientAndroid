package com.keehoo.kree.budgetguru;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.keehoo.kree.budgetguru.Rest.RestInterface;

import java.math.BigDecimal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BudgetEntry extends AppCompatActivity {

    EditText cost;
    EditText userId;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_entry);

        cost = (EditText) findViewById(R.id.cost);
        userId = (EditText) findViewById(R.id.user_id);
        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BudgetEntryModel budgetEntry = new BudgetEntryModel();
                budgetEntry.setBudgetItem(new BudgetItem(new BigDecimal(1000.0)));
                //budgetEntry.setCost(true);
                budgetEntry.setUser(1);

                RestInterface restInterface = RestInterface.retrofit.create(RestInterface.class);

                Call<Void> user1 = restInterface.addBudget(budgetEntry);
                String reqBody = user1.request().body().toString();
                Toast.makeText(BudgetEntry.this, "Request body : "+ reqBody, Toast.LENGTH_SHORT).show();

                String postCall = new Gson().toJson(budgetEntry);

                user1.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        System.out.println("LOG TAG "+call.request().toString());
                        System.out.println("LOG TAG "+call.toString());
                        System.out.println("LOG TAG "+response.message());
                        System.out.println("LOG TAG "+response.toString());
                        System.out.println("LOG TAG "+response.raw().message());

                        Toast.makeText(BudgetEntry.this, "success --> response code: "+response.code(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(BudgetEntry.this, "Failure", Toast.LENGTH_SHORT).show();
                        System.out.println(call.toString());
                        System.out.println(t.getLocalizedMessage());
                    }
                });
            }
        });

/*
        {
            "id": 2,
                "user": 1,
                "budgetItem": {
            "value": 500,
                    "cost": true
        }
        }*/




    }
}
