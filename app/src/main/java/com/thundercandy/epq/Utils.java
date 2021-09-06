package com.thundercandy.epq;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import androidx.preference.PreferenceManager;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Utils {

    public static final long UNIT_SECOND = 1000;
    public static final long UNIT_MINUTE = 60 * UNIT_SECOND;

    public static final long DEFAULT_INTERVAL = UNIT_SECOND;
    public static final long DEFAULT_SMOOTH_INTERVAL = UNIT_SECOND / 10;
    public static final long DEFAULT_EXTRA_SMOOTH_INTERVAL = UNIT_SECOND / 50;

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
        String key = context.getResources().getString(R.string.KEY_learntThreshold);
        int defValue = context.getResources().getInteger(R.integer.default_learntThreshold);

        int temp = sharedPreferences.getInt(key, defValue);
        return (double) temp / 100;

    }

    public static double getSeenThreshold(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String key = context.getResources().getString(R.string.KEY_seenThreshold);
        int defValue = context.getResources().getInteger(R.integer.default_seenThreshold);

        return sharedPreferences.getInt(key, defValue);
    }

    public static boolean getAutoCapitalize(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String key = context.getResources().getString(R.string.KEY_auto_capitalize);
        boolean defValue = context.getResources().getBoolean(R.bool.default_auto_capitalize);

        return sharedPreferences.getBoolean(key, defValue);
    }

    public static boolean getAutoFullStop(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String key = context.getResources().getString(R.string.KEY_auto_full_stop);
        boolean defValue = context.getResources().getBoolean(R.bool.default_auto_full_stops);

        return sharedPreferences.getBoolean(key, defValue);
    }

    public static boolean getSmoothTimerCircle(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String key = context.getResources().getString(R.string.KEY_smooth_timer_circle);
        boolean defValue = context.getResources().getBoolean(R.bool.default_smooth_timer_circle);

        return sharedPreferences.getBoolean(key, defValue);
    }

    public static long getTimerInterval(Context context, long duration) {
        if (getSmoothTimerCircle(context)) {
            if (duration < UNIT_MINUTE * 5) {
                return DEFAULT_EXTRA_SMOOTH_INTERVAL;
            } else {
                return DEFAULT_SMOOTH_INTERVAL;
            }
        } else {
            return DEFAULT_INTERVAL;
        }
    }

    public static long getPomodoroLength(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String key = context.getResources().getString(R.string.KEY_pomodoro_length);
        int defValue = context.getResources().getInteger(R.integer.default_pomodoro_length);

        return (long) sharedPreferences.getInt(key, defValue) * UNIT_MINUTE;
    }

    public static boolean getBreaks(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String key = context.getResources().getString(R.string.KEY_breaks);
        boolean defValue = context.getResources().getBoolean(R.bool.default_breaks);

        return sharedPreferences.getBoolean(key, defValue);
    }

    public static long getBreakLength(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String key = context.getResources().getString(R.string.KEY_break_length);
        int defValue = context.getResources().getInteger(R.integer.default_break_length);

        return (long) sharedPreferences.getInt(key, defValue) * UNIT_MINUTE;
    }

    public static int getLongBreakAfter(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String key = context.getResources().getString(R.string.KEY_long_break_after);
        int defValue = context.getResources().getInteger(R.integer.default_long_break_after);

        return sharedPreferences.getInt(key, defValue);
    }

    public static int getLongBreakLength(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String key = context.getResources().getString(R.string.KEY_long_break_length);
        int defValue = context.getResources().getInteger(R.integer.default_long_break_length);

        return sharedPreferences.getInt(key, defValue);
    }

    public static boolean getBreaksAutoStart(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String key = context.getResources().getString(R.string.KEY_breaks_auto_start);
        boolean defValue = context.getResources().getBoolean(R.bool.default_breaks_auto_start);

        return sharedPreferences.getBoolean(key, defValue);
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

    public static String getDurationBreakdown(long diff) {
        long millis = diff;
        if (millis <= 0) {
            return "00:00";
        }
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        return String.format(Locale.ENGLISH, "%02d:%02d", minutes, seconds);
        //return "${getWithLeadZero(hours)}:${getWithLeadZero(minutes)}:${getWithLeadZero(seconds)}"
    }
}
