package com.keehoo.kree.budgetguru.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.keehoo.kree.budgetguru.R;
import com.keehoo.kree.budgetguru.activities.adapters.CategoryListAdapter;
import com.keehoo.kree.budgetguru.activities.adapters.OcrResultAdapter;
import com.keehoo.kree.budgetguru.data_models.BudgetEntryModel;
import com.keehoo.kree.budgetguru.data_models.BudgetItem;
import com.keehoo.kree.budgetguru.database.AppDatabase;
import com.keehoo.kree.budgetguru.repositories.SessionData;
import com.keehoo.kree.budgetguru.rest.RestInterface;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FullReportActivity extends AppCompatActivity {

    RestInterface restInterface = RestInterface.retrofit.create(RestInterface.class);
    List<String> catList;

    private String category;

    private RecyclerView categoryList;
    @BindView(R.id.sum)
    TextView sum;


    @BindView(R.id.date)
    TextView date;


    @BindView(R.id.time)
    TextView time;


    @BindView(R.id.category)
    TextView categoryTextView;


    @BindView(R.id.upload)
    Button uploadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_report);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        sum.setText(intent.getStringExtra("sum"));

        String mydata = intent.getStringExtra("date");
        Pattern pattern = Pattern.compile(OcrResultAdapter.DATE_REGEX_WITH_DASH_DATE_ONLY);
        Matcher matcher = pattern.matcher(mydata);
        String dateOnly = null;
        if (matcher.find()) {
            dateOnly = matcher.group(0);
            System.out.println(matcher.group(0));
            System.out.println(matcher.group(1));
        }
        if (dateOnly != null) {
            date.setText(dateOnly);
        } else {
            date.setText(intent.getStringExtra("date"));
        }
        time.setText(intent.getStringExtra("time"));
        categoryList = (RecyclerView) findViewById(R.id.category_recycler_view);
        getList();


    }

    private void getList() {
        Call<List<String>> getAllCats = restInterface.getAllCategories();
        getAllCats.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                Log.d(getClass().getName(), response.message());
                if (!response.message().contains("Not Found")) {
                    catList = response.body();
                } else {
                    catList = Collections.emptyList();
                }

                CategoryListAdapter adapter = new CategoryListAdapter(FullReportActivity.this, catList);
                adapter.setOnItemClickListener((position, object) -> setCategoryFieldAndUpdateTextView(object));
                categoryList.setLayoutManager(new LinearLayoutManager(FullReportActivity.this));
                categoryList.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.d("FAIL", t.getLocalizedMessage());
            }
        });
    }


    private void setCategoryFieldAndUpdateTextView(String object) {
        categoryTextView.setText(object);
        setCategory(object);
    }

    @OnClick(R.id.upload)
    void upload() {

        String sumString = sum.getText().toString().replace(",", ".");

        Double sumDouble = 0.0;
        try {
            sumDouble = Double.valueOf(sumString);
        } catch (NumberFormatException e) {
            Log.e("Error", "Error while reading ");
            //TODO: Handle error while parsing double value from receipt.
        }

        final BudgetEntryModel budgetEntry = new BudgetEntryModel();

        budgetEntry.setBudgetItem(new BudgetItem(new BigDecimal(sumDouble)));
        budgetEntry.setDateOfCost(date.getText().toString());
        budgetEntry.setTimeOfCost(time.getText().toString());
        budgetEntry.setCategory(category);
        budgetEntry.setUser(new SessionData(this).getLoggedUserId());


        Call<Void> addBudgetEntry = restInterface.addBudget(budgetEntry);
        addBudgetEntry.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() != 404) {
                    Log.d("Add response", response.message());
                    Log.d("Add response", "code: " + response.code());

                    Toast.makeText(FullReportActivity.this, "Added budget entry to " + budgetEntry.getCategory() + " category, value of " + budgetEntry.getValue(), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(FullReportActivity.this, "Server is not running, saving to local db", Toast.LENGTH_LONG).show();
                    addToLocalDb(budgetEntry);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println(call.toString());
                System.out.println(t.getLocalizedMessage());
            }
        });
    }

    private void addToLocalDb(BudgetEntryModel budgetEntry) {
        Log.d("DataBase", "Adding budget entry to db ");
        AppDatabase appDatabase = AppDatabase.getAppDatabase(this);
        appDatabase.budgetEntryDao().insert(budgetEntry);
        Log.d("DataBase", "Adding finished");

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

