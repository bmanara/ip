import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Llama {
    public static void displayString(String str) {
        System.out.println("\t" + str);
    }

    public static void displayTask(int num, Task task) {
        System.out.println("\t" + num + ". " + task);
    }

    public static void addTodo(String remaining, TaskStorage tasks) {
        if (remaining.isBlank()) {
            throw new LlamaException("Empty Todo Task?!? Might as well ask me to not add it in!");
        }

        tasks.addTask(new Todo(remaining, false));
    }

    public static void addDeadline(String remaining, TaskStorage tasks) {
        if (remaining.isBlank()) {
            throw new LlamaException("Empty Deadline?!? What's the point of keeping track of nothing?");
        }

        String[] substringArr = remaining.split("/by ");
        try {
            LocalDate deadline = LocalDate.parse(substringArr[1].trim());
            tasks.addTask(new Deadline(substringArr[0], deadline, false));
        } catch (DateTimeParseException e) {
            throw new LlamaException("Invalid date given, please give in the format " +
                    "`deadline <name>/by yyyy-mm-dd'");
        }
    }

    public static void addEvent(String remaining, TaskStorage tasks) {
        if (remaining.isBlank()) {
            throw new LlamaException("Empty Event?!? You're planning to go nowhere with no one?");
        }

        String[] substringArr = remaining.split("/");
        String desc = substringArr[0];
        String startTimeStr = substringArr[1].substring(substringArr[1].indexOf(" ") + 1).trim();
        String endTimeStr = substringArr[2].substring(substringArr[2].indexOf(" ") + 1).trim();
        try {
            LocalDate startTime = LocalDate.parse(startTimeStr);
            LocalDate endTime = LocalDate.parse(endTimeStr);
            tasks.addTask(new Event(desc, startTime, endTime, false));
        } catch (DateTimeParseException e) {
            throw new LlamaException("Invalid date given, please give in the format " +
                    "`event <name> /from yyyy-mm-dd /to  yyyy-mm-dd'");
        }
    }

    public static void main(String[] args) {
        String logo = "";
        String hr = "____________________________________________________________" ;
        Scanner sc = new Scanner(System.in);

        TaskStorage tasks = new TaskStorage();

        // Initializing message
        displayString(hr);
        displayString("Hello! I'm Llama!");
        displayString(logo);
        displayString("What can I do for you?");



        // Get user input
        boolean shouldContinue = true;
        while (shouldContinue) {
            displayString(hr);
            String input = sc.nextLine();
            displayString(hr);

            // Split input into command and remaining
            String command = input;
            String remaining = "";
            if (input.contains(" ")) {
                command = input.substring(0, input.indexOf(" "));
                remaining = input.substring(input.indexOf(" ") + 1);
            }

            if (command.equals("bye")) {                            // end program
                shouldContinue = false;
                sc.close();
            } else if (command.equals("list")) {                    // list out tasks
                tasks.listAllTasks();
            } else if (command.equals("mark")) {                    // mark task
                int index = Integer.parseInt(remaining);
                try {
                    tasks.markTask(index);
                } catch (InvalidTaskException e) {
                    displayString(e.getMessage());
                }
            } else if (command.equals("unmark")) {                  // unmark task
                int index = Integer.parseInt(remaining);
                try {
                    tasks.unmarkTask(index);
                } catch (InvalidTaskException e) {
                    displayString(e.getMessage());
                }
            } else if (command.equals("delete")) {
                int index = Integer.parseInt(remaining);
                try {
                    tasks.deleteTask(index);
                } catch (InvalidTaskException e) {
                    displayString(e.getMessage());
                }
            } else {
                if (command.equals("todo")) {                       // add todo
                    try {
                        addTodo(remaining, tasks);
                    } catch(LlamaException e) {
                        displayString(e.getMessage());
                    }
                } else if (command.equals("deadline")) {            // add deadline
                    try {
                        addDeadline(remaining, tasks);
                    } catch(LlamaException e) {
                        displayString(e.getMessage());
                    }
                } else if (command.equals("event")) {               // add event
                    try {
                        addEvent(remaining, tasks);
                    } catch(LlamaException e) {
                        displayString(e.getMessage());
                    }
                } else {
                    try {
                        throw new LlamaException("Command not found, try again."); // really?
                    } catch (LlamaException e){
                        displayString(e.getMessage());
                    }
                }
            }
        }

        // Exit message
        displayString("Baaaaaa byeeee. Come baaaaack soon!");
    }
}
