package com.neolix.test;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.List;

public class PrivateKeyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        PackageManager pm = context.getPackageManager();
        Log.i(Constants.TAG, "action : " + action);

        if (action.equals(Constants.TEST)) {
            Log.i(Constants.TAG, "Just a test!");
        }

        if (action.equals(Constants.KEY_DOWN)) {
            Log.i(Constants.TAG, "Key down!");

            Intent localIntent = new Intent();
            String className = Configuration.getPrivateClassName(context);
            String packageName = Configuration.getPrivateKeyPackageName(context);

            if (className.equals("") || packageName.equals("")) return;

            boolean appRunBackground = isBackground(context, packageName);
            if (appRunBackground) {
                Intent testIntent = pm.getLaunchIntentForPackage(packageName);
                context.startActivity(testIntent);
                testIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                Log.i(Constants.TAG, "restart app");
            } else {
                localIntent.setComponent(new ComponentName(packageName, className));
                Intent testIntent = pm.getLaunchIntentForPackage(packageName);
                testIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(testIntent);
                Log.i(Constants.TAG, "start app");
            }
        }
    }

    public static boolean isBackground(Context context, String packageName) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)) {
                if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    Log.i("后台", appProcess.processName);
                    return true;
                } else {
                    Log.i("前台", appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }
}