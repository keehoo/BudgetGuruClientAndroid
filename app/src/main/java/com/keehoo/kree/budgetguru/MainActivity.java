package com.keehoo.kree.budgetguru;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button createUserButton;
    private Button createBudgetEntryButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initButtons();

        createUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UserCreator.class));
            }
        });

        createBudgetEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BudgetEntry.class));
            }
        });
    }

    private void initButtons() {
        createUserButton = (Button) findViewById(R.id.create_new_user);
        createBudgetEntryButton = (Button) findViewById(R.id.create_budget_entry);
    }
}
