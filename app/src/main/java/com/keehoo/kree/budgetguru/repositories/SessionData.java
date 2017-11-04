package com.keehoo.kree.budgetguru.repositories;

import android.content.Context;
import android.content.SharedPreferences;

import com.keehoo.kree.budgetguru.R;

/**
 * Created by krzysztof on 04.11.2017.
 */

public class SessionData {

    public static final String CURRENT_USER_ID = "CURRENT_USER_ID";
    public static final String CURRENT_USER_NAME = "CURRENT_USER_NAME";
    private Context context;

    private String currentUserLogin;
    private long currentUserId;
    private SharedPreferences preferences;

    public SessionData(Context context) {
        this.context = context;
         preferences = context.getSharedPreferences(
                context.getString(R.string.session_preferences), Context.MODE_PRIVATE);
    }

    public long getLoggedUserId() {
        return preferences.getLong(CURRENT_USER_ID, -1);
    }

    public boolean saveCurrentLoggedInUser(long id) {
        return preferences.edit().putLong(CURRENT_USER_ID, id).commit();
    }

    public String getLoggedUserName() {
        return preferences.getString(CURRENT_USER_NAME, null);
    }

    public boolean saveCurrentUserLogin(String userName) {
        return preferences.edit().putString(CURRENT_USER_NAME, userName).commit();
    }
}
