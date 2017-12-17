package com.keehoo.kree.budgetguru.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.vision.text.Line;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.keehoo.kree.budgetguru.R;
import com.keehoo.kree.budgetguru.activities.adapters.OcrResultAdapter;
import com.keehoo.kree.budgetguru.activities.adapters.OcrResultWrapper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OcrResultAnalysisActivity extends AppCompatActivity {

    @BindView(R.id.seeFullReportButtonId)
    Button seeFullReportButton;

    private List<Line> listOfLines;
    private Line sumaLine;
    private OcrResultWrapper ocrResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr_result_analysis);
        ButterKnife.bind(this);
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
            OcrResultAdapter adapter = new OcrResultAdapter(this, listToBePassedToAdapter);
            adapter.setItemClickListener((currentObject, currentPosition) -> startEditValueActivity(currentObject, currentPosition));
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            recyclerView.setAdapter(adapter);
        }
    }

    private void startEditValueActivity(String currentObject, int currentPosition) {
        Toast.makeText(this, "Starting activity to edit the value "+currentObject, Toast.LENGTH_SHORT).show();
    }

    private OcrResultWrapper analyzeLines() {
        OcrResultWrapper result = new OcrResultWrapper();
        for (Line line : listOfLines) {
            Log.d("LINE", line.getValue());
            if (line.getValue().toUpperCase().contains("suma".toUpperCase())
                    && !line.getValue().toUpperCase().contains("PTU")
                    ) {
                //result.setReceiptTotalValue(line.getValue());
                //TODO: we've got here the suma line, we need to find the total value to the right of this:
                Log.d("Adding: ", line.getValue());
                sumaLine = line;
            }
            if (line.getValue().matches(OcrResultAdapter.DATE_REGEX_WITH_DASH1)) {
                result.setReceiptDate(line.getValue());
                Log.d("Adding: ", line.getValue());
            }

            if (line.getValue().matches(OcrResultAdapter.TIME_REGEX_WITH_DASH1)) {
                result.setReceiptTime(line.getValue());
                Log.d("Adding: ", line.getValue());
            }
        }
        for (Line line : listOfLines) {
            if (line.equals(sumaLine)) {
                continue;
            }
            if (line.getBoundingBox().centerY() + 20 > sumaLine.getBoundingBox().centerY() &&
                    line.getBoundingBox().centerY() - 20 < sumaLine.getBoundingBox().centerY()) {
                result.setReceiptTotalValue(line.getValue());
            }
        }
        ocrResult = result;
        return result;
    }

    @OnClick(R.id.seeFullReportButtonId)
    void seeFullReport() {

        Intent intent = new Intent(this, FullReportActivity.class);
        intent.putExtra("sum", ocrResult.getReceiptTotalValue());
        intent.putExtra("date", ocrResult.getReceiptDate());
        intent.putExtra("time", ocrResult.getReceiptTime());
        startActivity(intent);
    }
}
