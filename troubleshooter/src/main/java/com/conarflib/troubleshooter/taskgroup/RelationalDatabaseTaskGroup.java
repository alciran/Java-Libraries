package com.conarflib.troubleshooter.taskgroup;

import java.util.ArrayList;
import java.util.List;

import com.conarflib.database.relational.DataSourceConfig;
import com.conarflib.troubleshooter.TroubleshooterTaskGroup;
import com.conarflib.troubleshooter.model.TroubleshooterTask;
import com.conarflib.troubleshooter.task.RelationalDatabaseProviderTask;

public class RelationalDatabaseTaskGroup extends TroubleshooterTaskGroup {

    private DataSourceConfig dataSourceConfig;

    public RelationalDatabaseTaskGroup() {
        this.dataSourceConfig = new DataSourceConfig("PostgreSQL", "localhost", "5432", "postgres", "Medcloud2022",
                "medx_test");
    }

    @Override
    public String description() {
        return "Test relational Database";
    }

    @Override
    public List<TroubleshooterTask> generateTasks() {
        List<TroubleshooterTask> tasks = new ArrayList<>();
        tasks.add(RelationalDatabaseProviderTask.databaseStructureHost(this.dataSourceConfig.getHost()));
        tasks.add(RelationalDatabaseProviderTask.checkConnection(dataSourceConfig));
        tasks.add(RelationalDatabaseProviderTask.getDatabaseObject(dataSourceConfig, "serviceordermedcloud"));
        tasks.add(RelationalDatabaseProviderTask.getSchema(dataSourceConfig, "public"));
        tasks.add(RelationalDatabaseProviderTask.getDatabaseObject(dataSourceConfig, null));
        return tasks;
    }

}
