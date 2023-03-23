package com.conarflib.troubleshooter.task;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TroubleshooterTask {

    private String taskName;
    private TaskStatus status;
    private String message = "";

    public TroubleshooterTask(String taskName, TaskStatus status, String message) {
        this.taskName = taskName;
        this.status = status;
        this.message = message;
    }

}
