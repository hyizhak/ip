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
    private int taskCount;

    public Bard() {
        tasks = new Task[100];
        taskCount = 0;
    }

    private void addTask(String taskString) throws BardException {
        String[] parts = taskString.split(" ", 2);
        String command = parts[0];

        Task task = null;

        if (command.equals("todo")) {
            if (parts.length < 2) {
                throw new BardException("Error: 'todo' requires a task description.");
            }
            task = new Todo(parts[1]);
        }
        else if (command.equals("deadline")) {
            String[] deadlineParts = parts.length > 1 ? parts[1].split(" /by ", 2) : new String[0];
            if (deadlineParts.length < 2) {
                throw new BardException("Error: 'deadline' requires a task description and a deadline.");
            }
            task = new Deadline(deadlineParts[0], deadlineParts[1]);
        }
        else if (command.equals("event")) {
            String[] eventParts = parts.length > 1 ? parts[1].split(" /from | /to ", 3) : new String[0];
            if (eventParts.length < 3) {
                throw new BardException("Error: 'event' requires a task description and a time range.");
            }
            task = new Event(eventParts[0], eventParts[1], eventParts[2]);
        }

        tasks[taskCount++] = task;

        System.out.println(horizontalLine + " Added: " + task + "\n"
                + " Now you have " + taskCount + " tasks in the list." + "\n" + horizontalLine);
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

    private void markTaskAsDone(int taskNumber) throws BardException {
        if (taskNumber < 1 || taskNumber > tasks.length || tasks[taskNumber - 1] == null) {
            throw new BardException("Error: Invalid task number");
        }
        tasks[taskNumber - 1].markAsDone();
        System.out.println(horizontalLine + " Nice! I've marked this task as done:\n"
                + tasks[taskNumber - 1] + "\n" + horizontalLine);
    }

    private void unmarkTaskAsDone(int taskNumber) throws BardException {
        if (taskNumber < 1 || taskNumber > tasks.length || tasks[taskNumber - 1] == null) {
            throw new BardException("Error: Invalid task number");
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
                    } catch (NumberFormatException | BardException e) {
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
                    } catch (NumberFormatException | BardException e) {
                        // Do nothing, will fall to add task
                    }
                    break;
                case "list":
                    wasHandled = true;
                    System.out.println(horizontalLine
                            + " Here are the tasks in your list:\n" + listTasks()
                            + horizontalLine);
                    break;
                case "todo":
                case "deadline":
                case "event":
                    wasHandled = true;
                    try {
                        addTask(input);
                    } catch (BardException e) {
                        System.out.println(e);
                    }
                    break;
                case "bye":
                    wasHandled = true;
                    System.out.println(endingLine);
                    return;
                default:
                    wasHandled = true;
                    System.out.println(horizontalLine
                            + " Sorry, I don't know what that means.\n" + horizontalLine);
                    break;
            }

            if (!wasHandled && !input.isBlank()) {
                System.out.println(horizontalLine
                        + " Sorry, I don't know what that means.\n" + horizontalLine);
            }
        }
    }

    public static void main(String[] args){
        Bard bard = new Bard();
        bard.run();
    }
}
