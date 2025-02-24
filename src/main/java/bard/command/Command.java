package bard.command;

import bard.exception.BardException;
import bard.storage.Storage;
import bard.task.TaskList;
import bard.ui.TextUi;

/**
 * Represents a command to be executed
 */
public abstract class Command {

    /**
     * Executes the command.
     *
     * @param tasks TaskList containing all tasks.
     * @param ui TextUi object to interact with user.
     * @param storage Storage object to save tasks.
     * @return String response to be displayed to user.
     * @throws BardException If an error occurs during execution.
     */
    public abstract String execute(TaskList tasks, TextUi ui, Storage storage) throws BardException;
}
