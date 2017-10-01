package com.keehoo.kree.budgetguru.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.keehoo.kree.budgetguru.R;
import com.keehoo.kree.budgetguru.data_models.BudgetEntryModel;
import com.keehoo.kree.budgetguru.data_models.BudgetItem;
import com.keehoo.kree.budgetguru.rest.RestInterface;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BudgetEntryActivity extends AppCompatActivity {

    @BindView(R.id.cost)
    EditText cost;
    @BindView(R.id.user_id)
    EditText userId;
    @BindView(R.id.add)
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_entry);
        ButterKnife.bind(this);
        userId.setVisibility(View.GONE);
    }
        @OnClick(R.id.add)
        protected void addNewBudgetEntry() {

                BudgetEntryModel budgetEntry = new BudgetEntryModel();
                Double price = Double.parseDouble(cost.getText().toString());
                budgetEntry.setBudgetItem(new BudgetItem(new BigDecimal(price)));
                budgetEntry.setUser(1);

                RestInterface restInterface = RestInterface.retrofit.create(RestInterface.class);

                Call<Void> user1 = restInterface.addBudget(budgetEntry);
                String reqBody = user1.request().body().toString();
                Toast.makeText(BudgetEntryActivity.this, "Request body : "+ reqBody, Toast.LENGTH_SHORT).show();
                user1.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        System.out.println("LOG TAG "+call.request().toString());
                        System.out.println("LOG TAG "+call.toString());
                        System.out.println("LOG TAG "+response.message());
                        System.out.println("LOG TAG "+response.toString());
                        System.out.println("LOG TAG "+response.raw().message());

                        Toast.makeText(BudgetEntryActivity.this, "success --> response code: "+response.code(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(BudgetEntryActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                        System.out.println(call.toString());
                        System.out.println(t.getLocalizedMessage());
                    }
                });
            }
    }

