public class Bard {
    public static void main(String[] args) {
        String horizontalLine =
                "____________________________________________________________\n";
        String startingLine = horizontalLine +
                " Hello! I'm Bard\n" +
                " What can I do for you?\n" +
                horizontalLine;
        String endingLine = horizontalLine +
                " Bye. Hope to see you again soon!\n" +
                horizontalLine;
        System.out.println(startingLine);

        // Read input from user
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        String input = scanner.nextLine();
        while (!input.equals("bye")) {
            System.out.println(horizontalLine + " " + input + "\n" + horizontalLine);
            input = scanner.nextLine();
        }

        System.out.println(endingLine);
    }
}
