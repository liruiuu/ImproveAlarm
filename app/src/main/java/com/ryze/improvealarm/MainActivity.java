package com.ryze.improvealarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.util.Log;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends Activity {
    static String Appname1;
    static String packagename1;
    static String Appname2;
    static String packagename2;
    static String Appname3;
    static String packagename3;
    static String Appname4;
    static String packagename4;
    long b;
    boolean flag = false;//判断试用是否超期标志
    boolean netflag = false;//判断试用是否超联网
//    TextView title_tv;
    TextView settimeTv;
    TextView set_auth;
    TextView mailtv;
    TextView QQTv;
    TextView weiboTv;
    TextView wechatTv;
    TextView douyin_tv;
    TextView musicService, set_tv;
    Calendar expcalendar = Calendar.getInstance();

//    private MsgReceiver msgReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        title_tv = findViewById(R.id.title_tv);
        settimeTv = findViewById(R.id.settime_tv);
        QQTv = findViewById(R.id.qq_tv);
        weiboTv = findViewById(R.id.weibo_tv);
        wechatTv = findViewById(R.id.wechat_tv);
        douyin_tv = findViewById(R.id.douyin_tv);
        set_tv = findViewById(R.id.set_tv);
        set_auth = findViewById(R.id.set_auth);
        mailtv = findViewById(R.id.mailtv);
        musicService = findViewById(R.id.music_service);
        SharedPreferences sp = getSharedPreferences("AppNameAndPackage", Context.MODE_PRIVATE);
        Appname1 = sp.getString("Appname1", "彩云");
        Appname2 = sp.getString("Appname2", "钉钉");
        Appname3 = sp.getString("Appname3", "微信");
        Appname4 = sp.getString("Appname4", "抖音");

//        msgReceiver = new MsgReceiver();
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("com.ryze.improvealarm.RECEIVER");
//        registerReceiver(msgReceiver, intentFilter);



    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("qidong", "Appname1 :" + Appname1);
        Log.i("qidong", "packagename1:" + packagename1);

        SharedPreferences sp = getSharedPreferences("AppNameAndPackage", Context.MODE_PRIVATE);

        Appname1 = sp.getString("Appname1", "彩云");
        Appname2 = sp.getString("Appname2", "钉钉");
        Appname3 = sp.getString("Appname3", "微信");
        Appname4 = sp.getString("Appname4", "抖音");

        packagename1 = sp.getString("packagename1", "com.shinemo.jtcy.gov");
        packagename2 = sp.getString("packagename2", "com.alibaba.android.rimet");
        packagename3 = sp.getString("packagename3", "com.tencent.mm");
        packagename4 = sp.getString("packagename4", "com.ss.android.ugc.aweme");

        QQTv.setText("启动" + Appname1 + "闹钟");
        weiboTv.setText("启动" + Appname2 + "闹钟");
        wechatTv.setText("启动" + Appname3 + "闹钟");
        douyin_tv.setText("启动" + Appname4 + "闹钟");
        musicService.setText("启动测试" + Appname1 + "闹钟");

        new Thread(new Runnable() {
            @Override
            public void run() {
                getNetTime();
            }
        }).start();



        set_auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });
        QQTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtils.checkPackInfo(MainActivity.this, packagename1)) {
                    Intent intent = new Intent(MainActivity.this, ClockActivity.class);
                    intent.putExtra("type", 1);
                    intent.putExtra("start", 1);
                    intent.putExtra("appname", Appname1);
                    intent.putExtra("packagename", packagename1);
                    Log.i("qidong", "跳转ClockActivity前-八桂str:");
                    if (!flag && !netflag) {
                        startActivity(intent);
                    } else if (netflag)
                        Toast.makeText(MainActivity.this, "请检查网络是否连接", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(MainActivity.this, "试用期已过，请联系捐赠，QQ及邮箱：770744617@qq.com", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "设备没有安装" + Appname1, Toast.LENGTH_SHORT).show();
                }
            }
        });

        weiboTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtils.checkPackInfo(MainActivity.this, packagename2)) {
                    Intent intent = new Intent(MainActivity.this, ClockActivity.class);
                    intent.putExtra("type", 2);
                    intent.putExtra("start", 1);
                    intent.putExtra("appname", Appname2);
                    intent.putExtra("packagename", packagename2);
                    Log.i("qidong", "跳转ClockActivity前-钉钉str:");
                    if (!flag && !netflag) {
                        startActivity(intent);
                    } else if (netflag)
                        Toast.makeText(MainActivity.this, "请检查网络是否连接", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(MainActivity.this, "试用期已过，请联系捐赠，QQ及邮箱：770744617@qq.com", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("qidong", "设备没有安装钉钉提示前");
                    Toast.makeText(MainActivity.this, "设备没有安装" + Appname2, Toast.LENGTH_SHORT).show();
                }
            }
        });
        wechatTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtils.checkPackInfo(MainActivity.this, packagename3)) {
                    Intent intent = new Intent(MainActivity.this, ClockActivity.class);
                    intent.putExtra("type", 3);
                    intent.putExtra("start", 0);
                    intent.putExtra("appname", Appname3);
                    intent.putExtra("packagename", packagename3);
                    Log.i("qidong", "跳转ClockActivity前-微信str:");
                    if (!flag && !netflag) {
                        startActivity(intent);
                    } else if (netflag)
                        Toast.makeText(MainActivity.this, "请检查网络是否连接", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(MainActivity.this, "试用期已过，请联系捐赠，QQ及邮箱：770744617@qq.com", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "设备没有安装" + Appname3, Toast.LENGTH_SHORT).show();
                }
            }
        });


        douyin_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtils.checkPackInfo(MainActivity.this, packagename4)) {
                    Intent intent = new Intent(MainActivity.this, ClockActivity.class);
                    intent.putExtra("type", 4);
                    intent.putExtra("start", 0);
                    intent.putExtra("appname", Appname4);
                    intent.putExtra("packagename", packagename4);
                    Log.i("qidong", "跳转ClockActivity前-抖音:");

                    if (!flag && !netflag) {
                        startActivity(intent);
                    } else if (netflag)
                        Toast.makeText(MainActivity.this, "请检查网络是否连接", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(MainActivity.this, "试用期已过，请联系捐赠，QQ及邮箱：770744617@qq.com", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainActivity.this, "设备没有安" + Appname4, Toast.LENGTH_SHORT).show();
                }
            }
        });
        set_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, SetAppPackage.class);
                startActivity(intent);
                Log.i("qidong", "跳转SetAppPackage:");

            }
        });

        settimeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                Dialog dialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance();              //获取日期对象
                        c.setTimeInMillis(System.currentTimeMillis());  //设置Calendar对象
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);         //设置闹钟小时数
                        c.set(Calendar.MINUTE, minute);                 //设置闹钟的分钟数
                        c.set(Calendar.SECOND, calendar.get(Calendar.SECOND));                      //设置闹钟的秒数
                        c.set(Calendar.MILLISECOND, 0);                 //设置闹钟的毫秒数
                        String realTime = new SimpleDateFormat("HH:mm").format(c.getTime());
                        Log.i("qidong", "picktime()闹钟时间1为:" + realTime);
                        settimeTv.setText("设置闹钟时间为:" + realTime);
                        b = c.getTimeInMillis();
                        Log.i("qidong", "b=c.getTimeInMillis():" + b);

                    }
                }, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        false);
                dialog.show();

            }
        });
//        final Intent mu_Intent = new Intent(MainActivity.this, MusicService.class);
        musicService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtils.checkPackInfo(MainActivity.this, packagename1)) {
                    if (!flag && !netflag) {
//                    if (MusicService.isplay == false) {
//                        mu_Intent.putExtra("Calendar", b);
//                        Log.i("qidong", "mu_Intent.putExtra(\"Calendar\",b)+b:" + b);
//                        startService(mu_Intent);
//                        musicService.setText("停止播放音乐");
//                    } else {
//                        stopService(mu_Intent);
//                        musicService.setText("启动测试" + Appname1 + "闹钟");
//                    }
                        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        Calendar once_alarm = Calendar.getInstance();
                        if (b != 0) once_alarm.setTimeInMillis(b);
                        else once_alarm.setTimeInMillis(once_alarm.getTimeInMillis());
                        setClockTime.setOnceClock(0, "once_alarm1", 1, 0, once_alarm, MainActivity.packagename1, MainActivity.this, alarmManager);
                        Toast.makeText(MainActivity.this, "已启动单次闹钟：" + new SimpleDateFormat("HH:mm").format(once_alarm.getTime()), Toast.LENGTH_SHORT).show();

                    } else if (netflag)
                        Toast.makeText(MainActivity.this, "请检查网络是否连接", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(MainActivity.this, "试用期已过，请联系捐赠，QQ及邮箱：770744617@qq.com", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(MainActivity.this, "设备没有安装" + Appname1, Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    protected void onDestroy() {
//        unregisterReceiver(msgReceiver);
        super.onDestroy();

    }

    public void check() {
//        if (!OpenAccessibilitySettingHelper.isAccessibilitySettingsOn(this,
//                MyAccessibilityService.class.getName())) {// 无障碍权限是否开启

        if (!OpenAccessibilitySettingHelper.isAccessibilitySettingsOn_beta(this)) {// 无障碍权限是否开启

            OpenAccessibilitySettingHelper.jumpToSettingPage(this);// 跳转到开启页面


        } else {
            Toast.makeText(this, "无障碍权限已开启", Toast.LENGTH_SHORT).show();
        }
    }

    private void getNetTime() {
        URL url = null;//取得资源对象
        try {
            url = new URL("https://www.baidu.com");
            //url = new URL("http://www.ntsc.ac.cn");//中国科学院国家授时中心
            //url = new URL("http://www.bjtime.cn");
            URLConnection uc = url.openConnection();//生成连接对象
            uc.connect(); //发出连接
            long ld = uc.getDate(); //取得网站日期时间
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(ld);
            final String format = formatter.format(calendar.getTime());
            final long nettime = calendar.getTimeInMillis();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                  Toast.makeText(MainActivity.this, "当前网络时间为: \n" + format, Toast.LENGTH_SHORT).show();
                    Log.i("qidong", "当前网络时间为1: " + format);
                    Calendar netcalendar = Calendar.getInstance();
                    if (nettime != 0) netcalendar.setTimeInMillis(nettime);
                    Log.i("qidong", "当前网络时间为2: time " + nettime);
                    expcalendar.set(2022, Calendar.MAY, 30, 0, 0, 0);
                    if (netcalendar.after(expcalendar)) {
                        flag = true;
                        mailtv.setText("试用期已过，请联系捐赠，QQ及邮箱：770744617@qq.com");
                        mailtv.setTextColor(android.graphics.Color.parseColor("#ff0000"));
                        Toast.makeText(MainActivity.this, "试用期已过，请联系捐赠，QQ及邮箱：770744617@qq.com", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("qidong", "网络断开");
            netflag = true;
//            Calendar netcalendar = Calendar.getInstance();
//            expcalendar.set(2022, Calendar.OCTOBER, 30, 0, 0, 0);
//            if (netcalendar.after(expcalendar)) {
//                flag = true;
//                mailtv.setText("试用期已过，请联系捐赠，QQ及邮箱：770744617@qq.com");
//                mailtv.setTextColor(android.graphics.Color.parseColor("#ff0000"));
//            }
        }
    }
//    public class MsgReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//            Log.i("qidong", " MainActivity ;接收到广播" );
//        }
//
//    }


}



