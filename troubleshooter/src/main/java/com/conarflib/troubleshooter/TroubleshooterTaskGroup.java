package com.conarflib.troubleshooter;

import java.util.List;

import com.conarflib.troubleshooter.model.ExecutationInfo;
import com.conarflib.troubleshooter.task.TaskStatusHandler;
import com.conarflib.troubleshooter.task.TroubleshooterTask;

public abstract class TroubleshooterTaskGroup extends ExecutationInfo implements TaskStatusHandler {

    public abstract String description();

    public abstract List<TroubleshooterTask> tasks();

    @Override
    public void taskWithStatusSucess() {
        this.taskSuccess();
    }

    @Override
    public void taskWithStatusFail() {
        this.taskFail();
    }

    @Override
    public void taskWithStatusWarning() {
        this.taskWarning();
    }

    @Override
    public void taskWithStatusIgnored() {
        this.taskIgnored();
    }

    public void executeTasks() {
        this.startCounterTime();
        tasks().forEach(task -> this.handlerStatusTask(task.getStatus()));
        this.stopCounterTime();
    }

}
