package com.conarflib.troubleshooter;

import java.util.ArrayList;
import java.util.List;

import com.conarflib.troubleshooter.model.ExecutationInfo;
import com.conarflib.troubleshooter.model.TaskStatus;
import com.conarflib.troubleshooter.taskgroup.RelationalDatabaseTaskGroup;
import com.conarflib.util.io.console.ConsoleOutput;

public class TroubleshooterCommandLine extends ExecutationInfo {

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
        System.out.println("Total Tasks: " + this.getTotalTasksExecuted() + " => "
                + ConsoleOutput.getGreenBackground(String.valueOf(" " + getTotalTasksExecuted() + " ")) + " Pass, "
                + ConsoleOutput.getRedBackground(String.valueOf(" " + getTotalTasksExecuted() + " ")) + " Fail, "
                + ConsoleOutput.getYellowBackground(String.valueOf(" " + getTotalTasksWarning() + " ")) + " Warning, "
                + ConsoleOutput.getCyanBackground(String.valueOf(" " + getTotalTasksIgnored() + " ")) + " Ignored");
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("Total time: " + getTotalTime() + " milliseconds");

        System.out.println("Finished at: " + getFinishedAtFormat("dd/MM/yyyy HH:mm:ss"));
        System.out.println("-----------------------------------------------------------------------------");
    }

    public void exec() {
        this.startCounterTime();
        loadInitialMessage();
        execTasks();
        this.stopCounterTime();
        this.printFinalResults();
    }

    private void execTasks() {
        List<TroubleshooterTaskGroup> groups = getGroups();
        if (groups != null && !groups.isEmpty()) {
            groups.forEach(group -> {
                group.startCounterTime();
                System.out.println(ConsoleOutput
                        .getBlue("\n## [ " + ConsoleOutput.getBlue(group.description()) + ConsoleOutput.getBlue(" ]")));
                group.generateTasks().forEach(task -> {

                    TaskStatus status = task.getStatus();
                    group.loadTask(status);
                    this.loadTask(status);

                    if (status.equals(TaskStatus.SUCCESS))
                        System.out
                                .println(ConsoleOutput
                                        .getGreenBackground(String.valueOf(" " + group.getTotalTasksExecuted() + " "))
                                        + ConsoleOutput.printSuccess(task.getMessage()));
                    else if (status.equals(TaskStatus.FAIL))
                        System.out
                                .println(ConsoleOutput
                                        .getRedBackground(String.valueOf(" " + group.getTotalTasksExecuted() + " "))
                                        + ConsoleOutput.printError(task.getMessage()));
                    else if (status.equals(TaskStatus.WARNING))
                        System.out
                                .println(
                                        ConsoleOutput.getYellowBackground(
                                                String.valueOf(" " + group.getTotalTasksExecuted() + " "))
                                                + ConsoleOutput.printWarning(task.getMessage()));
                    else if (status.equals(TaskStatus.IGNORED))
                        System.out
                                .println(
                                        ConsoleOutput.getCyanBackground(
                                                String.valueOf(" " + group.getTotalTasksExecuted() + " "))
                                                + ConsoleOutput.printIgnored(task.getMessage()));
                }

                );
            });
        }
    }

}
