package bard.command;

import bard.storage.Storage;
import bard.task.TaskList;
import bard.ui.TextUi;

public class InvalidCommand extends Command {
    public InvalidCommand() {}

    public String execute(TaskList tasks, TextUi ui, Storage storage) {
        return "I'm sorry, but I don't know what that means :-(\n";
    }
}
