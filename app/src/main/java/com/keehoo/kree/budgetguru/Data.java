package com.keehoo.kree.budgetguru;

import android.util.Log;

import com.keehoo.kree.budgetguru.activities.ChartFiller;
import com.keehoo.kree.budgetguru.data_models.BudgetEntryModel;
import com.keehoo.kree.budgetguru.rest.RestInterface;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by krzysztof on 09.12.2017.
 */

public class Data {

    private final RestInterface restInterface;
    private final ChartFiller filler;

    public Data(ChartFiller filler) {
        this.filler = filler;
        restInterface = RestInterface.rxRetrofit.create(RestInterface.class);
    }


    public void getAllBudgetEntries() {


        CompositeDisposable mCompositeDisposable = new CompositeDisposable();
        mCompositeDisposable.add(restInterface.getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleResponse(List<BudgetEntryModel> androidList) {


        Log.d("FILLER", "Sendind fill method to activyt");
        Log.d("FILLER", "Sendind fill method to activyt");


        filler.fillChart(androidList);
    }

    private void handleError(Throwable error) {

//
//
//
    }
}
