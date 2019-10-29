package organice.logic.commands;

import static java.util.Objects.requireNonNull;
import static organice.logic.commands.MatchCommand.match;

import java.util.List;

import organice.commons.core.Messages;
import organice.logic.commands.exceptions.CommandException;
import organice.model.Model;
import organice.model.person.Donor;
import organice.model.person.Nric;
import organice.model.person.Patient;
import organice.model.person.Person;
import organice.model.person.exceptions.PersonNotFoundException;

public class DoneCommand extends Command {
    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finish the processing of patients and donors"
            + "Parameters: ic/PATIENT NRIC ic/DONOR NRIC \n"
            + "Example: " + COMMAND_WORD + " ic/s4512345A ic/s7711123C";

    public static final String MESSAGE_SUCCESS = "Done processing the patient and donor";
    public static final String MESSAGE_NOT_PROCESSED = "Donor or patient NRIC must be valid";

    private String firstNricString;
    private String secondNricString;

    private Nric firstNric;
    private Nric secondNric;

    private Donor donor;
    private Patient patient;

    private Nric patientNRIC;
    private  Nric donorNRIC;


    public DoneCommand(String firstNricString, String secondNricString) {
        requireNonNull(firstNricString, secondNricString);
        firstNric = new Nric(firstNricString);
        secondNric = new Nric(secondNricString);
    }

    public boolean isValidDonorPatientPair(Nric firstNric, Nric secondNric, Model model) {
        if (model.hasDonor(firstNric)) {
            donorNRIC = firstNric;
            donor = model.getDonor(donorNRIC);

            patientNRIC = secondNric;
            patient = model.getPatient(patientNRIC);
        } else {
            patientNRIC = firstNric;
            patient = model.getPatient(patientNRIC);

            donorNRIC = secondNric;
            donor = model.getDonor(donorNRIC);
        }
        if (model.hasPatient(patientNRIC) && model.hasDonor(donorNRIC)
                && match(donor, patient))  {
            //todo check if tasklist isempty
            return true;
        } else {
            return false;
        }

    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        try {
            if (isValidDonorPatientPair(firstNric, secondNric, model)) {
                model.getFilteredPersonList();

                donor.setStatus("done");
                patient.setStatus("done");

                model.deletePerson(donor);
                model.deletePerson(patient);
            }
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (PersonNotFoundException pne) {
            return new CommandResult(MESSAGE_NOT_PROCESSED);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DoneCommand // instanceof handles nulls
                && (firstNricString.equals(((DoneCommand) other).firstNricString))
                || (secondNric.equals(((DoneCommand) other).secondNric))); // state check
    }
}
