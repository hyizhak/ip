public class MarkCommand extends Command {
    private int index;
    private boolean isMarkedDone;

    public MarkCommand(int index, boolean isMarkedDone) {
        this.index = index;
        this.isMarkedDone = isMarkedDone;
    }

    public String execute(TaskList tasks, Ui ui, Storage storage) throws BardException {
        Task task;
        String response;
        if (isMarkedDone) {
            task = tasks.markTaskAsDone(index);
            response = " Nice! I've marked this task as done:\n";
        } else {
            task = tasks.unmarkTaskAsDone(index);
            response = " Okay, I've unmarked this task as done:\n";
        }
        storage.save(tasks);
        return response + "   " + task + "\n";
    }
}
