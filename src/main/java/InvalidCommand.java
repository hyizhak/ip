public class InvalidCommand extends Command {
    public InvalidCommand() {
    }

    public String execute(TaskList tasks, Ui ui, Storage storage) {
        return "I'm sorry, but I don't know what that means :-(\n";
    }
}
