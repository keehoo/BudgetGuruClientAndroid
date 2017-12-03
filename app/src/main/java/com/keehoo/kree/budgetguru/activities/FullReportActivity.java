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
            //       System.out.println(matcher.group(2));
            //       System.out.println(matcher.group(3));
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

        Call<List<String>> getAllCats = restInterface.getAll();
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
                adapter.setOnItemClickListener(new CategoryListAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(int position, String object) {
                        setCategoryFieldAndUpdateTextView(object);
                    }
                });
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
        Double sumDouble = Double.valueOf(sumString);


        final BudgetEntryModel budgetEntry = new BudgetEntryModel();

        budgetEntry.setBudgetItem(new BudgetItem(new BigDecimal(sumDouble)));

        budgetEntry.setDateOfCost(date.getText().toString());
        budgetEntry.setTimeOfCost(time.getText().toString());
        budgetEntry.setCategory(category);
        budgetEntry.setUser(38);


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
        // TODO: implement local db.
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

