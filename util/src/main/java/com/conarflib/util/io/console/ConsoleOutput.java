package com.conarflib.util.io.console;

public final class ConsoleOutput {

    public static String getGreen(String text) {
        return ConsoleColors.GREEN + text + ConsoleColors.RESET;
    }

    public static String getGreenBold(String text) {
        return ConsoleColors.GREEN_BOLD + text + ConsoleColors.RESET;
    }

    public static String getGreenBoldBright(String text) {
        return ConsoleColors.GREEN_BOLD_BRIGHT + text + ConsoleColors.RESET;
    }

    public static String getGreenUnderlined(String text) {
        return ConsoleColors.GREEN_UNDERLINED + text + ConsoleColors.RESET;
    }

    public static String getGreenBackground(String text) {
        return ConsoleColors.GREEN_BACKGROUND + text + ConsoleColors.RESET;
    }

    public static String printSuccess(String message) {
        return "[ " + getGreenBold("OK") + " ] " + message;
    }

    public static String getRed(String text) {
        return ConsoleColors.RED + text + ConsoleColors.RESET;
    }

    public static String getRedBold(String text) {
        return ConsoleColors.RED_BOLD + text + ConsoleColors.RESET;
    }

    public static String getRedBoldBright(String text) {
        return ConsoleColors.RED_BOLD_BRIGHT + text + ConsoleColors.RESET;
    }

    public static String getRedUnderlined(String text) {
        return ConsoleColors.RED_UNDERLINED + text + ConsoleColors.RESET;
    }

    public static String getRedBackground(String text) {
        return ConsoleColors.RED_BACKGROUND + text + ConsoleColors.RESET;
    }

    public static String printError(String message) {
        return "[ " + getRedBold("ERROR") + " ] " + message;
    }

    public static String getYellow(String text) {
        return ConsoleColors.YELLOW + text + ConsoleColors.RESET;
    }

    public static String getYellowBold(String text) {
        return ConsoleColors.YELLOW_BOLD + text + ConsoleColors.RESET;
    }

    public static String getYellowBoldBright(String text) {
        return ConsoleColors.YELLOW_BOLD_BRIGHT + text + ConsoleColors.RESET;
    }

    public static String getYellowUnderlined(String text) {
        return ConsoleColors.YELLOW_UNDERLINED + text + ConsoleColors.RESET;
    }

    public static String getYellowBackground(String text) {
        return ConsoleColors.YELLOW_BACKGROUND + text + ConsoleColors.RESET;
    }

    public static String printWarning(String message) {
        return "[ " + getYellowBold("WARNING") + " ] " + message;
    }

    public static String printIgnored(String message) {
        return "[ " + getCyanBold("IGNORED") + " ] " + message;
    }

    public static String getBlack(String text) {
        return ConsoleColors.BLACK + text + ConsoleColors.RESET;
    }

    public static String getBlackBold(String text) {
        return ConsoleColors.BLACK_BOLD + text + ConsoleColors.RESET;
    }

    public static String getBlackBoldBright(String text) {
        return ConsoleColors.BLACK_BOLD_BRIGHT + text + ConsoleColors.RESET;
    }

    public static String getBlackUnderlined(String text) {
        return ConsoleColors.BLACK_UNDERLINED + text + ConsoleColors.RESET;
    }

    public static String getBlackBackground(String text) {
        return ConsoleColors.BLACK_BACKGROUND + text + ConsoleColors.RESET;
    }

    public static String getBlue(String text) {
        return ConsoleColors.BLUE + text + ConsoleColors.RESET;
    }

    public static String getBlueBold(String text) {
        return ConsoleColors.BLUE_BOLD + text + ConsoleColors.RESET;
    }

    public static String getBlueBoldBright(String text) {
        return ConsoleColors.BLUE_BOLD_BRIGHT + text + ConsoleColors.RESET;
    }

    public static String getBlueUnderlined(String text) {
        return ConsoleColors.BLUE_UNDERLINED + text + ConsoleColors.RESET;
    }

    public static String getBlueBackground(String text) {
        return ConsoleColors.BLUE_BACKGROUND + text + ConsoleColors.RESET;
    }

    public static String printInfoBlue(String message) {
        return "[ " + getBlueBold("INFO") + " ] " + message;
    }

    public static String getCyan(String text) {
        return ConsoleColors.CYAN + text + ConsoleColors.RESET;
    }

    public static String getCyanBold(String text) {
        return ConsoleColors.CYAN_BOLD + text + ConsoleColors.RESET;
    }

    public static String getCyanBoldBright(String text) {
        return ConsoleColors.CYAN_BOLD_BRIGHT + text + ConsoleColors.RESET;
    }

    public static String getCyanUnderlined(String text) {
        return ConsoleColors.CYAN_UNDERLINED + text + ConsoleColors.RESET;
    }

    public static String getCyanBackground(String text) {
        return ConsoleColors.CYAN_BACKGROUND + text + ConsoleColors.RESET;
    }

    public static String printInfoCyan(String message) {
        return "[ " + getCyanBold("INFO") + " ] " + message;
    }

    public static String getPurple(String text) {
        return ConsoleColors.PURPLE + text + ConsoleColors.RESET;
    }

    public static String getPurpleBold(String text) {
        return ConsoleColors.PURPLE_BOLD + text + ConsoleColors.RESET;
    }

    public static String getPurpleBoldBright(String text) {
        return ConsoleColors.PURPLE_BOLD_BRIGHT + text + ConsoleColors.RESET;
    }

    public static String getPurpleUnderlined(String text) {
        return ConsoleColors.PURPLE_UNDERLINED + text + ConsoleColors.RESET;
    }

    public static String getPurpleBackground(String text) {
        return ConsoleColors.PURPLE_BACKGROUND + text + ConsoleColors.RESET;
    }

    public static String getWhite(String text) {
        return ConsoleColors.WHITE + text + ConsoleColors.RESET;
    }

    public static String getWhiteBold(String text) {
        return ConsoleColors.WHITE_BOLD + text + ConsoleColors.RESET;
    }

    public static String getWhiteBoldBright(String text) {
        return ConsoleColors.WHITE_BOLD_BRIGHT + text + ConsoleColors.RESET;
    }

    public static String getWhiteUnderlined(String text) {
        return ConsoleColors.WHITE_UNDERLINED + text + ConsoleColors.RESET;
    }

    public static String getWhiteBackground(String message) {
        return ConsoleColors.WHITE_BACKGROUND + message + ConsoleColors.RESET;
    }

}
