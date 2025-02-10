package bard.ui;

import java.util.Scanner;

public class Ui {
    public static String horizontalLine = "____________________________________________________________\n";

    static String startingLine = horizontalLine +
            " Hello! I'm Bard.\n" +
            " What can I do for you?\n" +
            horizontalLine;

    private boolean hasExited = false;

    private final Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
        System.out.println(startingLine);
    }

    public void showLine() {
        System.out.println(horizontalLine);
    }

    public void response(String message){
        System.out.println(horizontalLine + message + horizontalLine);
    }

    public void showErrorMessage(String message) {
        System.out.println("Error: " + message);
    }

    public void showLoadingError() {
        System.out.println("Error: Unable to load tasks from file.");
    }

    public void setExited() {
        hasExited = true;
    }

    public boolean hasExited() {
        return hasExited;
    }

    public String readCommand() {
        return scanner.nextLine();
    }
}
