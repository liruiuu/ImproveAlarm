package com.ryze.improvealarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class ClockActivity extends Activity {
    public static AlarmManager alarmManager = null;
    private int hour1;
    private int minute1;
    private int hour2;
    private int minute2;
    private int hour3;
    private int minute3;
    private int hour4;
    private int minute4;

    Calendar calendar;
    TextView title_tv, set_repeat_alarm1, set_repeat_alarm2, set_once_alarm1, set_once_alarm2;
    private Calendar repeat_alarm1;
    private Calendar repeat_alarm2;
    private Calendar once_alarm1;
    private Calendar once_alarm2;
    public static int sd;
    Button button_repeat_alarm1, button_repeat_alarm2, button_once_alarm1, button_once_alarm2;
    private int type = 1;
    private int start = 1;
    private String appname;
    private String packagename;
    private timeClock timeClock;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sd = 10;//测试静态变量用，没有实际作用

//        Intent intent = getIntent();
//        timeClock = (timeClock) getIntent().getSerializableExtra("timeClock");

        Log.i("qidong", "跳转到ClockActivity-onCreate:sd:" + sd);
        setContentView(R.layout.activity_clock);
        title_tv = findViewById(R.id.title_tv);
        set_repeat_alarm1 = findViewById(R.id.set_repeat_alarm1);
        set_repeat_alarm2 = findViewById(R.id.set_repeat_alarm2);
        set_once_alarm1 = findViewById(R.id.set_once_alarm1);
        set_once_alarm2 = findViewById(R.id.set_once_alarm2);
        button_repeat_alarm1 = findViewById(R.id.button_repeat_alarm1);
        button_repeat_alarm2 = findViewById(R.id.button_repeat_alarm2);
        button_once_alarm1 = findViewById(R.id.button_once_alarm1);
        button_once_alarm2 = findViewById(R.id.button_once_alarm2);
        type = getIntent().getIntExtra("type", 1);
        start = getIntent().getIntExtra("start", 1);
        appname = getIntent().getStringExtra("appname");
        packagename = getIntent().getStringExtra("packagename");

        sp = getSharedPreferences("clocktime" + type, Context.MODE_PRIVATE);

        hour1 = sp.getInt("hour1", 8);
        minute1 = sp.getInt("minute1", 25);
        hour2 = sp.getInt("hour2", 18);
        minute2 = sp.getInt("minute2", 10);
        hour3 = sp.getInt("hour3", 8);
        minute3 = sp.getInt("minute3", 25);
        hour4 = sp.getInt("hour4", 18);
        minute4 = sp.getInt("minute4", 10);

        repeat_alarm1 = Calendar.getInstance();
        repeat_alarm2 = Calendar.getInstance();
        once_alarm1 = Calendar.getInstance();
        once_alarm2 = Calendar.getInstance();

        repeat_alarm1.set(Calendar.HOUR_OF_DAY, hour1);
        repeat_alarm1.set(Calendar.MINUTE, minute1);
        repeat_alarm2.set(Calendar.HOUR_OF_DAY, hour2);
        repeat_alarm2.set(Calendar.MINUTE, minute2);
        once_alarm1.set(Calendar.HOUR_OF_DAY, hour3);
        once_alarm1.set(Calendar.MINUTE, minute3);
        once_alarm2.set(Calendar.HOUR_OF_DAY, hour4);
        once_alarm2.set(Calendar.MINUTE, minute4);

        title_tv.setText("设置" + appname + "启动闹钟");

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Log.i("qidong", "设置闹钟前");
    }

    @Override
    protected void onResume() {
        super.onResume();

        String realTime = new SimpleDateFormat("HH:mm").format(repeat_alarm1.getTime());
        set_repeat_alarm1.setText("设置重复上班闹钟：" + realTime);
        realTime = new SimpleDateFormat("HH:mm").format(repeat_alarm2.getTime());
        set_repeat_alarm2.setText("设置重复下班闹钟：" + realTime);
        realTime = new SimpleDateFormat("HH:mm").format(once_alarm1.getTime());
        set_once_alarm1.setText("设置单次闹钟A：" + realTime);
        realTime = new SimpleDateFormat("HH:mm").format(once_alarm2.getTime());
        set_once_alarm2.setText("设置单次闹钟B：" + realTime);
        final SharedPreferences.Editor editor = sp.edit();

        title_tv.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                repeat_alarm1 = Calendar.getInstance();
                repeat_alarm2 = Calendar.getInstance();
                once_alarm1 = Calendar.getInstance();
                once_alarm2 = Calendar.getInstance();

                repeat_alarm1.set(Calendar.HOUR_OF_DAY, 8);
                repeat_alarm1.set(Calendar.MINUTE, 25);
                repeat_alarm2.set(Calendar.HOUR_OF_DAY, 18);
                repeat_alarm2.set(Calendar.MINUTE, 10);
                once_alarm1.set(Calendar.HOUR_OF_DAY, 8);
                once_alarm1.set(Calendar.MINUTE, 25);
                once_alarm2.set(Calendar.HOUR_OF_DAY, 18);
                once_alarm2.set(Calendar.MINUTE, 10);

                String realTime = new SimpleDateFormat("HH:mm").format(repeat_alarm1.getTime());
                set_repeat_alarm1.setText("设置重复上班闹钟：" + realTime);
                realTime = new SimpleDateFormat("HH:mm").format(repeat_alarm2.getTime());
                set_repeat_alarm2.setText("设置重复下班闹钟：" + realTime);
                realTime = new SimpleDateFormat("HH:mm").format(once_alarm1.getTime());
                set_once_alarm1.setText("设置单次闹钟A：" + realTime);
                realTime = new SimpleDateFormat("HH:mm").format(once_alarm2.getTime());
                set_once_alarm2.setText("设置单次闹钟B：" + realTime);

                return false;
            }

        });
        set_repeat_alarm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                Dialog dialog = new TimePickerDialog(ClockActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        long b;
                        Calendar c = Calendar.getInstance();              //获取日期对象
                        c.setTimeInMillis(System.currentTimeMillis());  //设置Calendar对象
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);         //设置闹钟小时数
                        c.set(Calendar.MINUTE, minute);                 //设置闹钟的分钟数
                        c.set(Calendar.SECOND, calendar.get(Calendar.SECOND));                      //设置闹钟的秒数
                        c.set(Calendar.MILLISECOND, 0);                 //设置闹钟的毫秒数
                        String realTime = new SimpleDateFormat("HH:mm").format(c.getTime());
                        Log.i("qidong", "picktime()闹钟时间1为:" + realTime);
                        repeat_alarm1 = c;
                        b = c.getTimeInMillis();
                        Log.i("qidong", "b=c.getTimeInMillis():" + b);
                        realTime = new SimpleDateFormat("HH:mm").format(repeat_alarm1.getTime());
                        set_repeat_alarm1.setText("设置重复上班闹钟：" + realTime);

                    }
                }, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        false);
                dialog.show();
            }
        });
        button_repeat_alarm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (repeat_alarm1 == null) {
                    Toast.makeText(ClockActivity.this, "请先设置闹钟", Toast.LENGTH_SHORT).show();
                } else {
                    editor.putInt("hour1", repeat_alarm1.get(Calendar.HOUR_OF_DAY));
                    editor.putInt("minute1", repeat_alarm1.get(Calendar.MINUTE));
                    editor.commit();

                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String format = formatter.format(repeat_alarm1.getTime());
                    Log.i("qidong", "updateDay(repeat_alarm1)前 " + format);

                    repeat_alarm1 = updateDay(repeat_alarm1);

                    format = formatter.format(repeat_alarm1.getTime());
                    Log.i("qidong", "updateDay(repeat_alarm1)后" + format);

                    setClockTime.setOnceRandomClock(1, "repeat_alarm1", type, start, repeat_alarm1, packagename, ClockActivity.this, alarmManager);
                    Toast.makeText(ClockActivity.this, "已启动重复上班闹钟：" + new SimpleDateFormat("HH:mm").format(repeat_alarm1.getTime()), Toast.LENGTH_SHORT).show();
                }
            }
        });


        set_repeat_alarm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                Dialog dialog = new TimePickerDialog(ClockActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        long b;
                        Calendar c = Calendar.getInstance();              //获取日期对象
                        c.setTimeInMillis(System.currentTimeMillis());  //设置Calendar对象
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);         //设置闹钟小时数
                        c.set(Calendar.MINUTE, minute);                 //设置闹钟的分钟数
                        c.set(Calendar.SECOND, calendar.get(Calendar.SECOND));                      //设置闹钟的秒数
                        c.set(Calendar.MILLISECOND, 0);                 //设置闹钟的毫秒数
                        String realTime = new SimpleDateFormat("HH:mm").format(c.getTime());
                        Log.i("qidong", "picktime()设置重复下班闹钟：" + realTime);
                        repeat_alarm2 = c;
                        b = c.getTimeInMillis();
                        Log.i("qidong", "b=c.getTimeInMillis():" + b);
                        realTime = new SimpleDateFormat("HH:mm").format(repeat_alarm2.getTime());
                        set_repeat_alarm2.setText("设置重复下班闹钟：" + realTime);

                    }
                }, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        false);
                dialog.show();
            }
        });
        button_repeat_alarm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (repeat_alarm2 == null) {
                    Toast.makeText(ClockActivity.this, "请先设置闹钟", Toast.LENGTH_SHORT).show();
                } else {
                    editor.putInt("hour2", repeat_alarm2.get(Calendar.HOUR_OF_DAY));
                    editor.putInt("minute2", repeat_alarm2.get(Calendar.MINUTE));
                    editor.commit();
                    repeat_alarm2 = updateDay(repeat_alarm2);
                    setClockTime.setOnceRandomClock(2, "repeat_alarm2", type, start, repeat_alarm2, packagename, ClockActivity.this, alarmManager);
                    Toast.makeText(ClockActivity.this, "已启动重复下班闹钟：" + new SimpleDateFormat("HH:mm").format(repeat_alarm2.getTime()), Toast.LENGTH_SHORT).show();
                }
            }
        });
        set_once_alarm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                Dialog dialog = new TimePickerDialog(ClockActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        long b;
                        Calendar c = Calendar.getInstance();              //获取日期对象
                        c.setTimeInMillis(System.currentTimeMillis());  //设置Calendar对象
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);         //设置闹钟小时数
                        c.set(Calendar.MINUTE, minute);                 //设置闹钟的分钟数
                        c.set(Calendar.SECOND, calendar.get(Calendar.SECOND));                      //设置闹钟的秒数
                        c.set(Calendar.MILLISECOND, 0);                 //设置闹钟的毫秒数
                        String realTime = new SimpleDateFormat("HH:mm").format(c.getTime());
                        Log.i("qidong", "picktime()设置单次闹钟A：:" + realTime);
                        once_alarm1 = c;
                        b = c.getTimeInMillis();
                        Log.i("qidong", "b=c.getTimeInMillis():" + b);
                        realTime = new SimpleDateFormat("HH:mm").format(once_alarm1.getTime());
                        set_once_alarm1.setText("设置单次闹钟A：" + realTime);


                    }
                }, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        false);
                dialog.show();


            }
        });
        button_once_alarm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (once_alarm1 == null) {
                    Toast.makeText(ClockActivity.this, "请先设置闹钟", Toast.LENGTH_SHORT).show();
                } else {
                    editor.putInt("hour3", once_alarm1.get(Calendar.HOUR_OF_DAY));
                    editor.putInt("minute3", once_alarm1.get(Calendar.MINUTE));
                    editor.commit();
                    once_alarm1 = updateDay(once_alarm1);
                    setClockTime.setOnceClock(3, "once_alarm1", type, start, once_alarm1, packagename, ClockActivity.this, alarmManager);
                    Toast.makeText(ClockActivity.this, "已启动单次闹钟A：" + new SimpleDateFormat("HH:mm").format(once_alarm1.getTime()), Toast.LENGTH_SHORT).show();
                }
            }
        });


        set_once_alarm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                Dialog dialog = new TimePickerDialog(ClockActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        long b;
                        Calendar c = Calendar.getInstance();              //获取日期对象
                        c.setTimeInMillis(System.currentTimeMillis());  //设置Calendar对象
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);         //设置闹钟小时数
                        c.set(Calendar.MINUTE, minute);                 //设置闹钟的分钟数
                        c.set(Calendar.SECOND, calendar.get(Calendar.SECOND));                      //设置闹钟的秒数
                        c.set(Calendar.MILLISECOND, 0);                 //设置闹钟的毫秒数
                        String realTime = new SimpleDateFormat("HH:mm").format(c.getTime());
                        Log.i("qidong", "picktime()闹钟时间1为:" + realTime);
                        once_alarm2 = c;
                        b = c.getTimeInMillis();
                        Log.i("qidong", "b=c.getTimeInMillis():" + b);
                        realTime = new SimpleDateFormat("HH:mm").format(once_alarm2.getTime());
                        set_once_alarm2.setText("设置单次闹钟B：" + realTime);
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        false);
                dialog.show();


            }
        });

        button_once_alarm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (once_alarm2 == null) {
                    Toast.makeText(ClockActivity.this, "请先设置闹钟", Toast.LENGTH_SHORT).show();
                } else {
                    editor.putInt("hour4", once_alarm2.get(Calendar.HOUR_OF_DAY));
                    editor.putInt("minute4", once_alarm2.get(Calendar.MINUTE));
                    editor.commit();
                    once_alarm2 = updateDay(once_alarm2);
                    setClockTime.setOnceClock(4, "once_alarm2", type, start, once_alarm2, packagename, ClockActivity.this, alarmManager);
                    Toast.makeText(ClockActivity.this, "已启动单次闹钟B：" + new SimpleDateFormat("HH:mm").format(once_alarm2.getTime()), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("qidong", "ClockActivity：onDestroy()");
    }


    /**********以下代码updateDay*****防止打开夸天后没有选时间点击，时间还是前一天的时间，导致闹钟不响铃**********/
    private Calendar updateDay(Calendar c) {

        calendar = Calendar.getInstance();
        Log.i("qidong", "updateDay===calendar.get(Calendar.DAY_OF_YEAR)" + calendar.get(Calendar.DAY_OF_YEAR));
        Log.i("qidong", "updateDay===c.get(Calendar.DAY_OF_YEAR)" + c.get(Calendar.DAY_OF_YEAR));

        if (calendar.get(Calendar.DAY_OF_YEAR) == c.get(Calendar.DAY_OF_YEAR)&&calendar.get(Calendar.YEAR) == c.get(Calendar.YEAR)) {
            return c;
        } else {
            calendar.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, c.get(Calendar.MINUTE));
            calendar.set(Calendar.SECOND, c.get(Calendar.SECOND));
            Log.i("qidong", "updateDay(Calendar c)执行========");
            return calendar;
        }
    }

}
