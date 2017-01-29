package com.bsunk.zumperproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Bharat on 1/28/2017.
 */

public class Utility {

    public static String getPosition(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(context.getString(R.string.shared_prefs_key),
                context.getString(R.string.shared_prefs_default));
    }
    public static void savePosition(Context context, String position) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(context.getString(R.string.shared_prefs_key), position );
        editor.apply();
    }

}
