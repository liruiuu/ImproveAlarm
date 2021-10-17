package com.ryze.improvealarm;


import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.List;


public class MyAccessibilityService extends AccessibilityService {
    public static MyAccessibilityService instance;
    String  str="允许";
//锁屏、唤醒相关




    @Override
    public void onCreate() {

        Log.i("qidong", "MyAccessibilityService_onCreate()");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);



    }


    @Override
    protected void onServiceConnected() {
        instance = this;
        Log.i("qidong", "MyAccessibilityService_onServiceConnected");
        //LogUtils.d("onServiceConnected");
    }

    String description;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        AccessibilityNodeInfo rootNodeInfo= getRootInActiveWindow();
        if (rootNodeInfo!=null) {
            List<AccessibilityNodeInfo> logintitleInfo=
                    rootNodeInfo.findAccessibilityNodeInfosByText("允许");
            Log.i("qidong", "登陆界面标题框数量A:" + logintitleInfo.size());

            if (logintitleInfo.size() != 0) {
                Log.i("qidong", "进入IF");
                for (int i = 0; i < logintitleInfo.size(); i++) {
                    Log.i("qidong", "rootNodeInfo:" + i + logintitleInfo.get(i).toString());
                    Log.i("qidong", "child widget------------------" + i + "--------" + logintitleInfo.get(i).getClassName());
                    Log.i("qidong", "showDialog:" + logintitleInfo.get(i).canOpenPopup());
                    Log.i("qidong", "Text：" + logintitleInfo.get(i).getText());
                    Log.i("qidong", "windowId:" + logintitleInfo.get(i).getWindowId());
                    logintitleInfo.get(i).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            }
            List<AccessibilityNodeInfo> logintitleInfo2 =
                    rootNodeInfo.findAccessibilityNodeInfosByText("关闭");
            Log.i("qidong", "登陆界面标题框数量B:" + logintitleInfo2.size());

            if (logintitleInfo2.size() != 0) {
                Log.i("qidong", "进入IF");
                for (int i = 0; i < logintitleInfo2.size(); i++) {
                    Log.i("qidong", "rootNodeInfo:" + i + logintitleInfo2.get(i).toString());
                    Log.i("qidong", "child widget------------------" + i + "--------" + logintitleInfo2.get(i).getClassName());
                    Log.i("qidong", "showDialog:" + logintitleInfo2.get(i).canOpenPopup());
                    Log.i("qidong", "Text：" + logintitleInfo2.get(i).getText());
                    Log.i("qidong", "windowId:" + logintitleInfo2.get(i).getWindowId());
                    logintitleInfo2.get(i).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            }

            Log.i("qidong", "MyAccessibilityService_登陆界面标:已经点击【允许/关闭】" + MyAccessibilityService.this.toString());
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        instance = null;
        return super.onUnbind(intent);
    }

    @Override
    public void onInterrupt() {
        Log.i("qidong", "MyAccessibilityService_onInterrupt");
       // LogUtils.d("onInterrupt");
    }

    @Override
    public void onDestroy() {

        int qidong = Log.i("qidong", "MyAccessibilityService-onDestroy()");

        super.onDestroy();
    }

}
