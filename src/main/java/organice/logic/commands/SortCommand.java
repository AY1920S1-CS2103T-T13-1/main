package organice.logic.commands;

import organice.logic.commands.exceptions.CommandException;
import organice.logic.parser.SortCommandParser;
import organice.model.Model;

import static java.util.Objects.requireNonNull;

public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Returns a sorted list of matches.\n"
            + "Use this command after you've generated a list of matches to sort according to:\n"
            + "Parameters:\n"
            + "success rate/expiry (for matched donors list)\n"
            + "or priority (for matched patients list)\n";

    public static final String LIST_OF_SORTED_DONORS = "Sorted all matched donors";
    public static final String LIST_OF_SORTED_PATIENTS = "Sorted all matched patients";

    private String input;

    public SortCommand(String input) {
        requireNonNull(input);
        this.input = input;
    }

    @Override
    public CommandResult execute(Model model) {
        String resultMessage = "";
        requireNonNull(model);
        if (input.equalsIgnoreCase(SortCommandParser.ORGAN_EXPIRY_DATE)) {
            model.sortByOrganExpiryDate();
            resultMessage = LIST_OF_SORTED_DONORS;
        } else if (input.equalsIgnoreCase(SortCommandParser.PRIORITY)) {
            model.sortByPriority();
            resultMessage = LIST_OF_SORTED_PATIENTS;
        } else if (input.equalsIgnoreCase(SortCommandParser.SUCCESS_RATE)) {
            model.sortBySuccessRate();
            resultMessage = LIST_OF_SORTED_DONORS;
        }
        return new CommandResult(resultMessage);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && input.equals(((SortCommand) other).input)); // state check
    }
}
