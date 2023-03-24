package com.conarflib.troubleshooter.task;

public interface TaskStatusHandler {

    abstract void taskWithStatusSucess();

    void taskWithStatusFail();

    void taskWithStatusWarning();

    void taskWithStatusIgnored();

    default void handlerStatusTask(TaskStatus status) {
        if (status.equals(TaskStatus.SUCCESS))
            taskWithStatusSucess();
        else if (status.equals(TaskStatus.FAIL))
            taskWithStatusFail();
        else if (status.equals(TaskStatus.WARNING))
            taskWithStatusWarning();
        else if (status.equals(TaskStatus.IGNORED))
            taskWithStatusIgnored();
    }
}
