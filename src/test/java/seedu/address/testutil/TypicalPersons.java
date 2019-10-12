package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_AGE_DONOR_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AGE_PATIENT_IRENE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DONOR_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_PATIENT_IRENE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_PERSON_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_PERSON_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_DONOR_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_PATIENT_IRENE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_PERSON_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_PERSON_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_DONOR_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_PATIENT_IRENE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_PERSON_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_PERSON_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_PERSON_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.person.Doctor;
import seedu.address.model.person.Donor;
import seedu.address.model.person.Patient;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Doctor DOCTOR_ALICE = new DoctorBuilder().withNric("S1532142A")
            .withName("Alice Pauline").withPhone("94351253").build();
    public static final Doctor DOCTOR_BENSON = new DoctorBuilder().withNric("T5231426Q")
            .withName("Benson Meier").withPhone("98765432").build();
    public static final Patient PATIENT_CARL = new PatientBuilder().withAge("22").withNric("G5642431P")
            .withName("Carl Kurz").withPhone("95352563").build();
    public static final Patient PATIENT_DANIEL = new PatientBuilder().withAge("34").withNric("F6423467F")
            .withName("Daniel Meier").withPhone("87652533").build();
    public static final Donor DONOR_ELLE = new DonorBuilder().withAge("13").withNric("S9374923S")
            .withName("Elle Meyer").withPhone("9482224").build();
    public static final Donor DONOR_FIONA = new DonorBuilder().withAge("25").withNric("F9183156L")
            .withName("Fiona Kunz").withPhone("9482427").build();
    public static final Donor DONOR_GEORGE = new DonorBuilder().withAge("44").withNric("S1234567A")
            .withName("George Best").withPhone("9482442").build();

    // Manually added
    public static final Person PERSON_HOON = new PersonBuilder().withType("doctor").withNric("G1245325A")
            .withName("Hoon Meier").withPhone("8482424").build();
    public static final Person PERSON_IDA = new PersonBuilder().withType("doctor").withNric("T1125125L")
            .withName("Ida Mueller").withPhone("8482131").build();

    //Sample Patients
    public static final Patient PATIENT_IRENE = new PatientBuilder().withAge(VALID_AGE_PATIENT_IRENE)
            .withName(VALID_NAME_PATIENT_IRENE).withNric(VALID_NRIC_PATIENT_IRENE).withPhone(VALID_PHONE_PATIENT_IRENE)
            .build();
    //Sample Donors
    public static final Donor DONOR_JOHN = new DonorBuilder().withAge(VALID_AGE_DONOR_JOHN)
            .withName(VALID_NAME_DONOR_JOHN).withNric(VALID_NRIC_DONOR_JOHN).withPhone(VALID_PHONE_DONOR_JOHN).build();

    // Manually added - person's details found in {@code CommandTestUtil}
    public static final Person PERSON_AMY = new PersonBuilder().withType(VALID_TYPE_PERSON_AMY)
            .withNric(VALID_NRIC_PERSON_AMY).withName(VALID_NAME_PERSON_AMY)
            .withPhone(CommandTestUtil.VALID_PHONE_PERSON_AMY).build();
    public static final Person PERSON_BOB = new PersonBuilder().withType(VALID_TYPE_PERSON_BOB)
            .withNric(VALID_NRIC_PERSON_BOB).withName(VALID_NAME_PERSON_BOB).withPhone(VALID_PHONE_PERSON_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(DOCTOR_ALICE, DOCTOR_BENSON, PATIENT_CARL, PATIENT_DANIEL, DONOR_ELLE,
                DONOR_FIONA, DONOR_GEORGE));
    }
}
