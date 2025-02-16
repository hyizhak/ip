package bard.command;

import bard.storage.Storage;
import bard.task.TaskList;
import bard.ui.TextUi;

public class ExitCommand extends Command {
    public ExitCommand() {}

    public String execute(TaskList tasks, TextUi ui, Storage storage) {
        ui.setExited();
        return "Bye. Hope to see you again soon!\n";
    }
}
