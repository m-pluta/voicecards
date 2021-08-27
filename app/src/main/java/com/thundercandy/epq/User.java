package com.thundercandy.epq;

import android.net.Uri;

public class User {
    /**
     * Constant for when the user entered the app as a guest
     */
    public static final int LOGIN_GUEST = 1;
    /**
     * Constant for when the user entered the app through their google account
     */
    public static final int LOGIN_GOOGLE = 2;
    /**
     * Constant for when the user entered the app through the app's login system
     * This will not be working for some time
     */
    public static final int LOGIN_APP = 3;

    /**
     * Stores the constant about how the user logged in
     */
    public static int LOGIN_TYPE = -1;

    /**
     * May be unnecessary as it can be stored in sharedPreferences
     */
    public static boolean PREFERENCE_REMEMBER_ME = false;

    public static String DisplayName = null;
    public static Uri imageUri = null;

    public static String DEFAULT_DISPLAY_NAME = "Guest";
    public static int DEFAULT_PROFILE_PICTURE = R.mipmap.ic_launcher_round;

    public static void resetUser() {
        LOGIN_TYPE = -1;
        DisplayName = null;
        imageUri = null;
    }



}
