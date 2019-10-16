package organice.logic.commands;

import static java.util.Objects.requireNonNull;

import organice.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class MatchCommand extends Command {

    public static final String COMMAND_WORD = "match";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Matches patients and donors who passes matching tests"
            + "Parameters: all/ (to match all patients and donors)"
            + "Parameters: ic/NRIC (to match a patient of specified NRIC)"
    public static final String MESSAGE_SUCCESS = "Matched all patients and donors";

    public MatchCommand(String input) {

    }


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        runAllTests();
        model.updateFilteredPersonList(results);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
