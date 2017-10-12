package com.keehoo.kree.budgetguru.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.keehoo.kree.budgetguru.R;
import com.keehoo.kree.budgetguru.activities.adapters.OcrResultAdapter;

import java.util.ArrayList;
import java.util.List;

public class OcrResultAnalysisActivity extends AppCompatActivity {

    List<String> ocrResultList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr_result_analysis);
        Intent intent = getIntent();
        List<String> ocrResultList = new ArrayList<>();
        ocrResultList = intent.getExtras().getStringArrayList("ocr_results");

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        OcrResultAdapter adapter = new OcrResultAdapter(this, ocrResultList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
