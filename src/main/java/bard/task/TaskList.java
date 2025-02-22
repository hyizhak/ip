package bard.task;

import java.util.ArrayList;
import java.util.Iterator;

import bard.exception.BardException;

/**
 * Represents a list of tasks.
 */
public class TaskList implements Iterable<Task> {

    private ArrayList<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task getTask(int index) throws BardException {
        if (index < 0 || index >= tasks.size()) {
            throw new BardException("Error: bard.task.Task number out of range.");
        }
        return tasks.get(index);
    }

    public int getSize() {
        return tasks.size();
    }

    /**
     * Returns a list of tasks in the format of a string.
     *
     * @return String representation of the list of tasks.
     */
    public String listTasks() {
        StringBuilder taskList = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            taskList.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
        }
        return taskList.toString();
    }

    /**
     * Marks a task as done.
     *
     * @param taskNumber Task number to be marked as done.
     * @return Task that was marked as done.
     * @throws BardException
     */
    public Task markTaskAsDone(int taskNumber) throws BardException {
        if (taskNumber < 1 || taskNumber >= tasks.size() + 1) {
            throw new BardException("Error: bard.task.Task number out of range.");
        }
        tasks.get(taskNumber - 1).markAsDone();
        return tasks.get(taskNumber - 1);
    }

    /**
     * Unmarks a task as done.
     *
     * @param taskNumber Task number to be unmarked as done.
     * @return Task that was unmarked as done.
     * @throws BardException
     */
    public Task unmarkTaskAsDone(int taskNumber) throws BardException {
        if (taskNumber < 1 || taskNumber >= tasks.size() + 1) {
            throw new BardException("Error: bard.task.Task number out of range.");
        }
        tasks.get(taskNumber - 1).unmarkAsDone();
        return tasks.get(taskNumber - 1);
    }

    /**
     * Deletes a task.
     *
     * @param taskNumber Task number to be deleted.
     * @return Task that was deleted.
     * @throws BardException
     */
    public Task deleteTask(int taskNumber) throws BardException {
        if (taskNumber < 1 || taskNumber >= tasks.size() + 1) {
            throw new BardException("Error: bard.task.Task number out of range.");
        }
        Task task = tasks.remove(taskNumber - 1);
        return task;
    }

    /**
     * Finds tasks that contain a keyword.
     *
     * @param keyword Keyword to search for in tasks.
     * @return TaskList containing tasks that contain the keyword.
     */
    public TaskList findTasks(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.toString().contains(keyword)) {
                matchingTasks.add(task);
            }
        }
        return new TaskList(matchingTasks);
    }

    /**
     * Sorts tasks in the list chronologically based on the deadline.
     */
    public void sortTasks() {
        tasks.sort((task1, task2) -> task1.compareTo(task2));
    }

    @Override
    public Iterator<Task> iterator() {
        return tasks.iterator();
    }
}
