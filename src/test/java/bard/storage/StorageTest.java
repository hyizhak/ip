package bard.storage;

import static org.junit.jupiter.api.Assertions.*;

import bard.exception.BardException;
import bard.parser.DateParser;
import bard.task.Deadline;
import bard.task.Event;
import bard.task.Task;
import bard.task.TaskList;
import bard.task.Todo;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.*;

/**
 * JUnit test class for the Storage class.
 *
 * <p>
 * Note: This test uses date strings in the "yyyy-MM-dd HHmm" format.
 */
public class StorageTest {

    // The storage file is located at "data/tasks.txt" as per Storage.FILE_PATH.
    private final File storageFile = new File(Path.of("data", "tasks.txt").toString());

    @BeforeEach
    public void setUp() {
        // Ensure a clean slate: delete the file if it exists.
        if (storageFile.exists()) {
            storageFile.delete();
        }
    }

    @AfterEach
    public void tearDown() {
        // Clean up the storage file after each test.
        if (storageFile.exists()) {
            storageFile.delete();
        }
    }

    /** Test that the Storage constructor creates the file if it does not exist. */
    @Test
    public void testFileCreation() {
        // Ensure the file does not exist.
        if (storageFile.exists()) {
            storageFile.delete();
        }
        // Constructing Storage should create the file.
        new Storage();
        assertTrue(storageFile.exists(),
                "Storage constructor should create the file if it does not exist.");
    }

    /** Test that saving a single task appends the correct file string. */
    @Test
    public void testSaveTask() throws IOException {
        Storage storage = new Storage();
        Todo todo = new Todo("test todo", false);
        storage.save(todo);

        // Read the entire file.
        List<String> lines = Files.readAllLines(storageFile.toPath());
        assertEquals(1, lines.size(),
                "There should be one line in the storage file after saving a task.");
        assertEquals(todo.toFileString(), lines.get(0),
                "The file content should match the task's file string.");
    }

    /** Test that saving a TaskList writes all tasks correctly. */
    @Test
    public void testSaveTaskList() throws IOException {
        Storage storage = new Storage();

        // Create tasks using the "yyyy-MM-dd HHmm" date format.
        Todo todo = new Todo("test todo", false);
        Deadline deadline = new Deadline("submit assignment",
                DateParser.parseHourDate("2025-12-31 2359"), true);
        Event event = new Event("meeting", DateParser.parseHourDate("2025-12-31 1000"),
                DateParser.parseHourDate("2025-12-31 1200"), false);

        ArrayList<Task> taskArray = new ArrayList<>();
        taskArray.add(todo);
        taskArray.add(deadline);
        taskArray.add(event);
        TaskList taskList = new TaskList(taskArray);

        storage.save(taskList);
        List<String> lines = Files.readAllLines(storageFile.toPath());
        assertEquals(3, lines.size(),
                "There should be three lines in the storage file after saving a task list.");
        assertEquals(todo.toFileString(), lines.get(0), "First task's file string should match.");
        assertEquals(deadline.toFileString(), lines.get(1),
                "Second task's file string should match.");
        assertEquals(event.toFileString(), lines.get(2), "Third task's file string should match.");
    }

    /**
     * Test that loading tasks from a file returns a TaskList with the correct tasks.
     *
     * <p>
     * This test writes known task strings into the file and then uses the load() method to verify
     * that the reconstructed tasks (via parseTask) have matching file string representations.
     */
    @Test
    public void testLoad() throws BardException, IOException, ReflectiveOperationException {
        // Prepare known file content (one Todo, one Deadline, and one Event) using the proper date
        // format.
        List<String> lines =
                List.of("T | 0 | test todo", "D | 1 | submit assignment | 2025-12-31 2359",
                        "E | 0 | meeting | 2025-12-31 1000 - 2025-12-31 1200");
        Files.write(storageFile.toPath(), lines);

        Storage storage = new Storage();
        TaskList loadedTaskList = storage.load();
        ArrayList<Task> tasks = getTasksFromTaskList(loadedTaskList);

        assertEquals(3, tasks.size(), "Should load three tasks from the file.");

        // Create expected tasks using the same date format.
        Todo expectedTodo = new Todo("test todo", false);
        Deadline expectedDeadline = new Deadline("submit assignment",
                DateParser.parseHourDate("2025-12-31 2359"), true);
        Event expectedEvent = new Event("meeting", DateParser.parseHourDate("2025-12-31 1000"),
                DateParser.parseHourDate("2025-12-31 1200"), false);

        // Compare the tasks via their file string representations.
        assertEquals(expectedTodo.toFileString(), tasks.get(0).toFileString(),
                "Loaded Todo task does not match expected.");
        assertEquals(expectedDeadline.toFileString(), tasks.get(1).toFileString(),
                "Loaded Deadline task does not match expected.");
        assertEquals(expectedEvent.toFileString(), tasks.get(2).toFileString(),
                "Loaded Event task does not match expected.");
    }

    /**
     * Helper method to extract the private 'tasks' field from a TaskList instance using reflection.
     *
     * @param taskList the TaskList instance from which to retrieve the tasks
     * @return the ArrayList<Task> stored within the TaskList
     * @throws ReflectiveOperationException if reflection fails
     */
    @SuppressWarnings("unchecked")
    private ArrayList<Task> getTasksFromTaskList(TaskList taskList)
            throws ReflectiveOperationException {
        Field tasksField = TaskList.class.getDeclaredField("tasks");
        tasksField.setAccessible(true);
        return (ArrayList<Task>) tasksField.get(taskList);
    }
}
