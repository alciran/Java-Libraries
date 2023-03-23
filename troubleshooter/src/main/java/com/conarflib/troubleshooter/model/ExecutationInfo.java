package com.conarflib.troubleshooter.model;

import com.conarflib.troubleshooter.task.TaskStatusHandler;

import lombok.Getter;

@Getter
public class ExecutationInfo extends TimeCounter implements TaskStatusHandler {

    private int totalTasksExecuted;
    private int totalTasksSuccess;
    private int totalTasksFail;
    private int totalTasksWarning;
    private int totalTasksIgnored;

    @Override
    public void taskWithStatusSucess() {
        totalTasksSuccess++;
    }

    @Override
    public void taskWithStatusFail() {
        totalTasksFail++;
    }

    @Override
    public void taskWithStatusWarning() {
        totalTasksWarning++;
    }

    @Override
    public void taskWithStatusIgnored() {
        totalTasksIgnored++;
    }

    public void loadedTask() {
        totalTasksExecuted++;
    }

}
