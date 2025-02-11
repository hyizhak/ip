package bard.task;

import java.util.ArrayList;
import java.util.Iterator;

import bard.exception.BardException;

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

    public String listTasks() {
        StringBuilder taskList = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            taskList.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
        }
        return taskList.toString();
    }

    public Task markTaskAsDone(int taskNumber) throws BardException {
        if (taskNumber < 1 || taskNumber >= tasks.size() + 1) {
            throw new BardException("Error: bard.task.Task number out of range.");
        }
        tasks.get(taskNumber - 1).markAsDone();
        return tasks.get(taskNumber - 1);
    }

    public Task unmarkTaskAsDone(int taskNumber) throws BardException {
        if (taskNumber < 1 || taskNumber >= tasks.size() + 1) {
            throw new BardException("Error: bard.task.Task number out of range.");
        }
        tasks.get(taskNumber - 1).unmarkAsDone();
        return tasks.get(taskNumber - 1);
    }

    public Task deleteTask(int taskNumber) throws BardException {
        if (taskNumber < 1 || taskNumber >= tasks.size() + 1) {
            throw new BardException("Error: bard.task.Task number out of range.");
        }
        Task task = tasks.remove(taskNumber - 1);
        return task;
    }

    @Override
    public Iterator<Task> iterator() {
        return tasks.iterator();
    }
}
