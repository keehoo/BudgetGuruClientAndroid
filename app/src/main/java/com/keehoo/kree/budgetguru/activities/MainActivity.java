package com.keehoo.kree.budgetguru.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.keehoo.kree.budgetguru.R;
import com.keehoo.kree.budgetguru.repositories.SessionData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final int RC_OCR_CAPTURE = 9003;

    @BindView(R.id.current_user)
    TextView currentUserTextView;

    @BindView(R.id.create_new_user)
    Button createUserButton;
    @BindView(R.id.create_budget_entry)
    Button createBudgetEntryButton;
    @BindView(R.id.login)
    Button loginButton;
    @BindView(R.id.ocr)
    Button ocrButton;
    @BindView(R.id.local_database)
    Button localDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setCurrentUserView();

    }

    private void setCurrentUserView() {
        Long currentUserId = new SessionData(this).getLoggedUserId();
        currentUserTextView.setText("Current User Id: "+currentUserId);
    }

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
    }
}
