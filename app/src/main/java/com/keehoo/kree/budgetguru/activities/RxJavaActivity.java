package com.keehoo.kree.budgetguru.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.keehoo.kree.budgetguru.R;
import com.keehoo.kree.budgetguru.data_models.User;
import com.keehoo.kree.budgetguru.rest.RestInterface;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class RxJavaActivity extends AppCompatActivity {

    public static final String BASE_URL = "http://145.239.89.73/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        CompositeDisposable mCompositeDisposable = new CompositeDisposable();
        RestInterface requestInterface = RestInterface.requestInterface;

        mCompositeDisposable.add(requestInterface.obsListUsers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }


    private void handleResponse(List<User> androidList) {

        for (User u : androidList) {
            Log.d("USER", u.getEmail());
        }

    }

    private void handleError(Throwable error) {

        Toast.makeText(this, "Error "+error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }
}
