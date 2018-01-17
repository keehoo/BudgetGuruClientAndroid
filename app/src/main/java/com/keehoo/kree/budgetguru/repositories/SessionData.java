package com.keehoo.kree.budgetguru.repositories;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.keehoo.kree.budgetguru.R;

/**
 * Created by krzysztof on 04.11.2017.
 */

public class SessionData {

    private static final String CURRENT_USER_ID = "CURRENT_USER_ID";
    private static final String CURRENT_USER_NAME = "CURRENT_USER_NAME";
    private static final String CURRENT_USER_LAST_NAME = "CURRENT_USER_LAST_NAME";
    private static final String USER_PHOTO_URL = "user_photo_url";
    public static final String OFFLINE = "OFFLINE";

    private static boolean isLogged;
    private String currentUserLogin;

    private long currentUserId;

    public static boolean isLogged() {
        return isLogged;
    }

    public static void setLogged(boolean logged) {
        isLogged = logged;
    }
    private SharedPreferences preferences;

    public SessionData(Context context) {
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

    public boolean saveCurrentUserLastName(String lastName) {
        return preferences.edit().putString(CURRENT_USER_LAST_NAME, lastName).commit();
    }

    public String getUserLastName() {
        return preferences.getString(CURRENT_USER_LAST_NAME, "");
    }

    public void saveUserPicUrl(Uri photoUrl) {
        preferences.edit().putString(USER_PHOTO_URL, photoUrl.toString()).commit();
    }

    public Uri getPicUrl() {
        String url = preferences.getString(USER_PHOTO_URL, null);
        if (url != null) {
            return Uri.parse(preferences.getString(USER_PHOTO_URL, null));
        }
        else return null;
    }

    public void setOffline(boolean b) {
        preferences.edit().putBoolean(OFFLINE, b).commit();
    }

    public boolean isOffline() {
        return preferences.getBoolean(OFFLINE, false);
    }
}
