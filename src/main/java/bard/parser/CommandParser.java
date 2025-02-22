package bard.parser;

import bard.command.AddCommand;
import bard.command.Command;
import bard.command.DeleteCommand;
import bard.command.ExitCommand;
import bard.command.FindCommand;
import bard.command.HelpCommand;
import bard.command.InvalidCommand;
import bard.command.ListCommand;
import bard.command.MarkCommand;
import bard.command.SortCommand;
import bard.exception.BardException;
import bard.task.Deadline;
import bard.task.Event;
import bard.task.Task;
import bard.task.Todo;

/** Parses the full command string and returns the corresponding Command object. */
public class CommandParser {

    /**
     * Parses the full command and returns the corresponding Command object.
     *
     * @param fullCommand Full command string.
     * @return Command object corresponding to the command.
     * @throws BardException If an error occurs during parsing.
     */
    public static Command parse(String fullCommand) throws BardException {
        // spotless:off
        String[] words = fullCommand.split(" ");
        String command = words[0];
        return switch (command) {
        case "bye" -> new ExitCommand();
        case "list" -> new ListCommand();
        case "delete" -> new DeleteCommand(Integer.parseInt(words[1]));
        case "todo", "deadline", "event" -> new AddCommand(createTask(fullCommand));
        case "mark" -> new MarkCommand(Integer.parseInt(words[1]), true);
        case "unmark" -> new MarkCommand(Integer.parseInt(words[1]), false);
        case "find" -> new FindCommand(words[1]);
        case "sort" -> new SortCommand();
        case "help" -> new HelpCommand();
        default -> new InvalidCommand();
        };
        // spotless:on
    }

    /**
     * Creates a Task object from the full command.
     *
     * @param fullCommand Full command string.
     * @return Task object created from the command.
     * @throws BardException If an error occurs during parsing.
     */
    private static Task createTask(String fullCommand) throws BardException {
        // Split the command into the command word and its arguments.
        String[] parts = fullCommand.split(" ", 2);
        String command = parts[0].trim();
        assert parts.length > 0 : "Add-task-command should not be empty";

        switch (command) {
        case "todo":
            if (parts.length < 2 || parts[1].trim().isEmpty()) {
                throw new BardException("Error: 'todo' requires a task description.");
            }
            return new Todo(parts[1].trim());
        case "deadline":
            if (parts.length < 2) {
                throw new BardException(
                        "Error: 'deadline' requires a task description and a deadline.");
            }
            String[] deadlineParts = parts[1].split(" /by ", 2);
            if (deadlineParts.length < 2) {
                throw new BardException(
                        "Error: 'deadline' requires a task description and a deadline.");
            }
            return new Deadline(deadlineParts[0].trim(),
                    DateParser.parseHourDate(deadlineParts[1].trim()));
        case "event":
            if (parts.length < 2) {
                throw new BardException(
                        "Error: 'event' requires a task description and a time range.");
            }
            String[] eventParts = parts[1].split(" /from | /to ", 3);
            if (eventParts.length < 3) {
                throw new BardException(
                        "Error: 'event' requires a task description and a time range.");
            }
            return new Event(eventParts[0].trim(), DateParser.parseHourDate(eventParts[1].trim()),
                    DateParser.parseHourDate(eventParts[2].trim()));
        default:
            throw new BardException("Error: Unknown task command '" + command + "'.");
        }
    }
}
