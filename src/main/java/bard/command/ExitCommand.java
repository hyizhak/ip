package bard.command;

import bard.storage.Storage;
import bard.task.TaskList;
import bard.ui.TextUi;

/**
 * Represents a command to exit the program.
 */
public class ExitCommand extends Command {
    public ExitCommand() {}

    /**
     * Exits the program.
     *
     * @param tasks TaskList containing all tasks.
     * @param ui TextUi object to interact with user.
     * @param storage Storage object to save tasks.
     * @return String response to be displayed to user.
     */
    public String execute(TaskList tasks, TextUi ui, Storage storage) {
        ui.setExited();
        return "Bye. Hope to see you again soon!\n";
    }
}
