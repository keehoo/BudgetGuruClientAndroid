package com.keehoo.kree.budgetguru.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.vision.text.Line;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.keehoo.kree.budgetguru.R;
import com.keehoo.kree.budgetguru.activities.adapters.OcrResultAdapter;
import com.keehoo.kree.budgetguru.activities.adapters.OcrResultWrapper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class OcrResultAnalysisActivity extends AppCompatActivity {


    private List<Line> listOfLines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr_result_analysis);
        Intent intent = getIntent();
        String ocrResultList = intent.getExtras().getString("ocr_results");
        Type listType = new TypeToken<ArrayList<Line>>() {
        }.getType();
        Gson gson = new Gson();
        listOfLines = gson.fromJson(ocrResultList, listType);
        List<OcrResultWrapper> listToBePassedToAdapter = new ArrayList<>();
        listToBePassedToAdapter.add(analyzeLines());


        if (!listToBePassedToAdapter.isEmpty()) {
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            OcrResultAdapter adapter = new OcrResultAdapter(this, listOfLines);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }

    }

    private OcrResultWrapper analyzeLines() {
        OcrResultWrapper result = new OcrResultWrapper();
        Line sumaLine = null;
        Line dateLine = null;
        for (Line line : listOfLines) {
            Log.d("line", line.getValue());
            if (line.getValue().toUpperCase().contains("SUMA")
                    || line.getValue().contains("SUNA")
                    || line.getValue().contains("SVNA")
                    || line.getValue().contains("Suma")
                    || !line.getValue().contains("PTU")
                    ) {
                sumaLine = line;
            }
            if (line.getValue().matches(".*2?0?1?[123456789]\\/1?[1234567890]\\/[123]?[1234567890].*")
                    || line.getValue().matches(".*2?0?1?[123456789]-1?[1234567890]-[123]?[1234567890].*")) {
                dateLine = line;
                result.setReceiptDate(dateLine.getValue().trim());
            }
        }
        for (Line line : listOfLines) {
            if (line.getBoundingBox().centerY() + 35 > sumaLine.getBoundingBox().centerY() &&
                    line.getBoundingBox().centerY() - 35 < sumaLine.getBoundingBox().centerY()) {
                if (line.equals(sumaLine)) {
                    continue;
                }
                result.setReceiptTotalValue(line.getValue());
            }
        }

        if (null != sumaLine && result != null) {
            return result;
        }
        return null;
    }
}
