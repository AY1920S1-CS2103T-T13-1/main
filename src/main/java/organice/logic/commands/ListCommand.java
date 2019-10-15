package organice.logic.commands;

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

    public static final String LIST_OF_DOCTORS = "Listed all doctors";
    public static final String LIST_OF_DONORS = "Listed all donors";
    public static final String LIST_OF_PATIENTS = "Listed all patients";
    public static final String TYPE_NOT_FOUND =
            "The type of person to list is either missing or invalid.\n"
                    + "Please indicate a valid type i.e.'doctor', 'donor' or 'patient'.\n"
                    + "Listed all persons";

    private static Type type;

    public ListCommand(Type type) {
        this.type = type;
    }

    @Override
    public CommandResult execute(Model model) {
        try {
            if (type.isDoctor()) {
                model.updateFilteredPersonList(PREDICATE_SHOW_ALL_DOCTORS);
                return new CommandResult(LIST_OF_DOCTORS);
            } else if (type.isDonor()) {
                model.updateFilteredPersonList(PREDICATE_SHOW_ALL_DONORS);
                return new CommandResult(LIST_OF_DONORS);
            } else if (type.isPatient()) {
                model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PATIENTS);
                return new CommandResult(LIST_OF_PATIENTS);
            } else {
                model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
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
                || type == null && (((ListCommand) other).type) == null // no arguments given should be same objects
                || (other instanceof ListCommand // instanceof handles nulls
                && type.equals(((ListCommand) other).type)); // state check
    }
}
