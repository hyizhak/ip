package bard;

import bard.command.Command;
import bard.exception.BardException;
import bard.parser.CommandParser;
import bard.storage.Storage;
import bard.task.TaskList;
import bard.ui.Ui;

public class Bard {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Bard() {
        storage = new Storage();
        ui = new Ui();
        try {
            tasks = storage.load();
        } catch (BardException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /** Runs the Bard program. */
    public void run() {
        while (!ui.hasExited()) {
            String fullCommand = ui.readCommand();
            try {
                Command c = CommandParser.parse(fullCommand);
                String response = c.execute(tasks, ui, storage);
                ui.response(response);
            } catch (BardException e) {
                ui.showErrorMessage(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        Bard bard = new Bard();
        bard.run();
    }
}
