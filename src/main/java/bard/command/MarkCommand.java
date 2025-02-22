package bard.command;

import bard.exception.BardException;
import bard.storage.Storage;
import bard.task.Task;
import bard.task.TaskList;
import bard.ui.TextUi;

/**
 * Represents a command to mark or unmark a task as done.
 */
public class MarkCommand extends Command {
    private int index;
    private boolean isMarkedDone;

    /**
     * Constructor for MarkCommand.
     *
     * @param index Index of task to be marked as done.
     * @param isMarkedDone Boolean indicating if task is marked as done.
     */
    public MarkCommand(int index, boolean isMarkedDone) {
        this.index = index;
        this.isMarkedDone = isMarkedDone;
    }

    /**
     * Executes the MarkCommand.
     *
     * @param tasks TaskList containing all tasks.
     * @param ui TextUi object to interact with user.
     * @param storage Storage object to save tasks.
     * @return String response to be displayed to user.
     * @throws BardException If an error occurs during execution.
     */
    public String execute(TaskList tasks, TextUi ui, Storage storage) throws BardException {
        Task task;
        String response;
        if (isMarkedDone) {
            task = tasks.markTaskAsDone(index);
            response = " Nice! I've marked this task as done:\n";
        } else {
            task = tasks.unmarkTaskAsDone(index);
            response = " Okay, I've unmarked this task as done:\n";
        }
        storage.save(tasks);
        return response + "   " + task + "\n";
    }
}
