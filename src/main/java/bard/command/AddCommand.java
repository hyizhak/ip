package bard.command;

import bard.exception.BardException;
import bard.storage.Storage;
import bard.task.Task;
import bard.task.TaskList;
import bard.ui.Ui;

public class AddCommand extends Command {
    private Task task;

    public AddCommand(Task task) {
        this.task = task;
    }

    public String execute(TaskList tasks, Ui ui, Storage storage) throws BardException {
        tasks.addTask(task);
        storage.save(task);
        return " Got it. I've added this task:\n"
                + "   " + task + "\n"
                + " Now you have " + tasks.getSize() + " tasks in the list.\n";
    }
}
