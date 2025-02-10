package bard.task;

import bard.parser.DateParser;

import java.time.LocalDateTime;

public class Deadline extends Task {
    protected LocalDateTime by;

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    public Deadline(String description, LocalDateTime by, boolean isDone) {
        super(description, isDone);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DateParser.OUTPUT_HOUR_FORMAT) + ")";
    }

    public String toFileString() {
        return "D | " + super.toFileString() + " | " + by.format(DateParser.INPUT_HOUR_FORMAT);
    }
}
