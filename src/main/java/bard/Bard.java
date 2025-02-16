package bard;

import bard.command.Command;
import bard.exception.BardException;
import bard.parser.CommandParser;
import bard.storage.Storage;
import bard.task.TaskList;
import bard.ui.TextUi;

public class Bard {

    private Storage storage;
    private TaskList tasks;
    private TextUi ui;

    public Bard() {
        storage = new Storage();
        ui = new TextUi();
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

    public String getResponse(String fullCommand) {
        try {
            Command c = CommandParser.parse(fullCommand);
            return c.execute(tasks, ui, storage);
        } catch (BardException e) {
            return e.getMessage();
        }
    }

    public static void main(String[] args) {
        Bard bard = new Bard();
        bard.run();
    }
}
