package com.conarflib.troubleshooter.model;

import lombok.Getter;

@Getter
public class ExecutationInfo extends TimeCounter {

    private int totalTasksExecuted;
    private int totalTasksSuccess;
    private int totalTasksFail;
    private int totalTasksWarning;
    private int totalTasksIgnored;

    public void loadTask(TaskStatus status) {
        this.totalTasksExecuted++;

        if (status.equals(TaskStatus.SUCCESS))
            this.totalTasksSuccess++;
        else if (status.equals(TaskStatus.FAIL))
            this.totalTasksFail++;
        else if (status.equals(TaskStatus.WARNING))
            this.totalTasksWarning++;
        else if (status.equals(TaskStatus.IGNORED))
            this.totalTasksIgnored++;
    }

}
