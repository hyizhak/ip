public class DeleteCommand extends Command{
    private int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    public String execute(TaskList tasks, Ui ui, Storage storage) throws BardException {
        Task task = tasks.deleteTask(index);
        storage.save(tasks);
        return " Noted. I've removed this task:\n"
                + "   " + task + "\n"
                + " Now you have " + tasks.getSize() + " tasks in the list.\n";
    }
}
