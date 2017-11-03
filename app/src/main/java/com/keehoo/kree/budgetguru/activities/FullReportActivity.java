package com.keehoo.kree.budgetguru.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.keehoo.kree.budgetguru.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FullReportActivity extends AppCompatActivity {

    @BindView(R.id.sum)
    TextView sum;


    @BindView(R.id.date)
    TextView date;


    @BindView(R.id.time)
    TextView time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_report);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        sum.setText(intent.getStringExtra("sum"));
        date.setText(intent.getStringExtra("date"));
        time.setText(intent.getStringExtra("time"));


    }
}
