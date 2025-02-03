public class Event extends Task {
    protected String from;
    protected String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public Event(String description, String from, String to, boolean isDone) {
        super(description, isDone);
        this.from = from;
        this.to = to;
    }

    public Event(String description, String fromTo, boolean isDone) {
        super(description, isDone);
        String[] fromToSplit = fromTo.split("-");
        this.from = fromToSplit[0];
        this.to = fromToSplit[1];
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }

    public String toFileString() {
        return "E | " + super.toFileString() + " | " + from + "-" + to;
    }
}
