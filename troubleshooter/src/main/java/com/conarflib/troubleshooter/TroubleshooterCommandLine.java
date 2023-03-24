package com.conarflib.troubleshooter;

import java.util.ArrayList;
import java.util.List;

import com.conarflib.troubleshooter.model.ExecutationInfo;
import com.conarflib.troubleshooter.task.TaskStatusHandler;
import com.conarflib.troubleshooter.task.TroubleshooterTask;
import com.conarflib.troubleshooter.taskgroup.RelationalDatabaseTaskGroup;
import com.conarflib.util.io.console.ConsoleOutput;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class TroubleshooterCommandLine extends ExecutationInfo implements TaskStatusHandler {

    private String messageActualTask;
    private String nameActualTask;
    private static final String printTaskInfoFormat = "|%-2s  %-37s  %-8s   %-66s |%n";

    private static void loadInitialMessage() {
        BufferedImage image = new BufferedImage(115, 40, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setFont(new Font("SansSerif", Font.PLAIN, 12));
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.drawString("TROUBLESHOOTER", 0, 12);

        for (int y = 0; y < 40; y++) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < 115; x++)
                sb.append(image.getRGB(x, y) == -16777216 ? " "
                        : image.getRGB(x, y) == -1 ? ConsoleOutput.getBlueBoldBright("#")
                                : ConsoleOutput.getBlueBoldBright("#"));
            if (sb.toString().trim().isEmpty())
                continue;
            System.out.println(sb);
        }
        System.out.println(
                ConsoleOutput.getPurpleBoldBright("\n   Troubleshooter Command Line - Version: 0.0.1\n"));
    }

    private static List<TroubleshooterTaskGroup> getGroups() {
        List<TroubleshooterTaskGroup> groups = new ArrayList<>();
        groups.add(new RelationalDatabaseTaskGroup());
        return groups;
    }

    private void printFinalResults() {
        System.out.println("\n-----------------------------------------------------------------------------");
        System.out.println("Total Tasks executed: " + getTotalTasksExecuted() + " => "
                + ConsoleOutput.getGreenBackground(String.valueOf(" " + getTotalTasksSuccess() + " "))
                + " Pass, "
                + ConsoleOutput.getRedBackground(String.valueOf(" " + getTotalTasksFail() + " "))
                + " Fail, "
                + ConsoleOutput.getYellowBackground(String.valueOf(" " + getTotalTasksWarning() + " "))
                + " Warning, "
                + ConsoleOutput.getCyanBackground(String.valueOf(" " + getTotalTasksIgnored() + " "))
                + " Ignored");
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("Total time: " + getTotalTime() + " milliseconds");

        System.out.println("Finished at: " + getFinishedDateAtFormat("dd/MM/yyyy HH:mm:ss"));
        System.out.println("-----------------------------------------------------------------------------");
    }

    private static void printHeaderTasksTable() {
        System.out.format(
                "+----+--------------------------------------+--------+--------------------------------------------------------------------+%n");
        System.out.format(
                "|Task|          Task Group / Name           | Status |                         Description                                +%n");
        System.out.format(
                "+----+--------------------------------------+--------+--------------------------------------------------------------------+%n");
    }

    private static void printFooterTasksTable() {
        System.out.format(
                "+----+--------------------------------------+--------+-------------------------------------------------------------------+%n");
    }

    private void setMessageActualTask(String message) {
        if (message == null)
            this.messageActualTask = " ";
        else
            this.messageActualTask = message;
    }

    private void setNameActualTask(String name) {
        if (name == null)
            this.nameActualTask = " ";
        else
            this.nameActualTask = name;
    }

    public void exec() {
        this.startCounterTime();
        loadInitialMessage();
        execTasks();
        this.stopCounterTime();
        printFinalResults();
    }

    private void execTasks() {
        List<TroubleshooterTaskGroup> groups = getGroups();
        if (groups != null && !groups.isEmpty()) {
            printHeaderTasksTable();
            groups.forEach(group -> {
                List<TroubleshooterTask> tasks = group.tasks();
                if (!tasks.isEmpty()) {
                    group.startCounterTime();
                    tasks.forEach(task -> {
                        group.handlerStatusTask(task.getStatus());
                        this.setMessageActualTask(task.getMessage());
                        this.setNameActualTask(group.description() + "/" + task.getTaskName());
                        this.handlerStatusTask(task.getStatus());
                    });
                    group.stopCounterTime();
                }
            });
            printFooterTasksTable();
        }
    }

    @Override
    public void taskWithStatusSucess() {
        this.taskSuccess();
        System.out.format(printTaskInfoFormat,
                ConsoleOutput.getGreenBackground(" " + String.valueOf(getTotalTasksExecuted()) + " "),
                this.nameActualTask,
                ConsoleOutput.getGreenBold("SUCCESS"), this.messageActualTask);
    }

    @Override
    public void taskWithStatusFail() {
        this.taskFail();
        System.out.format(printTaskInfoFormat,
                ConsoleOutput.getRedBackground(" " + String.valueOf(getTotalTasksExecuted()) + " "),
                this.nameActualTask,
                ConsoleOutput.getRedBold("FAIL   "), this.messageActualTask);
    }

    @Override
    public void taskWithStatusWarning() {
        this.taskWarning();
        System.out.format(printTaskInfoFormat,
                ConsoleOutput.getYellowBackground(" " + String.valueOf(getTotalTasksExecuted()) + " "),
                this.nameActualTask,
                ConsoleOutput.getYellowBold("WARNING"), this.messageActualTask);
    }

    @Override
    public void taskWithStatusIgnored() {
        this.taskIgnored();
        System.out.format(printTaskInfoFormat,
                ConsoleOutput.getCyanBackground(" " + String.valueOf(getTotalTasksExecuted()) + " "),
                this.nameActualTask,
                ConsoleOutput.getCyanBold("IGNORED"), this.messageActualTask);
    }

}
