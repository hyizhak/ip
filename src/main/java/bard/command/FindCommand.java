package bard.command;

import bard.storage.Storage;
import bard.task.TaskList;
import bard.ui.Ui;

public class FindCommand extends Command {
    private String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    public String execute(TaskList tasks, Ui ui, Storage storage) {
        TaskList matchingTasks = tasks.findTasks(keyword);
        return "Here are the matching tasks in your list:\n" + matchingTasks.listTasks();
    }
}
