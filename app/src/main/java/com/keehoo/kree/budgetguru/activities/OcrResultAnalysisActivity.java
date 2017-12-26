package com.keehoo.kree.budgetguru.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
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

    public static final String CURRENT_VALUE = "Current_value";
    public static final String CURRENT_INDEX = "Current_index";
    public static final int VAL_EDIT_REQUEST_CODE = 9976;
    public static final String OCR_RESULTS = "ocr_results";
    @BindView(R.id.seeFullReportButtonId)
    Button seeFullReportButton;

    private List<Line> listOfLines;
    private Line sumaLine;
    private OcrResultWrapper ocrResult;
    private RecyclerView recyclerView;
    private OcrResultAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr_result_analysis);
        ButterKnife.bind(this);
        initializeRecyclerViewWithValuesFromIntent();
    }

    private void initializeRecyclerViewWithValuesFromIntent() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        Intent intent = getIntent();
        String ocrResultList = intent.getExtras().getString(OCR_RESULTS);
        Type listType = new TypeToken<ArrayList<Line>>() {
        }.getType();
        Gson gson = new Gson();
        listOfLines = gson.fromJson(ocrResultList, listType);
        List<OcrResultWrapper> listToBePassedToAdapter = new ArrayList<>();
        listToBePassedToAdapter.add(analyzeLines());
        setTheList(listToBePassedToAdapter);
    }

    private void setTheList(List<OcrResultWrapper> listToBePassedToAdapter) {
        if (!listToBePassedToAdapter.isEmpty()) {
            adapter = new OcrResultAdapter(this, listToBePassedToAdapter);
            adapter.setItemClickListener((currentObject, currentPosition) -> startEditValueActivity(currentObject, currentPosition));
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }
    }

    private void startEditValueActivity(OcrResultWrapper currentObject, int currentPosition) {
        Intent intent = new Intent(this, ValueEditActivity.class);
        intent.putExtra(CURRENT_VALUE, new Gson().toJson(currentObject));
        intent.putExtra(CURRENT_INDEX, currentPosition);
        startActivityForResult(intent, VAL_EDIT_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == VAL_EDIT_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Toast.makeText(this, "Received value "+data.getStringExtra(CURRENT_VALUE), Toast.LENGTH_SHORT).show();
                    String stringExtra = data.getStringExtra(CURRENT_VALUE);
                    OcrResultWrapper wrapper = new Gson().fromJson(stringExtra, OcrResultWrapper.class);
                    List<OcrResultWrapper> list = new ArrayList<>();
                    list.add(wrapper);
                    adapter.notifuListChanged(list);
                    recyclerView.setAdapter(adapter);
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private OcrResultWrapper analyzeLines() {
        //TODO: this has to be in different class and written better
        OcrResultWrapper result = new OcrResultWrapper();
        try {

            for (Line line : listOfLines) {
                Log.d("LINE", line.getValue());
                if (line.getValue().toUpperCase().contains("suma".toUpperCase())
                        && !line.getValue().toUpperCase().contains("PTU")
                        && !line.getValue().toUpperCase().contains("NIP")
                        ) {
                    Log.d("Adding: ", line.getValue());
                    sumaLine = line;
                }
                if (line.getValue().matches(OcrResultAdapter.DATE_REGEX_WITH_DASH1) && !line.getValue().contains("NIP")) {
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
        } catch (Throwable t) {
            Log.e("Reading Error", "Error while reading receipt values :"+t.getLocalizedMessage());
        }
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
