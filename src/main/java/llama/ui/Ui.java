package llama.ui;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Deals with interactions with the user
 */
public class Ui {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final String logo = "";
    private static final String hr = "____________________________________________________________";
    /**
     * Displays welcome message to user
     */
    public String displayWelcome() {
        String response = "";
        this.displayLine();
        response += this.displayString("Hello! I'm Llama!");
        this.displayString(logo);
        response += this.displayString("What can I do for you?");
        return response;
    }

    /**
     * Displays end program message to user
     */
    public String displayBye() {
        return displayString("Baaaaaa byeeee. Come baaaaack soon!");
    }

    /**
     * Displays custom message to user with right formatting
     *
     * @param str string to be displayed to user
     */
    public String displayString(String str) {
        System.out.println("\t" + str);
        return "\n" + str;
    }

    /**
     * Displays a horizontal line
     */
    public void displayLine() {
        this.displayString(hr);
    }

    /**
     * Gets user input from terminal
     *
     * @param sc scanner used to get user input
     * @return string of user input
     */
    public String getUserInput(Scanner sc) {
        this.displayLine();
        String input = sc.nextLine();
        this.displayLine();
        return input;
    }
}
