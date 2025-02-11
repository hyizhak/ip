package bard.command;

import bard.exception.BardException;
import bard.storage.Storage;
import bard.task.Task;
import bard.task.TaskList;
import bard.ui.Ui;

public class AddCommand extends Command {
    private Task task;

    /**
     * Constructor for AddCommand.
     *
     * @param task Task to be added.
     */
    public AddCommand(Task task) {
        this.task = task;
    }

    /**
     * Executes the AddCommand.
     *
     * @param tasks TaskList containing all tasks.
     * @param ui Ui object to interact with user.
     * @param storage Storage object to save tasks.
     * @return String response to be displayed to user.
     * @throws BardException If an error occurs during execution.
     */
    public String execute(TaskList tasks, Ui ui, Storage storage) throws BardException {
        tasks.addTask(task);
        storage.save(task);
        return " Got it. I've added this task:\n"
                + "   " + task + "\n"
                + " Now you have " + tasks.getSize() + " tasks in the list.\n";
    }
}
