import java.time.LocalDate;

public class Deadline extends Task {
    protected LocalDate by;

    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    public Deadline(String description, LocalDate by, boolean isDone) {
        super(description, isDone);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DateParser.OUTPUT_DAY_FORMAT) + ")";
    }

    public String toFileString() {
        return "D | " + super.toFileString() + " | " + by.format(DateParser.INPUT_DAY_FORMAT);
    }
}
