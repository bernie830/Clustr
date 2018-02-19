package com.nothing.hunnaz.clustr;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by hunterbernhardt on 2/16/18.
 */

public class UserPrefs {
    private static final String USER_INFO = "user_information";

    public static void logInUser(String username, Context con){
        SharedPreferences settings = con.getSharedPreferences(USER_INFO, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.putString("username", username);
        editor.commit();
    }

    public static void logOutUser(Context con){
        SharedPreferences settings = con.getSharedPreferences(USER_INFO, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("isLoggedIn", false);
        editor.putString("username", "");
        editor.commit();
    }

    public static boolean isLoggedIn(Context con){
        SharedPreferences settings = con.getSharedPreferences(USER_INFO, 0);
        return settings.getBoolean("isLoggedIn", false);
    }

    public static String currentUser(Context con){
        SharedPreferences settings = con.getSharedPreferences(USER_INFO, 0);
        return settings.getString("username", "");
    }
}
