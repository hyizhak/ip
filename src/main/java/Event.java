import java.time.LocalDateTime;

public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public Event(String description, LocalDateTime from, LocalDateTime to, boolean isDone) {
        super(description, isDone);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(DateParser.OUTPUT_HOUR_FORMAT)
                + " to: " + to.format(DateParser.OUTPUT_HOUR_FORMAT) + ")";
    }

    public String toFileString() {
        return "E | " + super.toFileString() + " | " + from.format(DateParser.INPUT_HOUR_FORMAT)
                + " - " + to.format(DateParser.INPUT_HOUR_FORMAT);
    }
}
