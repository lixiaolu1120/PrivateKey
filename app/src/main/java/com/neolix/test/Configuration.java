package com.neolix.test;

import android.content.Context;
import android.content.SharedPreferences;

import org.joda.time.DateTime;

import static android.content.Context.MODE_PRIVATE;
import static android.content.SharedPreferences.Editor;
import static org.joda.time.DateTime.now;

public class Configuration {

    public static final String PREFERENCES_LOGIN_TIME = "LoginTime";
    private static final String KEY_LAST_LOGIN_TIME_IN_MILLIS = "LastLoginTimeInMillis";
    private static final String KEY_CURRENT_LOGIN_TIME_IN_MILLIS = "CurrentLoginTimeInMillis";

    private static final String DEFAULT_WIFI_PWD = "";

    public static final String PRIVATE_KEY = "private_key";
    public static final String PACKAGE_NAME = "package_name";
    public static final String CLASS_NAME = "class_name";

    private static Editor getEditor(Context applicationContext, String preferencesName) {
        SharedPreferences preferences = applicationContext.getSharedPreferences(preferencesName, MODE_PRIVATE);
        return preferences.edit();
    }

    private static SharedPreferences getPreferences(Context applicationContext, String preferencesName) {
        return applicationContext.getSharedPreferences(preferencesName, MODE_PRIVATE);
    }

    private static long fourAmOfToday() {
        return now().withTime(4, 0, 0, 0).getMillis();
    }

    private static long fourAmOfYesterday() {
        return now().minusDays(1).withTime(4, 0, 0, 0).getMillis();
    }

    private static long zeroAmOfToday() {
        return now().withTime(0, 0, 0, 0).getMillis();
    }

    public static boolean isFirstTimeUseAppToday(Context context) {
        return beforeFourAmOfSpecifiedDay(context, KEY_LAST_LOGIN_TIME_IN_MILLIS);
    }

    public static boolean isCurrentLoginExpired(Context context) {
        return beforeFourAmOfSpecifiedDay(context, KEY_CURRENT_LOGIN_TIME_IN_MILLIS);
    }

    private static boolean beforeFourAmOfSpecifiedDay(Context context, String keyOfLoginTime) {
        SharedPreferences lastUsedTime = getPreferences(context, PREFERENCES_LOGIN_TIME);
        DateTime lastLoginTime = new DateTime(lastUsedTime.getLong(keyOfLoginTime, 0l));
        boolean beforeFourAmOfToday = now().isAfter(zeroAmOfToday()) && now().isBefore(fourAmOfToday());
        long target = beforeFourAmOfToday ? fourAmOfYesterday() : fourAmOfToday();
        return lastLoginTime.isBefore(target);
    }

    public static void setPrivateKey(Context applicationContext, String packageName, String className) {
        Editor editor = getEditor(applicationContext, PRIVATE_KEY);
        editor.putString(PACKAGE_NAME, packageName);
        editor.putString(CLASS_NAME, className);
        editor.commit();
    }

    public static String getPrivateKeyPackageName(Context applicationContext) {
        SharedPreferences preferences = getPreferences(applicationContext, PRIVATE_KEY);
        return preferences.getString(PACKAGE_NAME, DEFAULT_WIFI_PWD);
    }

    public static String getPrivateClassName(Context applicationContext) {
        SharedPreferences preferences = getPreferences(applicationContext, PRIVATE_KEY);
        return preferences.getString(CLASS_NAME, DEFAULT_WIFI_PWD);
    }
}