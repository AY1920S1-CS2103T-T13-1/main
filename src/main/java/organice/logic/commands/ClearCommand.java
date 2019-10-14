package organice.logic.commands;

import static java.util.Objects.requireNonNull;

import organice.model.Organice;
import organice.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Organice has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setOrganice(new Organice());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
