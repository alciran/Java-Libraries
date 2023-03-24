package com.conarflib.troubleshooter.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeCounter {
    private Date startDate;
    private Date stopDate;
    private long startCounterTime;
    private long stopCounterTime;

    public void startCounterTime() {
        startDate = new Date();
        startCounterTime = System.currentTimeMillis();
    }

    public void stopCounterTime() {
        stopDate = new Date();
        stopCounterTime = System.currentTimeMillis();
    }

    public Long getTotalTime() {
        return (this.stopCounterTime - this.startCounterTime);
    }

    public String getFinishedDateAtFormat(String format) {
        if (this.stopDate != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            return formatter.format(this.stopDate);
        } else
            return null;
    }

    public String getStartDateAtFormat(String format) {
        if (this.startDate != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            return formatter.format(this.stopDate);
        } else
            return null;
    }

}
