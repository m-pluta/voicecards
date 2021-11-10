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
     * Stores the constant about how the user logged in
     */
    private static int LOGIN_TYPE = -1;

    public static String DisplayName = null;
    public static Uri imageUri = null;

    public static String DEFAULT_DISPLAY_NAME = "Guest";
    public static int DEFAULT_PROFILE_PICTURE = R.mipmap.ic_launcher_round;

    public static boolean autoGoogleSignIn = true;

    public static void resetUser() {
        LOGIN_TYPE = -1;
        DisplayName = null;
        imageUri = null;
    }

    public static void setGuestUser() {
        LOGIN_TYPE = LOGIN_GUEST;
    }

    public static void setGoogleUser() {
        LOGIN_TYPE = LOGIN_GOOGLE;
    }

    public static int getLoginType() {
        return LOGIN_TYPE;
    }
}
