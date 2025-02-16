package bard.command;

import bard.storage.Storage;
import bard.task.TaskList;
import bard.ui.TextUi;

public class ListCommand extends Command {
    public ListCommand() {}

    /**
     * Lists all tasks in the task list.
     *
     * @param tasks TaskList containing all tasks.
     * @param ui TextUi object to interact with user.
     * @param storage Storage object to save tasks.
     * @return String response to be displayed to user.
     */
    public String execute(TaskList tasks, TextUi ui, Storage storage) {
        return tasks.listTasks();
    }
}
