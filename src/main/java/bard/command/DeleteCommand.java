package bard.command;

import bard.exception.BardException;
import bard.storage.Storage;
import bard.task.Task;
import bard.task.TaskList;
import bard.ui.Ui;

public class DeleteCommand extends Command {
    private int index;

    /**
     * Constructor for DeleteCommand.
     *
     * @param index Index of task to be deleted.
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the DeleteCommand.
     *
     * @param tasks TaskList containing all tasks.
     * @param ui Ui object to interact with user.
     * @param storage Storage object to save tasks.
     * @return String response to be displayed to user.
     * @throws BardException If an error occurs during execution.
     */
    public String execute(TaskList tasks, Ui ui, Storage storage) throws BardException {
        Task task = tasks.deleteTask(index);
        storage.save(tasks);
        return " Noted. I've removed this task:\n" + "   " + task + "\n" + " Now you have "
                + tasks.getSize() + " tasks in the list.\n";
    }
}
