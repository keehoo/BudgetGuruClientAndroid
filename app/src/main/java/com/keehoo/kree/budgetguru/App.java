package com.keehoo.kree.budgetguru;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by krzysztof on 03.11.2017.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }


}
