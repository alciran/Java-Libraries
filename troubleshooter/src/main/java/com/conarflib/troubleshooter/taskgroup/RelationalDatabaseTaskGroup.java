package com.conarflib.troubleshooter.taskgroup;

import java.util.List;

import com.conarflib.troubleshooter.TroubleshooterTaskGroup;
import com.conarflib.troubleshooter.task.TroubleshooterTask;

public class RelationalDatabaseTaskGroup extends TroubleshooterTaskGroup {

    public RelationalDatabaseTaskGroup() {

    }

    @Override
    public String description() {
        return "Test relational Database";
    }

    @Override
    public List<TroubleshooterTask> tasks() {

        return null;
    }

}