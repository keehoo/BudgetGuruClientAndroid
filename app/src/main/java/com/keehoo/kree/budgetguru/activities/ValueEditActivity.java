package com.keehoo.kree.budgetguru.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.gson.Gson;
import com.keehoo.kree.budgetguru.R;
import com.keehoo.kree.budgetguru.activities.adapters.OcrResultWrapper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ValueEditActivity extends AppCompatActivity {

    private String valueToBeEdited;

    @BindView(R.id.val)
    EditText valToBeEditedEditText;
    @BindView(R.id.editText)
    EditText date;
    @BindView(R.id.editText2)
    EditText time;

    @BindView(R.id.accept)
    Button acceptButton;
    private OcrResultWrapper ocrResultWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_edit);
        ButterKnife.bind(this);
        setTheValueToBeEdited();
    }

    private void setTheValueToBeEdited() {

        String stringExtra = getIntent().getStringExtra(OcrResultAnalysisActivity.CURRENT_VALUE);
        ocrResultWrapper = new Gson().fromJson(stringExtra, OcrResultWrapper.class);

        valToBeEditedEditText.setText(ocrResultWrapper.getReceiptTotalValue());
        date.setText(ocrResultWrapper.getReceiptDate());
        time.setText(ocrResultWrapper.getReceiptTime());


    }

    @OnClick(R.id.accept)
    void click() {
        Intent intent = new Intent(this, OcrResultAnalysisActivity.class);
        //intent.putExtra(OcrResultAnalysisActivity.CURRENT_VALUE, valToBeEditedEditText.getText().toString());
        //intent.putExtra(OcrResultAnalysisActivity.CURRENT_VALUE, date.getText().toString());
        //intent.putExtra(OcrResultAnalysisActivity.CURRENT_VALUE, time.getText().toString());

        ocrResultWrapper.setReceiptTotalValue(valToBeEditedEditText.getText().toString());
        ocrResultWrapper.setReceiptDate(date.getText().toString());
        ocrResultWrapper.setReceiptTime(time.getText().toString());

        if (null!=ocrResultWrapper) {
            intent.putExtra(OcrResultAnalysisActivity.CURRENT_VALUE, new Gson().toJson(ocrResultWrapper));
            setResult(CommonStatusCodes.SUCCESS, intent);
            finish();
        }
        else {
            setResult(CommonStatusCodes.ERROR);
            finish();
        }
    }
}
