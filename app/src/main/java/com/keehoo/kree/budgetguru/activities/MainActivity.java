package com.keehoo.kree.budgetguru.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.keehoo.kree.budgetguru.Data;
import com.keehoo.kree.budgetguru.R;
import com.keehoo.kree.budgetguru.data_models.BudgetEntryModel;
import com.keehoo.kree.budgetguru.repositories.SessionData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.keehoo.kree.budgetguru.activities.OcrResultAnalysisActivity.OCR_RESULTS;

public class MainActivity extends AppCompatActivity implements ChartFiller {

    private static final int RC_OCR_CAPTURE = 9003;
    public static final String FULL_USER_NAME = "full_user_name";
    public static final String LOGGED_IN = "logged_in";
    public static final String PIC_URL = "pic_url";

    @BindView(R.id.imageButton5)
    ImageButton ocrButton;

    @BindView(R.id.imageButton7)
    ImageButton statsButton;

    private PieChart mChart;
    private List<BudgetEntryModel> budgetEntryModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (!SessionData.isLogged()) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        setUserNameField();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tryToFillChart();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        SessionData sessionData = new SessionData(this);
        Bundle bundle = new Bundle();
        bundle.putString(FULL_USER_NAME, sessionData.getLoggedUserName());
        bundle.putBoolean(LOGGED_IN, SessionData.isLogged());
        if (null != sessionData.getPicUrl()) {
            bundle.putString(PIC_URL, sessionData.getPicUrl().toString());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // TODO: restore field values;
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void setUserNameField() {
        SessionData sessionData = new SessionData(this);
        TextView userName = (TextView) findViewById(R.id.userName_id);
        userName.setText(new StringBuilder().append(sessionData.getLoggedUserName()).toString());
        ImageView userImage = (ImageView) findViewById(R.id.imageView);
        if (null != sessionData.getPicUrl())
            Picasso.with(this).load(sessionData.getPicUrl()).into(userImage);
    }

    private void setupMainScreenChart() {
        mChart = (PieChart) findViewById(R.id.chart1);
        mChart.setBackgroundColor(Color.TRANSPARENT);

        //  moveOffScreen();

        // mChart.setLayoutParams(new WindowManager.LayoutParams(200, 200, 200, 200, 0, 0, 0));

        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);

        // mChart.setCenterTextTypeface(mTfLight);
        // mChart.setCenterText(generateCenterSpannableText());

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.TRANSPARENT);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(255);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        mChart.setRotationEnabled(false);
        mChart.setHighlightPerTapEnabled(true);

        mChart.setMaxAngle(180f); // HALF CHART
        mChart.setRotationAngle(180f);
        mChart.setCenterTextOffset(0, -20);

        setMainScreenChartData(3, 100);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        mChart.setEntryLabelColor(Color.WHITE);
        //mChart.setEntryLabelTypeface(mTfRegular);
        mChart.setEntryLabelTextSize(12f);
    }

    private void tryToFillChart() {
        new Data(this).getAllBudgetEntries();
    }

    private void setMainScreenChartData(int count, float range) {


        ArrayList<PieEntry> values = new ArrayList<PieEntry>();

        for (BudgetEntryModel b : budgetEntryModels) {
            values.add(new PieEntry((float) b.getValue(), b.getCategory()));
        }
        PieDataSet dataSet = new PieDataSet(values, "Biggest expenses");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setSelectionShift(0f);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);
        mChart.invalidate();
    }

    @Override
    public void fillChart(List<BudgetEntryModel> listOfEntries) {
        budgetEntryModels = listOfEntries;
        setupMainScreenChart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_OCR_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    String result = data.getStringExtra("ocred_text");
                    Log.d(this.getPackageCodePath(), result);
                    Intent intentWithResults = new Intent(this, OcrResultAnalysisActivity.class);
                    intentWithResults.putExtra(OCR_RESULTS, result);
                    startActivity(intentWithResults);
                } else {
                    Toast.makeText(this, "text is null", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Error while reading receipt values", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @OnClick(R.id.imageButton5)
    void startOcrActivity() {
        Intent intent = new Intent(this, OcrActivity.class);
        intent.putExtra(OcrActivity.AutoFocus, true);
        intent.putExtra(OcrActivity.UseFlash, false);
        startActivityForResult(intent, RC_OCR_CAPTURE);
    }

    @OnClick(R.id.imageButton7)
    void startStatsActivity() {
        Intent intent = new Intent(this, StatsActivity.class);
        startActivity(intent);
    }
}
/*
    @OnClick(R.id.create_new_user)
     void startNewUserActivity() {
        startActivity(new Intent(MainActivity.this, UserCreatorActivity.class));
    }

    @OnClick(R.id.create_budget_entry)
     void startNewBudgetEntryActivity() {
        startActivity(new Intent(MainActivity.this, BudgetEntryActivity.class));
    }

    @OnClick(R.id.login)
    void startActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.local_database)
    void startLocalDbActivity() {
        Intent intent = new Intent(MainActivity.this, LocalDatabaseActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.ocr)
    void startOcrActivity() {
        Intent intent = new Intent(this, OcrActivity.class);
        intent.putExtra(OcrActivity.AutoFocus, true);
        intent.putExtra(OcrActivity.UseFlash, false);

        startActivityForResult(intent, RC_OCR_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RC_OCR_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    String result = data.getStringExtra("ocred_text");
                    Log.d(this.getPackageCodePath(), result);
                    Intent intentWithResults = new Intent(this, OcrResultAnalysisActivity.class);
                    intentWithResults.putExtra("ocr_results", result);
                    startActivity(intentWithResults);
                } else {
                    Toast.makeText(this, "text is null", Toast.LENGTH_LONG).show();
                }
            } else {

            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }*/

