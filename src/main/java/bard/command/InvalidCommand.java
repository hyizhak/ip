package bard.command;

import bard.storage.Storage;
import bard.task.TaskList;
import bard.ui.TextUi;

/**
 * Represents an invalid command.
 */
public class InvalidCommand extends Command {
    public InvalidCommand() {}

    public String execute(TaskList tasks, TextUi ui, Storage storage) {
        return "Hi! What can I do for you today? If you need help, type 'help'.";
    }
}
