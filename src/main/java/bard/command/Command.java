package bard.command;

import bard.exception.BardException;
import bard.storage.Storage;
import bard.task.TaskList;
import bard.ui.Ui;

public abstract class Command {
    public abstract String execute(TaskList tasks, Ui ui, Storage storage) throws BardException;
}
