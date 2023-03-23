package com.conarflib.troubleshooter;

import java.util.List;

import com.conarflib.troubleshooter.model.ExecutationInfo;
import com.conarflib.troubleshooter.task.TroubleshooterTask;

public abstract class TroubleshooterTaskGroup extends ExecutationInfo {

    public abstract String description();

    public abstract List<TroubleshooterTask> tasks();

    public void executeTasks() {
        this.startCounterTime();
        tasks().forEach(task -> this.handlerStatusTask(task.getStatus()));
        this.stopCounterTime();
    }

}
