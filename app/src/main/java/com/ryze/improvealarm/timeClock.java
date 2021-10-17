package com.ryze.improvealarm;

import java.io.Serializable;
import java.util.Calendar;

public class timeClock implements Serializable {
    public boolean clockSetflag;
    public int clocknum;
    public int type;
    public int start;
    public String action;
    public Calendar calendar;

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    public String packagename;


    public boolean isClockSetflag() {
        return clockSetflag;
    }

    public void setClockSetflag(boolean clockSetflag) {
        this.clockSetflag = clockSetflag;
    }

    public int getClocknum() {
        return clocknum;
    }

    public void setClocknum(int clocknum) {
        this.clocknum = clocknum;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }
}

