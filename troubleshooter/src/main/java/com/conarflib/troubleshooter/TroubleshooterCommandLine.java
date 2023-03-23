package com.conarflib.troubleshooter;

import java.util.ArrayList;
import java.util.List;

import com.conarflib.troubleshooter.model.ExecutationInfo;
import com.conarflib.troubleshooter.task.TaskStatusHandler;
import com.conarflib.troubleshooter.taskgroup.RelationalDatabaseTaskGroup;
import com.conarflib.util.io.console.ConsoleOutput;

public class TroubleshooterCommandLine implements TaskStatusHandler {

    private ExecutationInfo executationInfo;
    private String messageActualTask;

    public TroubleshooterCommandLine() {
        this.executationInfo = new ExecutationInfo();
    }

    private static void loadInitialMessage() {
        System.out.println(ConsoleOutput.getBlue(" Troubleshooter Command Line - Version: 0.0.1 "));
    }

    private static List<TroubleshooterTaskGroup> getGroups() {
        List<TroubleshooterTaskGroup> groups = new ArrayList<>();
        groups.add(new RelationalDatabaseTaskGroup());
        return groups;
    }

    private void printFinalResults() {
        System.out.println("\n-----------------------------------------------------------------------------");
        System.out.println("Total Tasks executed: " + executationInfo.getTotalTasksExecuted() + " => "
                + ConsoleOutput.getGreenBackground(String.valueOf(" " + executationInfo.getTotalTasksSuccess() + " "))
                + " Pass, "
                + ConsoleOutput.getRedBackground(String.valueOf(" " + executationInfo.getTotalTasksFail() + " "))
                + " Fail, "
                + ConsoleOutput.getYellowBackground(String.valueOf(" " + executationInfo.getTotalTasksWarning() + " "))
                + " Warning, "
                + ConsoleOutput.getCyanBackground(String.valueOf(" " + executationInfo.getTotalTasksIgnored() + " "))
                + " Ignored");
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("Total time: " + executationInfo.getTotalTime() + " milliseconds");

        System.out.println("Finished at: " + executationInfo.getFinishedAtFormat("dd/MM/yyyy HH:mm:ss"));
        System.out.println("-----------------------------------------------------------------------------");
    }

    private static final void printGroupDescription(String description) {
        // print description group tasks => blue
        System.out.println(ConsoleOutput
                .getBlue("\n## [ " + ConsoleOutput.getBlue(description) + ConsoleOutput.getBlue(" ]")));
    }

    public void exec() {
        executationInfo.startCounterTime();
        loadInitialMessage();
        execTasks();
        executationInfo.stopCounterTime();
        this.printFinalResults();
    }

    private void execTasks() {
        List<TroubleshooterTaskGroup> groups = getGroups();
        if (groups != null && !groups.isEmpty()) {
            groups.forEach(group -> {
                printGroupDescription(group.description());
                group.tasks().forEach(task -> {
                    group.startCounterTime();
                    group.handlerStatusTask(task.getStatus());
                    group.stopCounterTime();
                    executationInfo.loadedTask();
                    executationInfo.handlerStatusTask(task.getStatus());
                    this.messageActualTask = task.getMessage();
                    this.handlerStatusTask(task.getStatus());

                });
            });
        }
    }

    @Override
    public void taskWithStatusSucess() {
        System.out.println(ConsoleOutput
                .getGreenBackground(String.valueOf(" " + executationInfo.getTotalTasksExecuted() + " "))
                + ConsoleOutput.printSuccess(this.messageActualTask));
    }

    @Override
    public void taskWithStatusFail() {
        System.out.println(ConsoleOutput
                .getRedBackground(String.valueOf(" " + executationInfo.getTotalTasksExecuted() + " "))
                + ConsoleOutput.printError(this.messageActualTask));
    }

    @Override
    public void taskWithStatusWarning() {
        System.out.println(ConsoleOutput
                .getYellowBackground(String.valueOf(" " + executationInfo.getTotalTasksExecuted() + " "))
                + ConsoleOutput.printWarning(this.messageActualTask));
    }

    @Override
    public void taskWithStatusIgnored() {
        System.out.println(ConsoleOutput
                .getCyanBackground(String.valueOf(" " + executationInfo.getTotalTasksExecuted() + " "))
                + ConsoleOutput.printIgnored(this.messageActualTask));
    }

}
