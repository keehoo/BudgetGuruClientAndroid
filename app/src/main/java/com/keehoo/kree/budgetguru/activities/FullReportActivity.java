package com.keehoo.kree.budgetguru.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.keehoo.kree.budgetguru.R;
import com.keehoo.kree.budgetguru.activities.adapters.CategoryListAdapter;
import com.keehoo.kree.budgetguru.data_models.BudgetEntryModel;
import com.keehoo.kree.budgetguru.data_models.BudgetItem;
import com.keehoo.kree.budgetguru.data_models.Category;
import com.keehoo.kree.budgetguru.rest.RestInterface;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FullReportActivity extends AppCompatActivity {

    RestInterface restInterface = RestInterface.retrofit.create(RestInterface.class);
    List<String> catList;
    private RecyclerView categoryList;


    @BindView(R.id.sum)
    TextView sum;


    @BindView(R.id.date)
    TextView date;


    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.upload)
    Button uploadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_report);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        sum.setText(intent.getStringExtra("sum"));
        date.setText(intent.getStringExtra("date"));
        time.setText(intent.getStringExtra("time"));
getList();

        categoryList = (RecyclerView) findViewById(R.id.category_recycler_view);



    }

    private void getList() {

        Call<List<String>> getAllCats = restInterface.getAll();
        getAllCats.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
            catList = response.body();
                CategoryListAdapter adapter = new CategoryListAdapter(FullReportActivity.this, catList);
                categoryList.setLayoutManager(new LinearLayoutManager(FullReportActivity.this));
                categoryList.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.d("FAIL", t.getLocalizedMessage());
            }
        });

    }

    @OnClick(R.id.upload)
    void upload() {

            String sumString = sum.getText().toString().replace(",", ".");
            Double sumDouble = Double.valueOf(sumString);



            BudgetEntryModel budgetEntry = new BudgetEntryModel();

            budgetEntry.setBudgetItem(new BudgetItem(new BigDecimal(sumDouble)));
            String[] splitDate = date.getText().toString().split("-");
            long year = Long.valueOf(splitDate[0]);
            int month = Integer.valueOf(splitDate[1]);
            int day = Integer.valueOf(splitDate[2]);
            budgetEntry.setDateOfCost(date.getText().toString());
            budgetEntry.setTimeOfCost(time.getText().toString());
            budgetEntry.setCategory(Category.CAR);
            budgetEntry.setUser(38);




            Call<Void> addBudgetEntry = restInterface.addBudget(budgetEntry);
            addBudgetEntry.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    System.out.println("LOG TAG "+call.request().body().toString());
                    System.out.println("LOG TAG "+call.request().body().contentType());
                    System.out.println("LOG TAG "+call.request().method());
                    System.out.println("LOG TAG "+call.request().url());
                    System.out.println("LOG TAG "+call.request().toString());
                    System.out.println("LOG TAG "+call.toString());
                    System.out.println("LOG TAG "+response.message());
                    System.out.println("LOG TAG "+response.toString());
                    System.out.println("LOG TAG "+response.raw().message());

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    System.out.println(call.toString());
                    System.out.println(t.getLocalizedMessage());
                }
            });
        }
    }

