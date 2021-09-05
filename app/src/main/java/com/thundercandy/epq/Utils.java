package com.thundercandy.epq;

import android.content.Context;
import android.content.SharedPreferences;

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
        String KEY_learntThreshold = context.getResources().getString(R.string.KEY_seenThreshold);
        int default_learntThreshold = context.getResources().getInteger(R.integer.default_seenThreshold);

        return sharedPreferences.getInt(KEY_learntThreshold, default_learntThreshold);
    }
}
