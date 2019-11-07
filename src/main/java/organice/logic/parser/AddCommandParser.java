package organice.logic.parser;

import static organice.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static organice.logic.parser.CliSyntax.PREFIX_AGE;
import static organice.logic.parser.CliSyntax.PREFIX_BLOOD_TYPE;
import static organice.logic.parser.CliSyntax.PREFIX_DOCTOR_IN_CHARGE;
import static organice.logic.parser.CliSyntax.PREFIX_NAME;
import static organice.logic.parser.CliSyntax.PREFIX_NRIC;
import static organice.logic.parser.CliSyntax.PREFIX_ORGAN;
import static organice.logic.parser.CliSyntax.PREFIX_ORGAN_EXPIRY_DATE;
import static organice.logic.parser.CliSyntax.PREFIX_PHONE;
import static organice.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static organice.logic.parser.CliSyntax.PREFIX_TISSUE_TYPE;
import static organice.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import organice.logic.commands.AddCommand;
import organice.logic.parser.exceptions.ParseException;
import organice.model.person.Age;
import organice.model.person.BloodType;
import organice.model.person.Doctor;
import organice.model.person.DoctorInCharge;
import organice.model.person.Donor;
import organice.model.person.Name;
import organice.model.person.Nric;
import organice.model.person.Organ;
import organice.model.person.OrganExpiryDate;
import organice.model.person.Patient;
import organice.model.person.Phone;
import organice.model.person.Priority;
import organice.model.person.Status;
import organice.model.person.TissueType;
import organice.model.person.Type;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {
    private static List<Prefix> PREFIXES_DOCTORS = new ArrayList<>(Arrays.asList(PREFIX_TYPE, PREFIX_NRIC, PREFIX_NAME,
            PREFIX_PHONE));
    private static List<Prefix> PREFIXES_PATIENT = new ArrayList<>(Arrays.asList(PREFIX_TYPE, PREFIX_NRIC, PREFIX_NAME,
            PREFIX_AGE, PREFIX_PHONE, PREFIX_PRIORITY, PREFIX_BLOOD_TYPE, PREFIX_DOCTOR_IN_CHARGE,
            PREFIX_ORGAN, PREFIX_TISSUE_TYPE));
    private static List<Prefix> PREFIXES_DONORS = new ArrayList<>(Arrays.asList(PREFIX_TYPE, PREFIX_NRIC, PREFIX_NAME,
            PREFIX_AGE, PREFIX_PHONE, PREFIX_BLOOD_TYPE, PREFIX_ORGAN_EXPIRY_DATE, PREFIX_ORGAN, PREFIX_TISSUE_TYPE));

    /**
     * Returns the {@code Type} of person in the given {@code ArgumentMultimap}
     * @throws ParseException if the type is not specified correctly in the input arguments
     */
    private static Type parseType(ArgumentMultimap argumentMultimap) throws ParseException {
        List<String> allTypeInputs = argumentMultimap.getAllValues(PREFIX_TYPE);
        if (allTypeInputs.size() == 1) {
            return ParserUtil.parseType(allTypeInputs.get(0));
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Checks if the input argument has any prefixes.
     * If there are any prefixes besides PREFIX_TYPE, the add via form method will not be launched.
     * @return true if all prefixes besides PREFIX_TYPE are not in the input
     */
    private static boolean canLaunchForm(ArgumentMultimap argMultimap) {
        List<Prefix> allPrefixesExceptType = new ArrayList<>(Arrays.asList(PREFIX_NRIC, PREFIX_NAME,
                PREFIX_AGE, PREFIX_PHONE, PREFIX_PRIORITY, PREFIX_BLOOD_TYPE, PREFIX_DOCTOR_IN_CHARGE,
                PREFIX_ORGAN, PREFIX_TISSUE_TYPE));
        for (Prefix prefix : allPrefixesExceptType) {
            List<String> allInputs = argMultimap.getAllValues(prefix);
            if (allInputs.size() != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Creates a {@code Doctor} from arguments in the {@code ArgumentMultimap}.
     */
    private static Doctor createDoctor(Type type, ArgumentMultimap argMultimap) throws ParseException {
        Nric nric;
        Name name;
        Phone phone;
        if (areNumberOfPrefixesCorrectDoctor(argMultimap)) {
            nric = ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get());
            name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        return new Doctor(type, nric, name, phone);
    }

    /**
     * Creates a {@code Donor} from arguments in the {@code ArgumentMultimap}.
     */
    private static Donor createDonor(Type type, ArgumentMultimap argMultimap) throws ParseException {
        Nric nric;
        Name name;
        Phone phone;
        Age age;
        BloodType bloodType;
        TissueType tissueType;
        Organ organ;
        OrganExpiryDate organExpiryDate;
        Status status;

        if (areNumberOfPrefixesCorrectDonor(argMultimap)) {
            nric = ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get());
            name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
            age = ParserUtil.parseAge(argMultimap.getValue(PREFIX_AGE).get());
            bloodType = ParserUtil.parseBloodType(argMultimap.getValue(PREFIX_BLOOD_TYPE).get());
            tissueType = ParserUtil.parseTissueType(argMultimap.getValue(PREFIX_TISSUE_TYPE).get());
            organ = ParserUtil.parseOrgan(argMultimap.getValue(PREFIX_ORGAN).get());
            organExpiryDate = ParserUtil.parseOrganExpiryDate(argMultimap.getValue(PREFIX_ORGAN_EXPIRY_DATE).get());
            status = new Status(Status.STATUS_NOT_PROCESSING);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        return new Donor(type, nric, name, phone, age, bloodType, tissueType, organ, organExpiryDate,
                status);
    }

    /**
     * Creates a {@code Donor} from arguments in the {@code ArgumentMultimap}.
     */
    private static Patient createPatient(Type type, ArgumentMultimap argMultimap) throws ParseException {
        Nric nric;
        Name name;
        Phone phone;
        Age age;
        Priority priority;
        BloodType bloodType;
        TissueType tissueType;
        Organ organ;
        DoctorInCharge doctorInCharge;
        Status status;

        if (areNumberOfPrefixesCorrectPatient(argMultimap)) {
            nric = ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get());
            name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
            age = ParserUtil.parseAge(argMultimap.getValue(PREFIX_AGE).get());
            priority = ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get());
            bloodType = ParserUtil.parseBloodType(argMultimap.getValue(PREFIX_BLOOD_TYPE).get());
            tissueType = ParserUtil.parseTissueType(argMultimap.getValue(PREFIX_TISSUE_TYPE).get());
            organ = ParserUtil.parseOrgan(argMultimap.getValue(PREFIX_ORGAN).get());
            doctorInCharge = ParserUtil.parseDoctorInCharge(argMultimap.getValue(PREFIX_DOCTOR_IN_CHARGE).get());
            status = new Status(Status.STATUS_NOT_PROCESSING);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        return new Patient(type, nric, name, phone, age, priority,
                bloodType, tissueType, organ, doctorInCharge, status);
    }


    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        //put all the prefixes in the multimap to tokenize.
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TYPE, PREFIX_NRIC, PREFIX_NAME,
                PREFIX_AGE, PREFIX_PHONE, PREFIX_PRIORITY, PREFIX_BLOOD_TYPE, PREFIX_DOCTOR_IN_CHARGE,
                        PREFIX_ORGAN_EXPIRY_DATE, PREFIX_ORGAN, PREFIX_TISSUE_TYPE);

        //checks if preamble is empty. Acts as the first round of validation
        isPreambleEmpty(argMultimap);

        // common prefixes between all three types of persons. parseType also validates if PREFIX_TYPE is there.
        Type type = parseType(argMultimap);

        // form mode is launched when only the type prefix is specified
        if (canLaunchForm(argMultimap)) {
            return new AddCommand(type);
        }

        if (type.isDoctor() && areNumberOfPrefixesCorrectDoctor(argMultimap)) {
            Doctor doctor = createDoctor(type, argMultimap);
            return new AddCommand(doctor);
        } else if (type.isDonor() && areNumberOfPrefixesCorrectDonor(argMultimap)) {
            //TaskList taskList = new TaskList("");
            Donor donor = createDonor(type, argMultimap);
            return new AddCommand(donor);
        } else if (type.isPatient() && areNumberOfPrefixesCorrectPatient(argMultimap)) {
            Patient patient = createPatient(type, argMultimap);
            return new AddCommand(patient);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Throws an exception if the preamble of an input is not empty.
     */
    private static void isPreambleEmpty(ArgumentMultimap argMultimap) throws ParseException {
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
    }

    private static boolean areNumberOfPrefixesCorrectDoctor(ArgumentMultimap argumentMultimap) {
        for (Prefix prefix : PREFIXES_DOCTORS) {
            List<String> allInputsOfAPrefix = argumentMultimap.getAllValues(prefix);
            if (allInputsOfAPrefix.size() != 1) {
                return false;
            }
        }
        return true;
    }

    private static boolean areNumberOfPrefixesCorrectDonor(ArgumentMultimap argumentMultimap) {
        for (Prefix prefix : PREFIXES_DONORS) {
            List<String> allInputsOfAPrefix = argumentMultimap.getAllValues(prefix);
            if (allInputsOfAPrefix.size() != 1) {
                return false;
            }
        }
        return true;
    }

    private static boolean areNumberOfPrefixesCorrectPatient(ArgumentMultimap argumentMultimap) {
        for (Prefix prefix : PREFIXES_PATIENT) {
            List<String> allInputsOfAPrefix = argumentMultimap.getAllValues(prefix);
            if (allInputsOfAPrefix.size() != 1) {
                return false;
            }
        }
        return true;
    }
}
