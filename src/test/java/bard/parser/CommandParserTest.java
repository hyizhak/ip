package bard.parser;

import bard.command.*;
import bard.exception.BardException;
import bard.task.Deadline;
import bard.task.Event;
import bard.task.Task;
import bard.task.Todo;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for the CommandParser class.
 */
public class CommandParserTest {

    private Field getFieldRecursively(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {
                // Move to the superclass
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchFieldException("Field '" + fieldName + "' not found in class hierarchy.");
    }

    /**
     * Helper method to retrieve a private field's value via reflection.
     *
     * @param instance  The object instance from which to extract the field.
     * @param fieldName The name of the private field.
     * @param fieldClass The expected type of the field.
     * @param <T>       The type parameter.
     * @return The value of the private field.
     * @throws Exception if reflection fails.
     */
    private <T> T getPrivateField(Object instance, String fieldName, Class<T> fieldClass) throws Exception {
        Field field = getFieldRecursively(instance.getClass(), fieldName);
        field.setAccessible(true);
        return fieldClass.cast(field.get(instance));
    }

    @Test
    public void testParseBye() throws BardException {
        Command command = CommandParser.parse("bye");
        assertTrue(command instanceof ExitCommand, "Command 'bye' should return an instance of ExitCommand.");
    }

    @Test
    public void testParseList() throws BardException {
        Command command = CommandParser.parse("list");
        assertTrue(command instanceof ListCommand, "Command 'list' should return an instance of ListCommand.");
    }

    @Test
    public void testParseDelete() throws BardException, Exception {
        Command command = CommandParser.parse("delete 3");
        assertTrue(command instanceof DeleteCommand, "Command 'delete' should return an instance of DeleteCommand.");
        // Use reflection to check the internal index value.
        int index = getPrivateField(command, "index", Integer.class);
        assertEquals(3, index, "DeleteCommand should store index 3.");
    }

    @Test
    public void testParseTodo() throws BardException, Exception {
        String taskDescription = "read book";
        Command command = CommandParser.parse("todo " + taskDescription);
        assertTrue(command instanceof AddCommand, "Command 'todo' should return an instance of AddCommand.");
        // Retrieve the encapsulated task from the AddCommand.
        Task task = getPrivateField(command, "task", Task.class);
        assertTrue(task instanceof Todo, "Task for a 'todo' command should be an instance of Todo.");
        // Check the task description.
        String desc = getPrivateField(task, "description", String.class);
        assertEquals(taskDescription, desc, "Todo task description should match the input.");
    }

    @Test
    public void testParseDeadline() throws BardException, Exception {
        String description = "submit report";
        String deadlineString = "2025-12-31 2359";
        // Parse the deadline command.
        Command command = CommandParser.parse("deadline " + description + " /by " + deadlineString);
        assertTrue(command instanceof AddCommand, "Command 'deadline' should return an instance of AddCommand.");
        Task task = getPrivateField(command, "task", Task.class);
        assertTrue(task instanceof Deadline, "Task for a 'deadline' command should be an instance of Deadline.");
        // Verify the task description.
        String taskDesc = getPrivateField(task, "description", String.class);
        assertEquals(description, taskDesc, "Deadline task description should match the input.");
        // Verify the parsed deadline.
        LocalDateTime by = getPrivateField(task, "by", LocalDateTime.class);
        LocalDateTime expectedBy = DateParser.parseHourDate(deadlineString);
        assertEquals(expectedBy, by, "Deadline time should match the expected parsed value.");
    }

    @Test
    public void testParseEvent() throws BardException, Exception {
        String description = "project meeting";
        String fromString = "2025-12-31 1000";
        String toString = "2025-12-31 1200";
        Command command = CommandParser.parse("event " + description + " /from " + fromString + " /to " + toString);
        assertTrue(command instanceof AddCommand, "Command 'event' should return an instance of AddCommand.");
        Task task = getPrivateField(command, "task", Task.class);
        assertTrue(task instanceof Event, "Task for an 'event' command should be an instance of Event.");
        // Check event description.
        String taskDesc = getPrivateField(task, "description", String.class);
        assertEquals(description, taskDesc, "Event task description should match the input.");
        // Verify the event's from and to times.
        LocalDateTime from = getPrivateField(task, "from", LocalDateTime.class);
        LocalDateTime to = getPrivateField(task, "to", LocalDateTime.class);
        LocalDateTime expectedFrom = DateParser.parseHourDate(fromString);
        LocalDateTime expectedTo = DateParser.parseHourDate(toString);
        assertEquals(expectedFrom, from, "Event start time should match the expected parsed value.");
        assertEquals(expectedTo, to, "Event end time should match the expected parsed value.");
    }

    @Test
    public void testParseMark() throws BardException, Exception {
        Command command = CommandParser.parse("mark 2");
        assertTrue(command instanceof MarkCommand, "Command 'mark' should return an instance of MarkCommand.");
        int index = getPrivateField(command, "index", Integer.class);
        boolean isMarkedDone = getPrivateField(command, "isMarkedDone", Boolean.class);
        assertEquals(2, index, "MarkCommand should store index 2.");
        assertTrue(isMarkedDone, "MarkCommand should indicate marking (true) for 'mark' command.");
    }

    @Test
    public void testParseUnmark() throws BardException, Exception {
        Command command = CommandParser.parse("unmark 2");
        assertTrue(command instanceof MarkCommand, "Command 'unmark' should return an instance of MarkCommand.");
        int index = getPrivateField(command, "index", Integer.class);
        boolean isMarkedDone = getPrivateField(command, "isMarkedDone", Boolean.class);
        assertEquals(2, index, "MarkCommand should store index 2.");
        assertFalse(isMarkedDone, "MarkCommand should indicate unmarking (false) for 'unmark' command.");
    }

    @Test
    public void testParseInvalidCommand() throws BardException {
        Command command = CommandParser.parse("unknown command");
        assertTrue(command instanceof InvalidCommand, "An unrecognized command should return an instance of InvalidCommand.");
    }

    @Test
    public void testTodoWithoutDescriptionThrowsException() {
        BardException exception = assertThrows(BardException.class, () -> CommandParser.parse("todo"));
        assertEquals("Error: 'todo' requires a task description.", exception.getMessage(),
                "A 'todo' command without a description should throw the correct exception.");
    }

    @Test
    public void testDeadlineWithoutProperFormatThrowsException() {
        BardException exception = assertThrows(BardException.class, () -> CommandParser.parse("deadline submit report"));
        assertEquals("Error: 'deadline' requires a task description and a deadline.", exception.getMessage(),
                "A 'deadline' command without proper '/by' format should throw the correct exception.");
    }

    @Test
    public void testEventWithoutProperFormatThrowsException() {
        BardException exception = assertThrows(BardException.class, () ->
                CommandParser.parse("event meeting /from 2025-12-31T10:00"));
        assertEquals("Error: 'event' requires a task description and a time range.", exception.getMessage(),
                "An 'event' command without proper '/from' and '/to' segments should throw the correct exception.");
    }
}