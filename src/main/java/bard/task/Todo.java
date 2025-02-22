package bard.task;

/**
 * Represents a todo task without any date/time attached to it.
 */
public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    public Todo(String description, boolean isDone) {
        super(description, isDone);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    public String toFileString() {
        return "T | " + super.toFileString();
    }
}
