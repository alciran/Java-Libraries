package com.conarflib.troubleshooter.model;

import lombok.Getter;

@Getter
public class ExecutationInfo extends TimeCounter {

    private int totalTasksExecuted;
    private int totalTasksSuccess;
    private int totalTasksFail;
    private int totalTasksWarning;
    private int totalTasksIgnored;

    public void taskSuccess() {
        totalTasksExecuted++;
        totalTasksSuccess++;
    }

    public void taskFail() {
        totalTasksExecuted++;
        totalTasksFail++;
    }

    public void taskWarning() {
        totalTasksExecuted++;
        totalTasksWarning++;
    }

    public void taskIgnored() {
        totalTasksExecuted++;
        totalTasksIgnored++;
    }

}
