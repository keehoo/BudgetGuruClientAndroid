package com.keehoo.kree.budgetguru;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.create_new_user)
    Button createUserButton;
    @BindView(R.id.create_budget_entry)
    Button createBudgetEntryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }
    @OnClick(R.id.create_new_user)
     void startNewUserActivity() {
        startActivity(new Intent(MainActivity.this, UserCreatorActivity.class));
    }

    @OnClick(R.id.create_budget_entry)
     void startNewBudgetEntryActivity() {
        startActivity(new Intent(MainActivity.this, BudgetEntry.class));
    }

  ///
}
