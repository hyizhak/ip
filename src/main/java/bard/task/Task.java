package bard.task;

import java.time.LocalDateTime;

/**
 * Represents a task with a description and a status of whether it is done.
 */
public class Task {
    private String description;
    private boolean isDone;

    /**
     * Constructor for Task.
     *
     * @param description Description of task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Constructor for Task.
     *
     * @param description Description of task.
     * @param isDone Whether the task is done.
     */
    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void unmarkAsDone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return (isDone ? "[X] " : "[ ] ") + description;
    }

    /**
     * Returns the description of the task used in storage.
     *
     * @return Description of the task in storage format.
     */
    public String toFileString() {
        return (isDone ? "1" : "0") + " | " + description;
    }

    /**
     * Compares this task with another task based on priority.
     *
     * @param task2 Task to compare with.
     * @return Integer representing the comparison. Tasks with a closer deadline are considered
     *         higher priority.
     */
    public int compareTo(Task task2) {
        // Incomplete tasks are prioritized over completed ones.
        if (this.isDone != task2.isDone) {
            return this.isDone ? 1 : -1;
        }

        // Extract the time value from each task, if available.
        LocalDateTime thisTime = null;
        if (this instanceof Deadline) {
            thisTime = ((Deadline) this).by;
        } else if (this instanceof Event) {
            thisTime = ((Event) this).from;
        }

        LocalDateTime otherTime = null;
        if (task2 instanceof Deadline) {
            otherTime = ((Deadline) task2).by;
        } else if (task2 instanceof Event) {
            otherTime = ((Event) task2).from;
        }

        // If both tasks have a time, compare them.
        if (thisTime != null && otherTime != null) {
            return thisTime.compareTo(otherTime);
        }
        // If only one task has a time, it is prioritized.
        if (thisTime != null) {
            return -1;
        }
        if (otherTime != null) {
            return 1;
        }
        // If neither task has a time, they are considered equal.
        return 0;
    }
}
