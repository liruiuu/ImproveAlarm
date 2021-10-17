package com.ryze.improvealarm;


import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;

import java.util.List;

/**
 * 开启无障碍服务帮助类
 * Created by mazaiting on 2017/8/18.
 */
public class OpenAccessibilitySettingHelper {

    /**
     * 跳转到无障碍服务设置页面
     * @param context 设备上下文
     */
    public static void jumpToSettingPage(Context context){
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 判断是否有辅助功能权限
     * @return true 已开启
     *          false 未开启
     */
    public static boolean isAccessibilitySettingsOn(Context context,String className){
        if (context == null){
            return false;
        }
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServices =
                activityManager.getRunningServices(100);// 获取正在运行的服务列表
        if (runningServices.size()<0){
            return false;
        }
        for (int i=0;i<runningServices.size();i++){
            ComponentName service = runningServices.get(i).service;
            if (service.getClassName().equals(className)){
                return true;
            }
        }
        return false;
    }

    // To check if service is enabled
    public static boolean isAccessibilitySettingsOn_beta(Context mContext) {
        int accessibilityEnabled = 0;
        final String service = mContext.getPackageName() + "/" +MyAccessibilityService.class.getCanonicalName();
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    mContext.getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
//            Log.v(TAG, "accessibilityEnabled = " + accessibilityEnabled);
        } catch (Settings.SettingNotFoundException e) {
//            Log.e(TAG, "Error finding setting, default accessibility to not found: "
//                    + e.getMessage());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {
//            Log.v(TAG, "***ACCESSIBILITY IS ENABLED*** -----------------");
            String settingValue = Settings.Secure.getString(
                    mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessibilityService = mStringColonSplitter.next();

//                    Log.v(TAG, "-------------- > accessibilityService :: " + accessibilityService + " " + service);
                    if (accessibilityService.equalsIgnoreCase(service)) {
//                        Log.v(TAG, "We've found the correct setting - accessibility is switched on!");
                        return true;
                    }
                }
            }
        } else {
//            Log.v(TAG, "***ACCESSIBILITY IS DISABLED***");
        }

        return false;
    }

}
