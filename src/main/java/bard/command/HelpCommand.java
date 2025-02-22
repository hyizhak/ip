package bard.command;

import bard.exception.BardException;
import bard.storage.Storage;
import bard.task.TaskList;
import bard.ui.TextUi;

/**
 * Represents a command to display help information.
 */
public class HelpCommand extends Command {
    /**
     * Creates a new HelpCommand.
     */
    public HelpCommand() {}

    /**
     * Displays help information.
     *
     * @param tasks TaskList containing all tasks.
     * @param ui TextUi object to interact with user.
     * @param storage Storage object to save tasks.
     * @return String response to be displayed to user.
     * @throws BardException If an error occurs during execution.
     */
    public String execute(TaskList tasks, TextUi ui, Storage storage) throws BardException {
        return "Here are the commands you can use:\n"
                + "1. todo <description> - Adds a todo task.\n"
                + "2. deadline <description> /by <date> - Adds a deadline task.\n"
                + "3. event <description> /at <date> - Adds an event task.\n"
                + "4. list - Lists all tasks.\n"
                + "5. (un)mark <task number> - Marks a task as (not) done.\n"
                + "6. delete <task number> - Deletes a task.\n"
                + "7. find <keyword> - Finds tasks with the keyword.\n"
                + "8. sort - Sorts all tasks by deadline.\n" + "9. bye - Exits the program.\n"
                + "For more information, visit: \n";
    }
}
