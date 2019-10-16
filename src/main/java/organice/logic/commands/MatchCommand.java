package organice.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import organice.model.Model;
import organice.model.person.BloodType;
import organice.model.person.Donor;
import organice.model.person.Nric;
import organice.model.person.Patient;
import organice.model.person.Person;
import organice.model.person.TissueType;
import organice.model.person.exceptions.PersonNotFoundException;

/**
 * Lists all persons in the address book to the user.
 */
public class MatchCommand extends Command {

    public static final String COMMAND_WORD = "match";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Matches patients and donors who passes matching tests"
            + "Parameters: all/ (to match all patients and donors)"
            + "Parameters: ic/NRIC (to match a patient of specified NRIC)";

    public static final String MESSAGE_SUCCESS = "Matched all patients and donors";
    public static final String MESSAGE_PERSON_NOT_FOUND = "The patient with Nric %1$s cannot be found in ORGANice!";

    public static final Double SUCCESSFUL_PERCENTAGE = 80.0;


    private String input;
    private Patient patient;

    public MatchCommand(String input) {
        this.input = input;
    }

    public boolean match(Person donor, Patient patient) {
        if (!(donor instanceof Donor)) {
            return false;
        }

        BloodType donorBloodType = ((Donor) donor).getBloodType();
        BloodType patientBloodType = patient.getBloodType();

        TissueType donorTissueType = ((Donor) donor).getTissueType();
        TissueType patientTissueType = patient.getTissueType();

        if (patientBloodType.isBloodTypeMatch(donorBloodType) &&
                donorTissueType.getPercentageMatch(patientTissueType) >= SUCCESSFUL_PERCENTAGE) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        Nric patientNric = new Nric(input);
        try {
            if (model.hasPatient(patientNric)) {
                patient = model.getPatient(patientNric);
                Predicate<Person> matchesWithNric = donor -> match(donor, patient);
                model.updateFilteredPersonList(matchesWithNric);
            }
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (PersonNotFoundException pne) {
            return new CommandResult(String.format(MESSAGE_PERSON_NOT_FOUND, patientNric));
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MatchCommand // instanceof handles nulls
                && input.equals(((MatchCommand) other).input)); // state check
    }
}
