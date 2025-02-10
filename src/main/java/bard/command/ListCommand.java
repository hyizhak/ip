package bard.command;

import bard.storage.Storage;
import bard.task.TaskList;
import bard.ui.Ui;

public class ListCommand extends Command {
    public ListCommand() {
    }

    public String execute(TaskList tasks, Ui ui, Storage storage) {
        return tasks.listTasks();
    }
}
