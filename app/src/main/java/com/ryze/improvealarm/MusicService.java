package com.ryze.improvealarm;

import android.app.AlarmManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.media.MediaPlayer.create;

/** MusicService没有使用，可以删除* */

public class MusicService extends Service {
    static boolean isplay;//记录当前播放状态
    MediaPlayer player;//MediaPlayer对象
    AlarmManager alarmManager = null;

    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {

        player = MediaPlayer.create(this, R.raw.myheartwillgoon);//创建播放对象
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        long c;
        c = intent.getLongExtra("Calendar", 1);
        Log.i("qidong", "intent.getLongExtra：接收到b的值:" + c);
        //设置闹钟---------------------------------------------------------------------------------------
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar once_alarm = Calendar.getInstance();
        if (c!=0)once_alarm.setTimeInMillis(c);else once_alarm.setTimeInMillis(once_alarm.getTimeInMillis()+5000);
        setClockTime.setOnceClock(0, "once_alarm1", 1, 0, once_alarm, MainActivity.packagename1, MusicService.this, alarmManager);
        Toast.makeText(MusicService.this, "已启动单次闹钟：" + new SimpleDateFormat("HH:mm").format(once_alarm.getTime()), Toast.LENGTH_SHORT).show();
        //播放音乐---------------------------------------------------------------------------------------
        if (!player.isPlaying()) {
            player.setLooping(true);
            player.start();
            isplay = player.isPlaying();
        }
        Log.i("qidong", " MusicService" + MusicService.this.toString());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        player.stop();
        isplay = player.isPlaying();
        player.release();
        Log.i("qidong", "MusicService_onDestroy()");
        super.onDestroy();
    }


}
