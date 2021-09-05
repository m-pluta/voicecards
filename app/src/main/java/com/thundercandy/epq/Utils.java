package com.thundercandy.epq;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import androidx.preference.PreferenceManager;

public class Utils {

    public static String capitalize(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }

        char baseChar = str.charAt(0);
        char updatedChar;
        updatedChar = Character.toUpperCase(baseChar);

        if (baseChar == updatedChar) {
            return str;
        }

        char[] chars = str.toCharArray();
        chars[0] = updatedChar;
        return new String(chars, 0, chars.length);
    }

    public static double getLearntThreshold(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String KEY_learntThreshold = context.getResources().getString(R.string.KEY_learntThreshold);
        int default_learntThreshold = context.getResources().getInteger(R.integer.default_learntThreshold);

        int temp = sharedPreferences.getInt(KEY_learntThreshold, default_learntThreshold);
        return (double) temp / 100;

    }

    public static double getSeenThreshold(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String KEY_seenThreshold = context.getResources().getString(R.string.KEY_seenThreshold);
        int default_seenThreshold = context.getResources().getInteger(R.integer.default_seenThreshold);

        return sharedPreferences.getInt(KEY_seenThreshold, default_seenThreshold);
    }

    public static boolean getAutoCapitalize(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String KEY_auto_capitalize = context.getResources().getString(R.string.KEY_auto_capitalize);
        boolean default_auto_capitalize = context.getResources().getBoolean(R.bool.default_auto_capitalize);

        return sharedPreferences.getBoolean(KEY_auto_capitalize, default_auto_capitalize);
    }

    public static boolean getAutoFullStop(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String KEY_auto_full_stop = context.getResources().getString(R.string.KEY_auto_full_stop);
        boolean default_auto_full_stop = context.getResources().getBoolean(R.bool.default_auto_full_stops);

        return sharedPreferences.getBoolean(KEY_auto_full_stop, default_auto_full_stop);
    }

    public static void removeBottomNavigation(Activity activity) {
        // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {       // Use this if statement for backwards compatibility, When SDK_INT < 19
        // }
        activity.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}
