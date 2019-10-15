package organice.logic.commands;

import static java.util.Objects.requireNonNull;
import static organice.logic.parser.CliSyntax.PREFIX_TYPE;
import static organice.model.Model.PREDICATE_SHOW_ALL_DOCTORS;
import static organice.model.Model.PREDICATE_SHOW_ALL_DONORS;
import static organice.model.Model.PREDICATE_SHOW_ALL_PATIENTS;
import static organice.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import organice.model.Model;
import organice.model.person.Type;

/**
 * Lists all persons of the same type in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Returns a list of persons of the stated type.\n"
            + "Parameter:\n"
            + PREFIX_TYPE + "PERSON TYPE ";

    public static final String MESSAGE_SUCCESS = "Listed all persons of stated type";

    private static final String LIST_OF_DOCTORS = "";
    private static final String LIST_OF_DONORS = "";
    private static final String LIST_OF_PATIENTS = "";
    private static final String TYPE_NOT_FOUND = "";

    private static Type type;

    public ListCommand(Type type) {
        requireNonNull(type);
        this.type = type;
    }

    @Override
    public CommandResult execute(Model model) {
        try {
            if (type.isDoctor()) {
                model.updateFilteredPersonList(PREDICATE_SHOW_ALL_DOCTORS);
                return new CommandResult(MESSAGE_SUCCESS + LIST_OF_DOCTORS);
            } else if (type.isDonor()) {
                model.updateFilteredPersonList(PREDICATE_SHOW_ALL_DONORS);
                return new CommandResult(MESSAGE_SUCCESS + LIST_OF_DONORS);
            } else if (type.isPatient()) {
                model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PATIENTS);
                return new CommandResult(MESSAGE_SUCCESS + LIST_OF_PATIENTS);
            } else {
                return new CommandResult(TYPE_NOT_FOUND);
            }
        } catch (NullPointerException e) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(TYPE_NOT_FOUND);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCommand // instanceof handles nulls
                && type.equals(((ListCommand) other).type)); // state check
    }
}
