package com.ryze.improvealarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;
import java.util.TimeZone;

import static com.ryze.improvealarm.ClockActivity.sd;


public class AlarmReceiver extends BroadcastReceiver {
    private static String mWay;


    @Override
    public void onReceive(Context context, Intent intent) {


//        Intent wall_intent = new Intent(context,WallActivity.class);
//        wall_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(wall_intent);
//        Log.i("qidong", "WallActivity已启动");


        Intent ac_Intent = new Intent(context, MyAccessibilityService.class);
        context.startService(ac_Intent);
        Log.i("qidong", "MyAccessibilityService已启动");

        timeClock timeC = new timeClock();
        Calendar calendar = Calendar.getInstance();
        String str = intent.getStringExtra("calendar");
        try {
            calendar = (Calendar) serializeUtil.deserializeToObject(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int type = intent.getIntExtra("type", 1);
        int start = intent.getIntExtra("start", 1);
        String packagename = intent.getStringExtra("packagename");

        Log.i("qidong", "接收到广播type:" + type);
        Log.i("qidong", "接收到广播start:" + start);
        Log.i("qidong", "接收到广播intent.getAction()" + intent.getAction());
        Log.i("qidong", "接收到广播isweekend()" + isweekend());
        if ("repeat_alarm1".equals(intent.getAction())) {
            timeC.setClockSetflag(true);
            timeC.setClocknum(1);
            timeC.setType(type);
            timeC.setStart(start);
            timeC.setAction("repeat_alarm1");
            timeC.setCalendar(calendar);
            timeC.setPackagename(packagename);
            try {
                str = serializeUtil.serializeToString(timeC);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.i("qidong", "接收到广播+ClockActivity.sd=" + sd);
            if (!isweekend()) {
                Log.i("qidong", "接收到广播+isweekend()工作日上班1");
//                JumpTo(context, type);
/*************************************************************************************/
                Intent ma_Intent = new Intent(context, TimeBackService.class);
                context.startService(ma_Intent);
                Log.i("qidong", "TimeBackService已启动");
                AppUtils.startApp(context, packagename);
                Log.i("qidong", " AppUtils.startApp;已启动"+packagename);
/*************************************************************************************/

                Intent Acintent = new Intent(context, SetTimeService.class);
                Acintent.putExtra("timeClock", str);
                context.startService(Acintent);
//                setClockTime.setOnceRandomClock(1, "repeat_alarm1", type, start, repeat_alarm1,  context, ClockActivity.alarmManager);

            } else {
                Log.i("qidong", "接收到广播+isweekend()周末上班2-ClockSetflag：" + timeC.isClockSetflag());
                Intent Acintent = new Intent(context, SetTimeService.class);
                Acintent.putExtra("timeClock", str);
                context.startService(Acintent);
//                setClockTime.setOnceRandomClock(1, "repeat_alarm1", type, start, repeat_alarm1,  context, ClockActivity.alarmManager);
            }
        } else if ("repeat_alarm2".equals(intent.getAction())) {
            timeC.setClockSetflag(true);
            timeC.setClocknum(2);
            timeC.setType(type);
            timeC.setStart(start);
            timeC.setAction("repeat_alarm2");
            timeC.setCalendar(calendar);
            try {
                str = serializeUtil.serializeToString(timeC);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!isweekend()) {
                Log.i("qidong", "接收到广播+isweekend()工作日下班");
/*************************************************************************************/
                Intent ma_Intent = new Intent(context, TimeBackService.class);
                context.startService(ma_Intent);
                Log.i("qidong", "TimeBackService已启动");
                AppUtils.startApp(context, packagename);
                Log.i("qidong", " AppUtils.startApp;已启动"+packagename);
/*************************************************************************************/
                Intent Acintent = new Intent(context, SetTimeService.class);
                Acintent.putExtra("timeClock", str);
                context.startService(Acintent);
//                setClockTime.setOnceRandomClock(2, "repeat_alarm2", type, start, repeat_alarm2,context, ClockActivity.alarmManager);

            } else {
                Log.i("qidong", "接收到广播+isweekend()周末下班");
                Intent Acintent = new Intent(context, SetTimeService.class);
                Acintent.putExtra("timeClock", str);
                context.startService(Acintent);
//                setClockTime.setOnceRandomClock(2, "repeat_alarm2", type, start, repeat_alarm2, context, ClockActivity.alarmManager);

            }

        } else {
            Log.i("qidong", "接收到单次闹钟广播");
/*************************************************************************************/
            Intent ma_Intent = new Intent(context, TimeBackService.class);
            context.startService(ma_Intent);
            Log.i("qidong", "TimeBackService已启动");
            AppUtils.startApp(context, packagename);
            Log.i("qidong", " AppUtils.startApp;已启动"+packagename);
/*************************************************************************************/

        }




    }


    public static boolean isweekend() {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        Log.i("qidong", "isweekend()" + mWay);
        if ("1".equals(mWay) || "7".equals(mWay)) {
            return true;
        } else {
            return false;
        }

    }


}
