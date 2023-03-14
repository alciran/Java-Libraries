package com.conarflib.troubleshooter;

import java.util.List;

import com.conarflib.troubleshooter.model.ExecutationInfo;
import com.conarflib.troubleshooter.model.TroubleshooterTask;

public abstract class TroubleshooterTaskGroup extends ExecutationInfo {

    public abstract String description();

    public abstract List<TroubleshooterTask> generateTasks();

}
