package organice.logic.commands;

import static java.util.Objects.requireNonNull;
import static organice.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static organice.logic.commands.MatchCommand.match;

import organice.logic.parser.exceptions.ParseException;
import organice.model.Model;
import organice.model.person.Donor;
import organice.model.person.Nric;
import organice.model.person.Patient;
import organice.model.person.exceptions.PersonNotFoundException;

/**
 * Set the status of a donor and patient to Done and remove the pair from the model
 */
public class DoneCommand extends Command {
    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finish the processing of patients and donors"
            + "Parameters: ic/PATIENT NRIC ic/DONOR NRIC res/[PASS/FAIL] \n"
            + "Example: " + COMMAND_WORD + " ic/s4512345A ic/s7711123C res/pass";

    public static final String MESSAGE_SUCCESS = "Done processing the patient and donor";
    public static final String MESSAGE_NOT_PROCESSED = "Donor or patient NRIC must be valid," +
            " result of cross-matching can only be pass or fail.";

    private Nric firstNric;
    private Nric secondNric;

    private Donor donor;
    private Patient patient;

    private Nric patientNric;
    private Nric donorNric;

    private String statusDone = "done";
    private String statusNotProcessing = "not processing";
    private String result;

    /**
     * Creates a DoneCommand to determine if the given patient and donor can be deleted from the system.
     * If the cross-matching fails, the patient and donor will need to be in the system for further matching.
     * If the cross-matching pass, the patient and donor can be deleted from ORGANice.
     * @param firstNricString
     * @param secondNricString
     */
    public DoneCommand(String firstNricString, String secondNricString, String result) {
        requireNonNull(firstNricString, secondNricString);
        firstNric = new Nric(firstNricString);
        secondNric = new Nric(secondNricString);
        this.result = result;
    }

    /**
     * Method to check if the two Nrics given are valid.
     * It needs to contain one patient and one donor.
     * Both of them must be matched and is processing in order to be valid.
     * The method will first convert the Nrics given in String to an actual Nric type,
     * then it will create the donor and patient with the respective Nric in ORAGANice.
     * @param firstNric the first Nric given by the user in String.
     * @param secondNric the second Nric given by the user in String.
     * @param model
     * @return a boolean true false stating whether the inputs are valid.
     */
    public boolean isValidDonorPatientPair(Nric firstNric, Nric secondNric, Model model) {
        if (model.hasDonor(firstNric)) {
            donorNric = firstNric;
            donor = model.getDonor(donorNric);

            patientNric = secondNric;
            patient = model.getPatient(patientNric);
        } else {
            patientNric = firstNric;
            patient = model.getPatient(patientNric);

            donorNric = secondNric;
            donor = model.getDonor(donorNric);
        }
        if (model.hasPatient(patientNric) && model.hasDonor(donorNric)
                && match(donor, patient)) {
            return donor.getStatus().isProcessing() && patient.getStatus().isProcessing();
        } else {
            return false;
        }
    }

    public boolean isPass(String result) {
        if (result.toLowerCase().equals("pass")) {
            return true;
        } else if (result.toLowerCase().equals("fail")) {
            return false;
        } else {
            return false;
        }
    }

    /**
     * Set the status of the patient and donor to done if the result of the cross-matching is pass.
     * Set the status of the patient and donor to not processing if the result of the cross-matching
     * fail. Then it will be taken into consideration when MatchCommand is called.
     * @param model {@code Model} which the command should operate on.
     * @return CommandResult object.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        try {
            if (isValidDonorPatientPair(firstNric, secondNric, model) && isPass(result)) {
                model.getFilteredPersonList();

                donor.setStatus(statusDone);
                patient.setStatus(statusDone);

                model.deletePerson(donor);
                model.deletePerson(patient);
            } else if (isValidDonorPatientPair(firstNric, secondNric, model) && !isPass(result)) {
                model.getFilteredPersonList();

                donor.setStatus(statusNotProcessing);
                patient.setStatus(statusNotProcessing);

                donor.setProcessingList("");
                //TODO remove the patient from the list too to be done after processing command is merged
            } else {
                return new CommandResult(MESSAGE_NOT_PROCESSED);
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
                && (firstNric.equals(((DoneCommand) other).firstNric))
                || (secondNric.equals(((DoneCommand) other).secondNric))); // state check
    }
}

