package bard.task;

import java.time.LocalDateTime;

import bard.parser.DateParser;

/**
 * Represents a deadlined task.
 */
public class Deadline extends Task {
    protected LocalDateTime by;

    /**
     * Constructor for Deadline.
     *
     * @param description Description of deadline.
     * @param by Deadline of task.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /**
     * Constructor for Deadline.
     *
     * @param description Description of deadline.
     * @param by Deadline of task.
     * @param isDone Whether the deadline is done.
     */
    public Deadline(String description, LocalDateTime by, boolean isDone) {
        super(description, isDone);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DateParser.OUTPUT_HOUR_FORMAT) + ")";
    }

    @Override
    public String toFileString() {
        return "D | " + super.toFileString() + " | " + by.format(DateParser.INPUT_HOUR_FORMAT);
    }
}
