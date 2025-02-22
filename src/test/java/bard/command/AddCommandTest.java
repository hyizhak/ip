package bard.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import bard.exception.BardException;
import bard.storage.Storage;
import bard.task.Task;
import bard.task.TaskList;
import bard.ui.TextUi;

/** JUnit test class for AddCommand. */
public class AddCommandTest {

    /** Dummy Task implementation for testing. */
    static class DummyTask extends Task {
        public DummyTask(String description) {
            super(description);
        }
    }

    /**
     * Dummy TaskList implementation for testing. This implementation records tasks in an ArrayList.
     */
    static class DummyTaskList extends TaskList {
        private final List<Task> tasks = new ArrayList<>();

        @Override
        public void addTask(Task task) {
            tasks.add(task);
        }

        @Override
        public int getSize() {
            return tasks.size();
        }

        // Optional helper to retrieve the task list.
        public List<Task> getTasks() {
            return tasks;
        }
    }

    /**
     * Dummy TextUi implementation. In this test, the TextUi is not used, so we provide an empty
     * implementation.
     */
    static class DummyUi extends TextUi {
        // Implement any abstract methods if necessary.
    }

    /**
     * Dummy Storage implementation for testing. This implementation records the tasks passed to
     * save().
     */
    static class DummyStorage extends Storage {
        private final List<Task> savedTasks = new ArrayList<>();

        @Override
        public void save(Task task) {
            savedTasks.add(task);
        }

        // Helper method to verify which tasks were saved.
        public List<Task> getSavedTasks() {
            return savedTasks;
        }
    }

    /**
     * Tests that executing an AddCommand - adds the task to the TaskList, - calls
     * storage.save(task), and - returns the correct message.
     */
    @Test
    public void testExecuteAddsTaskAndSavesIt() throws BardException {
        // Create a dummy task.
        DummyTask task = new DummyTask("Test Task");

        // Create the AddCommand with the dummy task.
        AddCommand addCommand = new AddCommand(task);

        // Create dummy instances for TaskList, TextUi, and Storage.
        DummyTaskList taskList = new DummyTaskList();
        DummyUi ui = new DummyUi();
        DummyStorage storage = new DummyStorage();

        // Execute the command.
        String result = addCommand.execute(taskList, ui, storage);

        // Verify that the task was added to the TaskList.
        assertEquals(1, taskList.getSize(),
                "TaskList should contain 1 task after executing AddCommand.");

        // Verify that Storage.save(task) was called.
        assertEquals(1, storage.getSavedTasks().size(), "Storage should have saved 1 task.");
        assertEquals(task, storage.getSavedTasks().get(0),
                "The saved task should be the same as the added task.");

        // Construct the expected output message.
        String expectedMessage = " Got it. I've added this task:\n" + "   " + task + "\n"
                + " Now you have " + taskList.getSize() + " tasks in the list.\n";

        // Verify that the returned message is exactly as expected.
        assertEquals(expectedMessage, result,
                "The output message should match the expected message.");
    }
}
