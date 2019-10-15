package organice.logic.commands;

import static java.util.Objects.requireNonNull;
import static organice.logic.parser.CliSyntax.PREFIX_TYPE;
import static organice.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import organice.model.Model;
import organice.model.person.Type;

/**
 * Lists all persons of the same type in the address book to the user.
 */
public class ListCommand extends Command {
    private final Type type;

    public ListCommand(Type type) {
        requireNonNull(type);
        this.type = type;
    }

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Returns a list of persons of the stated type.\n"
            + "Parameter:\n"
            + PREFIX_TYPE + "PERSON TYPE ";

    public static final String MESSAGE_SUCCESS = "Listed all persons of stated type";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCommand // instanceof handles nulls
                && type.equals(((ListCommand) other).type)); // state check
    }
}
