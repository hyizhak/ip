package bard.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import bard.exception.BardException;
import bard.parser.DateParser;
import bard.task.Deadline;
import bard.task.Event;
import bard.task.Task;
import bard.task.TaskList;
import bard.task.Todo;

/** Handles loading and saving of tasks to a file */
public class Storage {
    private static final String FILE_PATH = Path.of("data", "tasks.txt").toString();

    public Storage() {
        createFileIfNotExists();
    }

    private void createFileIfNotExists() {
        File file = new File(FILE_PATH);
        // System.out.println("Saving storage file to: " + file.getAbsolutePath());
        File parentDir = file.getParentFile();

        try {
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs(); // Create parent directory if it doesn't exist
            }
            if (!file.exists()) {
                file.createNewFile(); // Create file if it doesn't exist
            }
        } catch (IOException e) {
            System.out.println("Error creating file: " + e.getMessage());
        }
    }

    /** Loads tasks from the file into memory */
    public TaskList load() throws BardException {
        ArrayList<Task> tasks = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                Task task = parseTask(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            throw new BardException("Error reading from file: " + e.getMessage());
        }
        return new TaskList(tasks);
    }

    /** Saves all tasks to the file */
    public void save(TaskList tasks) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Task task : tasks) {
                bw.write(task.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    /** Saves a single task to the file */
    public void save(Task task) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(task.toFileString());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    /** Parses a line from the file into a bard.task.Task object */
    private Task parseTask(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            return null;
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        switch (type) {
        case "T":
            return new Todo(description, isDone);
        case "D":
            return new Deadline(description, DateParser.parseHourDate(parts[3]), isDone);
        case "E":
            String[] eventParts = parts[3].split(" - ");
            return new Event(description, DateParser.parseHourDate(eventParts[0]),
                    DateParser.parseHourDate(eventParts[1]), isDone);
        default:
            return null;
        }
    }
}
