package bard.command;

import bard.storage.Storage;
import bard.task.TaskList;
import bard.ui.TextUi;

/**
 * Represents a command to find tasks by keyword.
 */
public class FindCommand extends Command {
    private String keyword;

    /**
     * Creates a new FindCommand with the given keyword.
     *
     * @param keyword Keyword to search for.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Finds tasks containing the keyword.
     *
     * @param tasks TaskList containing all tasks.
     * @param ui TextUi object to interact with user.
     * @param storage Storage object to save tasks.
     * @return String response to be displayed to user.
     */
    public String execute(TaskList tasks, TextUi ui, Storage storage) {
        TaskList matchingTasks = tasks.findTasks(keyword);
        return " Here are the matching tasks in your list:\n" + matchingTasks.listTasks();
    }
}
