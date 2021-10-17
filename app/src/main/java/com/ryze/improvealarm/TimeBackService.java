package com.ryze.improvealarm;

import android.accessibilityservice.AccessibilityService;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;


public class TimeBackService extends Service {
    static boolean isplay;//记录当前播放状态
    MediaPlayer player;//MediaPlayer对象
    static boolean isdelay = false;//记录当前播放状态

//    private Intent intent = new Intent("com.ryze.improvealarm.RECEIVER");


    public TimeBackService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
//        sendBroadcast(intent);
        Log.i("qidong", " TimeBackService_sendBroadcast;" );
        player = MediaPlayer.create(this, R.raw.myheartwillgoon);//创建播放对象
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (!player.isPlaying()) {
            // player.setLooping(true);
            player.start();
            isplay = player.isPlaying();
        }
        Log.i("qidong", " TimeBackService" + TimeBackService.this.toString());
        delayAndStart(20000);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        player.stop();
        isplay = player.isPlaying();
        player.release();
        Log.i("qidong", "TimeBackService_onDestroy()");
        super.onDestroy();
    }

    public void delayAndStart(long delay) {

        TimerTask task = new TimerTask() {
            public void run() {
                if (MyAccessibilityService.instance != null) {
                    MyAccessibilityService.instance.performGlobalAction(AccessibilityService.GESTURE_SWIPE_UP);
                }
                isdelay = true;
                Log.i("qidong", "TimeBackService: GLOBAL_ACTION_BACK");
                stopSelf();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, delay);
        Log.i("qidong", "TimeBackService:delayAndStart（）调用最后一行: " + delay);
    }


}

