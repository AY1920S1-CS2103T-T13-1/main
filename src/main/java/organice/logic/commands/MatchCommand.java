package organice.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import organice.model.Model;
import organice.model.person.Nric;
import organice.model.person.Patient;
import organice.model.person.Person;

/**
 * Lists all persons in the address book to the user.
 */
public class MatchCommand extends Command {

    public static final String COMMAND_WORD = "match";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Matches patients and donors who passes matching tests"
            + "Parameters: all/ (to match all patients and donors)"
            + "Parameters: ic/NRIC (to match a patient of specified NRIC)";

    public static final String MESSAGE_SUCCESS = "Matched all patients and donors";

    Predicate<Person> matchesWithNric = donor -> donor.matches(patient);

    private String input;
    private Patient patient;

    public MatchCommand(String input) {
        this.input = input;
    }


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        Nric patientNric = new Nric(input);

        if (model.hasPatient(patientNric)) {
            model.updateFilteredPersonList(matchesWithNric);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MatchCommand // instanceof handles nulls
                && input.equals(((MatchCommand) other).input)); // state check
    }
}
