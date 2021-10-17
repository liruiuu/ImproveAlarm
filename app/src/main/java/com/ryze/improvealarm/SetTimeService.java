package com.ryze.improvealarm;

import android.app.AlarmManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class SetTimeService extends Service {
    private  timeClock timeClock;
    public static AlarmManager alarmManager = null;
    Calendar calendar = Calendar.getInstance();
    public SetTimeService() {
    }
    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onCreate() {

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String str=intent.getStringExtra("timeClock");
        try {
            timeClock=(timeClock) serializeUtil.deserializeToObject(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String realTime = new SimpleDateFormat("HH:mm").format(timeClock.getCalendar().getTime());

        Log.i("qidong", "接收到的timeClock.getCalendar().getTime():" + realTime);
        setReaptClock(timeClock);
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {

        Log.i("qidong", "SetTimeService_onDestroy()");
        super.onDestroy();
    }
    public void setReaptClock(timeClock timeClock){
        Log.i("qidong", "重复设置重复闹钟：timeClock.getClocknum()： "+timeClock.getClocknum());
        Log.i("qidong", "重复设置重复闹钟：timeClock.getClockSetflag()： "+timeClock.isClockSetflag());
        Log.i("qidong", "重复设置重复闹钟：imeClock.setAction();： "+timeClock.getAction());
        Log.i("qidong", "重复设置重复闹钟：timeClock.getCalendar()： "+timeClock.getCalendar().toString());
        if (timeClock.isClockSetflag()){
            calendar.set(Calendar.HOUR_OF_DAY, timeClock.getCalendar().get(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, timeClock.getCalendar().get(Calendar.MINUTE));
            calendar.set(Calendar.SECOND, timeClock.getCalendar().get(Calendar.SECOND));
            setClockTime.setOnceRandomClock_beta(timeClock.getClocknum(), timeClock.getAction(), timeClock.getType(), timeClock.getStart(), calendar,  timeClock.getPackagename() ,SetTimeService.this, alarmManager);
            Log.i("qidong", "重复设置重复闹钟：");
        }

    }

}
