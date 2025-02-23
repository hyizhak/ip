package bard.ui;

import java.util.Scanner;

/**
 * TextUi class handles the user interface of the application.
 */
public class TextUi {
    private static String horizontalLine =
            "____________________________________________________________\n";

    private static String startingLine =
            horizontalLine + " Hello! I'm Bard.\n" + " What can I do for you?\n" + horizontalLine;

    private boolean hasExited = false;

    private final Scanner scanner;

    /**
     * Constructor for TextUi class.
     */
    public TextUi() {
        scanner = new Scanner(System.in);
    }

    public static String getHorizontalLine() {
        return horizontalLine;
    }

    public void response(String message) {
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
