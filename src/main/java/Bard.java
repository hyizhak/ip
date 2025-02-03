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
    private Task[] tasks;

    public Bard() {
        tasks = new Task[100];
    }

    private void addTask(String task) {
        for (int i = 0; i < tasks.length; i++) {
            if (tasks[i] == null) {
                tasks[i] = new Task(task);
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

    private void markTaskAsDone(int taskNumber) {
        if (taskNumber < 1 || taskNumber > tasks.length || tasks[taskNumber - 1] == null) {
            System.out.println(horizontalLine + " Invalid task number\n" + horizontalLine);
            return;
        }
        tasks[taskNumber - 1].markAsDone();
        System.out.println(horizontalLine + " Nice! I've marked this task as done:\n"
                + tasks[taskNumber - 1] + "\n" + horizontalLine);
    }

    private void unmarkTaskAsDone(int taskNumber) {
        if (taskNumber < 1 || taskNumber > tasks.length || tasks[taskNumber - 1] == null) {
            System.out.println(horizontalLine + " Invalid task number\n" + horizontalLine);
            return;
        }
        tasks[taskNumber - 1].unmarkAsDone();
        System.out.println(horizontalLine + " Okay, I've marked this task as not done yet:\n"
                + tasks[taskNumber - 1] + "\n" + horizontalLine);
    }

    public void run() {
        System.out.println(startingLine);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            String[] parts = input.split(" ");

            boolean wasHandled = false;
            switch (parts[0]) {
                case "mark":
                    if (parts.length != 2) {
                        break;
                    }
                    try {
                        markTaskAsDone(Integer.parseInt(parts[1]));
                        wasHandled = true;
                    } catch (NumberFormatException e) {
                        // Do nothing, will fall to add task
                    }
                    break;
                case "unmark":
                    if (parts.length != 2) {
                        break;
                    }
                    try {
                        unmarkTaskAsDone(Integer.parseInt(parts[1]));
                        wasHandled = true;
                    } catch (NumberFormatException e) {
                        // Do nothing, will fall to add task
                    }
                    break;
                case "list":
                    wasHandled = true;
                    System.out.println(horizontalLine + " Here are the tasks in your list:\n" + listTasks() + horizontalLine);
                    break;
                case "bye":
                    wasHandled = true;
                    System.out.println(endingLine);
                    return;
                default:
                    break;
            }

            if (!wasHandled) {
                addTask(input);
            }
        }
    }

    public static void main(String[] args) {
        Bard bard = new Bard();
        bard.run();
    }
}
