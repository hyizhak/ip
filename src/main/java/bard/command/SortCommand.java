package bard.command;

import bard.storage.Storage;
import bard.task.TaskList;
import bard.ui.TextUi;

/**
 * Represents a command to sort all tasks.
 */
public class SortCommand extends Command {
    public SortCommand() {}

    /**
     * Sorts all tasks in the task list.
     *
     * @param tasks TaskList containing all tasks.
     * @param ui TextUi object to interact with user.
     * @param storage Storage object to save tasks.
     * @return String response to be displayed to user.
     */
    public String execute(TaskList tasks, TextUi ui, Storage storage) {
        tasks.sortTasks();
        return " Here are the tasks in your list, sorted by deadline:\n" + tasks.listTasks();
    }
}
