package com.fakevideocall.fakechat.prank.fake.call.app.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class  SharePreferenceUtils {

    private static final String FIND_PHONE_NAME = "FindPhone";
    private static final String KEY_TUTORIAL = "isTutorial";
    private static final String KEY_SOUND = "KEY_SOUND";

    private static final String COUNTER_KEY = "counter_value";

    private static volatile SharePreferenceUtils instance;

    private SharedPreferences sharePreference;

    public SharePreferenceUtils(Context context) {
        sharePreference = context.getSharedPreferences(FIND_PHONE_NAME, Context.MODE_PRIVATE);
    }

    public static SharePreferenceUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (SharePreferenceUtils.class) {
                if (instance == null) {
                    instance = new SharePreferenceUtils(context);
                }
            }
        }
        return instance;
    }

    public static boolean isOrganic(Context context) {
        SharedPreferences pref = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        return pref.getBoolean("organic", true);
    }


    public static void setOrganicValue(Context context, boolean value) {
        SharedPreferences pre = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();
        editor.putBoolean("organic", value);
        editor.apply();
    }

    public int getCurrentValue() {
        return sharePreference.getInt(COUNTER_KEY, 0);
    }

    public void incrementCounter() {
        sharePreference.edit()
                .putInt(COUNTER_KEY, getCurrentValue() + 1)
                .apply();
    }

//    public static boolean isFullAds(Context context) {
//        SharedPreferences pref = context.getSharedPreferences("data", Context.MODE_PRIVATE);
//        return pref.getBoolean("full_ads", false);
//    }+






































































































































































































































//
//
//    public static void setFullAds(Context context, boolean value) {
//        SharedPreferences pre = context.getSharedPreferences("data", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = pre.edit();
//        editor.putBoolean("full_ads", value);
//        editor.apply();
//    }

    public static boolean isFirstHome(Context context) {
        SharedPreferences pref = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        return pref.getBoolean("firstHome", true);
    }

    public static void setFirstHome(Context context, boolean value) {
        SharedPreferences pre = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();
        editor.putBoolean("firstHome", value);
        editor.apply();
    }
}
