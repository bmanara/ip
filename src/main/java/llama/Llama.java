package llama;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import llama.commands.Command;
import llama.data.Storage;
import llama.data.TaskList;
import llama.exceptions.LlamaException;
import llama.parser.Parser;
import llama.ui.Ui;

/**
 * Main class for program. Responsible for starting and ending the program
 */
public class Llama {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private Storage storage;
    private Ui ui;
    private TaskList taskList;

    /**
     * Constructor for Llama
     */
    public Llama() {
        this.ui = new Ui();
        this.storage = new Storage();
        try {
            this.taskList = storage.load();
        } catch (IOException e) {
            ui.displayString(e.getMessage());
        }
    }

    public String greet() {
        return ui.displayWelcome();
    }

    public String getResponse(String input) {
        try {
            Command command = Parser.parse(input);
            return command.execute(this.taskList, this.ui, this.storage);
        } catch (IOException | LlamaException e) {
            return e.getMessage();
        }
    }
}
