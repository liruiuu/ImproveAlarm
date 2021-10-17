package com.ryze.improvealarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;


public class setClockTime {


    public static void setOnceClock(int clocknum, String action, int type, int start, Calendar c, String packagename, Context context, AlarmManager alarmManager) {
        String str = "";
        Calendar c1 = Calendar.getInstance();
        try {
            str = serializeUtil.serializeToString(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String realTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime());
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("type", type);
        intent.putExtra("start", start);
        intent.putExtra("calendar", str);
        intent.putExtra("packagename", packagename);
        intent.setAction(action);
        PendingIntent pi = PendingIntent.getBroadcast(context, clocknum,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);    //创建PendingIntent

        if ((c.getTimeInMillis() + 5000) < System.currentTimeMillis()) {
            c1.setTimeInMillis(c.getTimeInMillis() + 5000 + 24 * 60 * 60 * 1000);
            String realTime1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c1.getTime());
            Log.i("clock", "设置时间要推迟24小时,不然立刻会响");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + (c.getTimeInMillis() - System.currentTimeMillis()) + 5000 + 24 * 60 * 60 * 1000, pi);
                Log.i("qidong", "设置好闹钟setOnceRandomClock-1:" + realTime + "实际响铃时间：" + realTime1);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() + 5000 + 24 * 60 * 60 * 1000, pi);
                Log.i("qidong", "设置好闹钟setOnceRandomClock-1:" + realTime + "实际响铃时间：" + realTime1);
            }
        } else {
            c1.setTimeInMillis(c.getTimeInMillis() + 5000);
            String realTime1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c1.getTime());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + (c.getTimeInMillis() - System.currentTimeMillis()) + 5000, pi);    //设置闹钟，当前时间就唤醒
                Log.i("qidong", "设置好闹钟setOnceRandomClock-1:" + realTime + "实际响铃时间：" + realTime1);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() + 5000, pi);
                Log.i("qidong", "设置好闹钟setOnceRandomClock-1:" + realTime + "实际响铃时间：" + realTime1);
            }
//            Intent ma_Intent = new Intent(context, MyAccessibilityService.class);
//            context.startService(ma_Intent);
        }

    }

    public static void setOnceRandomClock(int clocknum, String action, int type, int start, Calendar c, String packagename, Context context, AlarmManager alarmManager) {
        String str = "";
        Calendar c1 = Calendar.getInstance();
        try {
            str = serializeUtil.serializeToString(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Random r = new Random();
        int ran1 = r.nextInt(20);
        long b = (ran1 + 1) * 60 * 1000;
        String realTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime());


        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("type", type);
        intent.putExtra("start", start);
        intent.putExtra("calendar", str);
        intent.putExtra("packagename", packagename);
        intent.setAction(action);
        PendingIntent pi = PendingIntent.getBroadcast(context, clocknum,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);    //创建PendingIntent
        if (clocknum == 1) {
            if ((c.getTimeInMillis() + 5000) < System.currentTimeMillis()) {
                c1.setTimeInMillis(c.getTimeInMillis() + 5000 - b + 24 * 60 * 60 * 1000);
                String realTime1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c1.getTime());

                Log.i("clock", "设置时间要推迟24小时,不然立刻会响");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + (c.getTimeInMillis() + 5000 - System.currentTimeMillis()) + 24 * 60 * 60 * 1000 - b, pi);
                    Log.i("qidong", "设置好闹钟setOnceRandomClock-1:" + realTime + "实际响铃时间：" + realTime1);
                } else {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() + 5000 + 24 * 60 * 60 * 1000 - b, pi);
                    Log.i("qidong", "设置好闹钟setOnceRandomClock-2:" + realTime + "实际响铃时间：" + realTime1);
                }
            } else {
                if ((c.getTimeInMillis() + 5000 - b) < System.currentTimeMillis()) {//防止减掉随机时间b少于当前时间

                    c1.setTimeInMillis(c.getTimeInMillis() + 5000 - (c.getTimeInMillis() + 5000 - System.currentTimeMillis()) / 2);
                    String realTime1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c1.getTime());

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + (c.getTimeInMillis() + 5000 - System.currentTimeMillis()) - (c.getTimeInMillis() + 5000 - System.currentTimeMillis()) / 2, pi);
                        Log.i("qidong", "设置好闹钟setOnceRandomClock-3:" + realTime + "实际响铃时间：" + realTime1);
                    } else {
                        alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() - (c.getTimeInMillis() + 5000 - System.currentTimeMillis()) / 2, pi);
                        Log.i("qidong", "设置好闹钟setOnceRandomClock-4:" + realTime + "实际响铃时间：" + realTime1);
                    }
                } else {
                    c1.setTimeInMillis(c.getTimeInMillis() + 5000 - b);
                    String realTime1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c1.getTime());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + (c.getTimeInMillis() + 5000 - System.currentTimeMillis()) - b, pi);    //设置闹钟，当前时间就唤醒
                        Log.i("qidong", "设置好闹钟setOnceRandomClock-5:" + realTime + "实际响铃时间：" + realTime1);
                    } else {
                        alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() + 5000 - b, pi);
                        Log.i("qidong", "设置好闹钟setOnceRandomClock-6:" + realTime + "实际响铃时间：" + realTime1);
                    }
                }
            }


        } else {

            if ((c.getTimeInMillis() + 5000) < System.currentTimeMillis()) {
                c1.setTimeInMillis(c.getTimeInMillis() + 5000 + b + 24 * 60 * 60 * 1000);
                String realTime1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c1.getTime());
                Log.i("clock", "设置时间要推迟24小时,不然立刻会响");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + (c.getTimeInMillis() + 5000 - System.currentTimeMillis()) + 24 * 60 * 60 * 1000 + b, pi);

                    Log.i("qidong", "设置好闹钟setOnceRandomClock-7:" + realTime + "实际响铃时间：" + realTime1);
                } else {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() + 5000 + 24 * 60 * 60 * 1000 + b, pi);

                    Log.i("qidong", "设置好闹钟setOnceRandomClock-8:" + realTime + "实际响铃时间：" + realTime1);
                }
            } else {
                c1.setTimeInMillis(c.getTimeInMillis() + 5000 + b);
                String realTime1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c1.getTime());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + (c.getTimeInMillis() + 5000 - System.currentTimeMillis()) + b, pi);    //设置闹钟，当前时间就唤醒

                    Log.i("qidong", "设置好闹钟setOnceRandomClock-9:" + realTime + "实际响铃时间：" + realTime1);
                } else {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() + 5000 + b, pi);

                    Log.i("qidong", "设置好闹钟setOnceRandomClock-10:" + realTime + "实际响铃时间：" + realTime1);
                }

            }


        }
//        Intent ma_Intent = new Intent(context, MyAccessibilityService.class);
//        context.startService(ma_Intent);
    }

    public static void setOnceRandomClock_beta(int clocknum, String action, int type, int start, Calendar c, String packagename, Context context, AlarmManager alarmManager) {
        String str = "";
        Calendar c1 = Calendar.getInstance();
        c.setTimeInMillis(c.getTimeInMillis() + 24 * 60 * 60 * 1000);
        try {
            str = serializeUtil.serializeToString(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Random r = new Random();
        int ran1 = r.nextInt(20);
        long b = (ran1 + 1) * 60 * 1000;
        String realTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime());//HH:mm


        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("type", type);
        intent.putExtra("start", start);
        intent.putExtra("calendar", str);
        intent.putExtra("packagename", packagename);
        intent.setAction(action);
        PendingIntent pi = PendingIntent.getBroadcast(context, clocknum,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);    //创建PendingIntent
        if (clocknum == 1) {

            c1.setTimeInMillis(c.getTimeInMillis() - b);
            String realTime1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c1.getTime());//"HH:mm

            Log.i("clock", "第二天的闹钟，设置时间要推迟24小时");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + c.getTimeInMillis() - b - System.currentTimeMillis(), pi);
                Log.i("qidong", "设置好闹钟setOnceRandomClock_beta-1:" + realTime + "实际响铃时间：" + realTime1);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() - b, pi);
                Log.i("qidong", "设置好闹钟setOnceRandomClock_beta-2:" + realTime + "实际响铃时间：" + realTime1);
            }


        } else {
            c1.setTimeInMillis(c.getTimeInMillis() + b);
            String realTime1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c1.getTime());//"HH:mm

            Log.i("clock", "第二天的闹钟，设置时间要推迟24小时");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + c.getTimeInMillis() + b - System.currentTimeMillis(), pi);
                Log.i("qidong", "设置好闹钟setOnceRandomCloc_betak-3:" + realTime + "实际响铃时间：" + realTime1);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() + b, pi);
                Log.i("qidong", "设置好闹钟setOnceRandomClock_beta-4:" + realTime + "实际响铃时间：" + realTime1);
            }


        }
//        Intent ma_Intent = new Intent(context, MyAccessibilityService.class);
//        context.startService(ma_Intent);
    }

    /**
     * 该方法没有使用
     *
     */
    public static void setRepeateClock(int clocknum, String action, int type, int start, Calendar c, String packagename, Context context, AlarmManager alarmManager) {

        String str = "";
        try {
            str = serializeUtil.serializeToString(c);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String realTime = new SimpleDateFormat("HH:mm").format(c.getTime());
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("type", type);
        intent.putExtra("start", start);
        intent.putExtra("calendar", str);
        intent.putExtra("packagename", packagename);
        intent.setAction(action);
        PendingIntent pi = PendingIntent.getBroadcast(context, clocknum,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);    //创建PendingIntent
        if (c.getTimeInMillis() < System.currentTimeMillis()) {
            Log.i("clock", "设置时间要推迟24小时,不然立刻会响");
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + (c.getTimeInMillis() - System.currentTimeMillis()) + 24 * 60 * 60 * 1000, AlarmManager.INTERVAL_DAY, pi);
        } else {
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + (c.getTimeInMillis() - System.currentTimeMillis()), AlarmManager.INTERVAL_DAY, pi);   //设置闹钟，当前时间就唤醒
            Log.i("qidong", "设置好闹钟:" + realTime);
        }

//        Intent ma_Intent = new Intent(context, MyAccessibilityService.class);
//        context.startService(ma_Intent);

    }
}
