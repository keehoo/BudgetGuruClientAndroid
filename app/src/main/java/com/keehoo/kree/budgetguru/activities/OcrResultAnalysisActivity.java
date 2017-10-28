package com.keehoo.kree.budgetguru.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.vision.text.Line;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.keehoo.kree.budgetguru.R;
import com.keehoo.kree.budgetguru.activities.adapters.OcrResultAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class OcrResultAnalysisActivity extends AppCompatActivity {

    List<String> ocrResultList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr_result_analysis);
        Intent intent = getIntent();
        String ocrResultList = intent.getExtras().getString("ocr_results");
        Type listType = new TypeToken<ArrayList<Line>>(){}.getType();
        Gson gson = new Gson();
        List<Line> listOfLines = gson.fromJson(ocrResultList, listType);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        OcrResultAdapter adapter = new OcrResultAdapter(this, listOfLines);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
