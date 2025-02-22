package bard.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

/**
 * JUnit tests for the Task class and its comparison behavior.
 */
public class TaskTest {

    @Test
    public void testMarkAndUnmark() {
        Task task = new Task("Write tests");
        assertEquals("[ ] Write tests", task.toString(), "Task should initially be unmarked");

        task.markAsDone();
        assertEquals("[X] Write tests", task.toString(),
                "Task should be marked as done after markAsDone()");

        task.unmarkAsDone();
        assertEquals("[ ] Write tests", task.toString(),
                "Task should be unmarked after unmarkAsDone()");
    }

    @Test
    public void testToFileString() {
        Task task = new Task("Write tests");
        assertEquals("0 | Write tests", task.toFileString(),
                "toFileString should indicate task is not done");

        task.markAsDone();
        assertEquals("1 | Write tests", task.toFileString(),
                "toFileString should indicate task is done");
    }

    @Test
    public void testCompareBasicTasks() {
        Task task1 = new Task("Task1");
        Task task2 = new Task("Task2");
        // Both tasks are basic (no time info) and incomplete; they compare as equal.
        assertEquals(0, task1.compareTo(task2),
                "Basic tasks without time should be equal in priority");
    }

    @Test
    public void testCompareDoneVsNotDone() {
        Task task1 = new Task("Task1");
        Task task2 = new Task("Task2");
        task2.markAsDone();
        // Incomplete tasks should come before completed tasks.
        assertTrue(task1.compareTo(task2) < 0,
                "Incomplete task should have higher priority than a done task");
        assertTrue(task2.compareTo(task1) > 0,
                "Done task should have lower priority than an incomplete task");
    }

    @Test
    public void testCompareDeadlineTasks() {
        LocalDateTime time1 = LocalDateTime.of(2025, 12, 31, 10, 0);
        LocalDateTime time2 = LocalDateTime.of(2025, 12, 31, 12, 0);
        Deadline deadline1 = new Deadline("Deadline1", time1);
        Deadline deadline2 = new Deadline("Deadline2", time2);
        // Deadline with an earlier 'by' time should be prioritized.
        assertTrue(deadline1.compareTo(deadline2) < 0,
                "Deadline with an earlier time should have higher priority");
        assertTrue(deadline2.compareTo(deadline1) > 0,
                "Deadline with a later time should have lower priority");
    }

    @Test
    public void testCompareEventTasks() {
        LocalDateTime from1 = LocalDateTime.of(2025, 12, 31, 9, 0);
        LocalDateTime from2 = LocalDateTime.of(2025, 12, 31, 11, 0);
        Event event1 = new Event("Event1", from1, LocalDateTime.of(2025, 12, 31, 10, 0));
        Event event2 = new Event("Event2", from2, LocalDateTime.of(2025, 12, 31, 12, 0));
        // Event with an earlier 'from' time should be prioritized.
        assertTrue(event1.compareTo(event2) < 0,
                "Event with an earlier start time should have higher priority");
        assertTrue(event2.compareTo(event1) > 0,
                "Event with a later start time should have lower priority");
    }

    @Test
    public void testCompareDeadlineVsBasicTask() {
        LocalDateTime deadlineTime = LocalDateTime.of(2025, 12, 31, 10, 0);
        Deadline deadline = new Deadline("Deadline", deadlineTime);
        Task basicTask = new Task("Basic");
        // According to our logic, a task with a deadline (i.e. with time info) is prioritized over
        // a basic task without time.
        assertTrue(deadline.compareTo(basicTask) < 0,
                "Task with deadline should be prioritized over basic task");
        assertTrue(basicTask.compareTo(deadline) > 0,
                "Basic task should be deprioritized compared to task with deadline");
    }
}
