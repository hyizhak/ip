package bard.command;

import bard.storage.Storage;
import bard.task.TaskList;
import bard.ui.Ui;

public class ExitCommand extends Command {
    public ExitCommand() {}

    public String execute(TaskList tasks, Ui ui, Storage storage) {
        ui.setExited();
        return "Bye. Hope to see you again soon!\n";
    }
}
