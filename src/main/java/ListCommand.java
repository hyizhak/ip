public class ListCommand extends Command {
    public ListCommand() {
    }

    public String execute(TaskList tasks, Ui ui, Storage storage) {
        return tasks.listTasks();
    }
}
