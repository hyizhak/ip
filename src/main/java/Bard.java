import java.util.Scanner;

public class Bard {

    static String horizontalLine = "____________________________________________________________\n";

    static String startingLine = horizontalLine +
            " Hello! I'm Bard\n" +
            " What can I do for you?\n" +
            horizontalLine;
    static String endingLine = horizontalLine +
            " Bye. Hope to see you again soon!\n" +
            horizontalLine;
    private String[] tasks;

    public Bard() {
        tasks = new String[100];
    }

    private void addTask(String task) {
        for (int i = 0; i < tasks.length; i++) {
            if (tasks[i] == null) {
                tasks[i] = task;
                break;
            }
        }
        System.out.println(horizontalLine + " Added: " + task + "\n" + horizontalLine);
    }

    private String listTasks() {
        StringBuilder taskList = new StringBuilder();
        for (int i = 0; i < tasks.length; i++) {
            if (tasks[i] == null) {
                break;
            }
            taskList.append((i + 1)).append(". ").append(tasks[i]).append("\n");
        }
        return taskList.toString();
    }

    public void run() {
        System.out.println(startingLine);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                System.out.println(endingLine);
                break;
            } else if (input.equals("list")) {
                System.out.println(horizontalLine + " Here are the tasks in your list:\n" + listTasks() + horizontalLine);
            } else {
                addTask(input);
            }
        }
    }

    public static void main(String[] args) {
        Bard bard = new Bard();
        bard.run();
    }
}
