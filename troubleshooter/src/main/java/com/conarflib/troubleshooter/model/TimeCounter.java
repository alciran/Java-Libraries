package com.conarflib.troubleshooter.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Getter;

@Getter
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
        return (this.getStopCounterTime() - this.getStartCounterTime());
    }

    public String getFinishedAtFormat(String format) {
        if (this.stopDate != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            return formatter.format(this.stopDate);
        } else
            return null;
    }

}
