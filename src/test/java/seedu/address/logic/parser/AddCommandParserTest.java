package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AGE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.AGE_DESC_IRENE;
import static seedu.address.logic.commands.CommandTestUtil.AGE_DESC_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AGE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NRIC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRIORITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_IRENE;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_IRENE;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_IRENE;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_IRENE;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_IRENE;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AGE_IRENE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AGE_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_IRENE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_IRENE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_IRENE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_IRENE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_BOB;

import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_IRENE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_JOHN;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.IRENE;
import static seedu.address.testutil.TypicalPersons.JOHN;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Age;
import seedu.address.model.person.Doctor;
import seedu.address.model.person.Donor;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;

import seedu.address.model.person.Patient;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Priority;
import seedu.address.model.person.Type;
import seedu.address.testutil.DoctorBuilder;
import seedu.address.testutil.PatientBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresentPatient_success() {
        Patient expectedPatient = new PatientBuilder(IRENE).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TYPE_DESC_IRENE + NRIC_DESC_IRENE
                + NAME_DESC_IRENE + PHONE_DESC_IRENE + AGE_DESC_IRENE + PRIORITY_DESC_IRENE,
                new AddCommand(expectedPatient));

        // multiple names - last name accepted
        assertParseSuccess(parser, TYPE_DESC_IRENE + NRIC_DESC_IRENE + NAME_DESC_AMY
                + NAME_DESC_IRENE + PHONE_DESC_IRENE + AGE_DESC_IRENE + PRIORITY_DESC_IRENE,
                new AddCommand(expectedPatient));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, TYPE_DESC_IRENE + NRIC_DESC_IRENE + NAME_DESC_IRENE
                + PHONE_DESC_AMY + PHONE_DESC_IRENE + AGE_DESC_IRENE + PRIORITY_DESC_IRENE,
                new AddCommand(expectedPatient));

        // multiple nrics - last nric accepted
        assertParseSuccess(parser, TYPE_DESC_IRENE + NRIC_DESC_AMY + NRIC_DESC_IRENE + NAME_DESC_IRENE
                + PHONE_DESC_IRENE + AGE_DESC_IRENE + PRIORITY_DESC_IRENE, new AddCommand(expectedPatient));

        // multiple types - last type accepted
        assertParseSuccess(parser, TYPE_DESC_BOB + TYPE_DESC_IRENE + NRIC_DESC_IRENE
                + NAME_DESC_IRENE + PHONE_DESC_IRENE + AGE_DESC_IRENE + PRIORITY_DESC_IRENE,
                new AddCommand(expectedPatient));

        //multiple priority - last priority accepted
        assertParseSuccess(parser, TYPE_DESC_IRENE + NRIC_DESC_IRENE + NAME_DESC_IRENE + PHONE_DESC_IRENE
                + AGE_DESC_IRENE + PRIORITY_DESC_BOB + PRIORITY_DESC_IRENE, new AddCommand(expectedPatient));

        // multiple ages - last age accepted
        assertParseSuccess(parser, TYPE_DESC_IRENE + NRIC_DESC_IRENE + NAME_DESC_IRENE + PHONE_DESC_IRENE
                + AGE_DESC_BOB + AGE_DESC_IRENE + PRIORITY_DESC_IRENE, new AddCommand(expectedPatient));

    }

    @Test
    public void parse_allFieldsPresentDonor_success() {
        //Testing all fields present for a Donor
        Donor expectedDonor = JOHN;

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TYPE_DESC_JOHN + NRIC_DESC_JOHN
                + NAME_DESC_JOHN + PHONE_DESC_JOHN + AGE_DESC_JOHN, new AddCommand(expectedDonor));

        // multiple names - last name accepted
        assertParseSuccess(parser, TYPE_DESC_JOHN + NRIC_DESC_JOHN + NAME_DESC_AMY
                + NAME_DESC_JOHN + PHONE_DESC_JOHN + AGE_DESC_JOHN, new AddCommand(expectedDonor));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, TYPE_DESC_JOHN + NRIC_DESC_JOHN + NAME_DESC_JOHN
                + PHONE_DESC_AMY + PHONE_DESC_JOHN + AGE_DESC_JOHN, new AddCommand(expectedDonor));

        // multiple nrics - last nric accepted
        assertParseSuccess(parser, TYPE_DESC_JOHN + NRIC_DESC_AMY + NRIC_DESC_JOHN + NAME_DESC_JOHN
                + PHONE_DESC_JOHN + AGE_DESC_JOHN, new AddCommand(expectedDonor));

        // multiple types - last type accepted
        assertParseSuccess(parser, TYPE_DESC_BOB + TYPE_DESC_JOHN + NRIC_DESC_JOHN
                + NAME_DESC_JOHN + PHONE_DESC_JOHN + AGE_DESC_JOHN, new AddCommand(expectedDonor));

        // multiple ages - last age accepted
        assertParseSuccess(parser, TYPE_DESC_JOHN + NRIC_DESC_JOHN
                + NAME_DESC_JOHN + PHONE_DESC_JOHN + AGE_DESC_BOB + AGE_DESC_JOHN, new AddCommand(expectedDonor));
    }

    @Test
    public void parse_allFieldsPresentDoctor_success() {
        Doctor expectedDoctor = new DoctorBuilder(AMY).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TYPE_DESC_AMY + NRIC_DESC_AMY
                + NAME_DESC_AMY + PHONE_DESC_AMY, new AddCommand(expectedDoctor));

        // multiple names - last name accepted
        assertParseSuccess(parser, TYPE_DESC_AMY + NRIC_DESC_AMY + NAME_DESC_BOB
                + NAME_DESC_AMY + PHONE_DESC_AMY, new AddCommand(expectedDoctor));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, TYPE_DESC_AMY + NRIC_DESC_AMY + NAME_DESC_AMY
                + PHONE_DESC_BOB + PHONE_DESC_AMY, new AddCommand(expectedDoctor));

        // multiple nrics - last nric accepted
        assertParseSuccess(parser, TYPE_DESC_AMY + NRIC_DESC_BOB + NRIC_DESC_AMY
                + NAME_DESC_AMY + PHONE_DESC_AMY, new AddCommand(expectedDoctor));

        // multiple types - last type accepted
        assertParseSuccess(parser, TYPE_DESC_BOB + TYPE_DESC_AMY + NRIC_DESC_AMY
                + NAME_DESC_AMY + PHONE_DESC_AMY, new AddCommand(expectedDoctor));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing type prefix
        assertParseFailure(parser, VALID_TYPE_BOB + NRIC_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB,
                expectedMessage);

        // missing nric prefix -- donor
        assertParseFailure(parser, TYPE_DESC_JOHN + NAME_DESC_JOHN + VALID_NRIC_JOHN + PHONE_DESC_JOHN
                + AGE_DESC_JOHN, expectedMessage);

        //missing nric prefix -- patient
        assertParseFailure(parser, TYPE_DESC_IRENE + NAME_DESC_IRENE + VALID_NRIC_IRENE + PHONE_DESC_IRENE
                + AGE_DESC_IRENE + PRIORITY_DESC_IRENE, expectedMessage);

        // missing name prefix -- donor
        assertParseFailure(parser, TYPE_DESC_JOHN + NRIC_DESC_JOHN + VALID_NAME_JOHN + PHONE_DESC_JOHN
                + AGE_DESC_JOHN, expectedMessage);

        // missing name prefix -- patient
        assertParseFailure(parser, TYPE_DESC_IRENE + NRIC_DESC_IRENE + VALID_NAME_IRENE + PHONE_DESC_IRENE
                + AGE_DESC_IRENE + PRIORITY_DESC_IRENE, expectedMessage);

        // missing phone prefix -- donor
        assertParseFailure(parser, TYPE_DESC_JOHN + NRIC_DESC_JOHN + NAME_DESC_JOHN + VALID_PHONE_JOHN
                + AGE_DESC_JOHN, expectedMessage);

        // missing phone prefix -- patient
        assertParseFailure(parser, TYPE_DESC_IRENE + NRIC_DESC_IRENE + NAME_DESC_IRENE + VALID_PHONE_IRENE
            + AGE_DESC_IRENE + PRIORITY_DESC_IRENE, expectedMessage);

        // missing age prefix -- donor
        assertParseFailure(parser, TYPE_DESC_JOHN + NRIC_DESC_JOHN + NAME_DESC_JOHN + PHONE_DESC_JOHN
            + VALID_AGE_JOHN, expectedMessage);

        // missing age prefix -- patient
        assertParseFailure(parser, TYPE_DESC_IRENE + NRIC_DESC_IRENE + NAME_DESC_IRENE + PHONE_DESC_IRENE
            + VALID_AGE_IRENE + PRIORITY_DESC_IRENE, expectedMessage);

        // missing priority prefix -- patient
        assertParseFailure(parser, TYPE_DESC_IRENE + NRIC_DESC_IRENE + NAME_DESC_IRENE + PHONE_DESC_IRENE
                + AGE_DESC_IRENE + VALID_PRIORITY_IRENE, expectedMessage);

        // all prefixes missing -- donor
        assertParseFailure(parser, VALID_TYPE_JOHN + VALID_NRIC_JOHN + VALID_NAME_JOHN + VALID_PHONE_JOHN
            + VALID_AGE_JOHN, expectedMessage);

        // all prefixes missing -- patient
        assertParseFailure(parser, VALID_TYPE_IRENE + VALID_NRIC_IRENE + VALID_NAME_IRENE + VALID_PHONE_IRENE
            + VALID_AGE_IRENE + VALID_PRIORITY_IRENE, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid type
        assertParseFailure(parser, INVALID_TYPE_DESC + NRIC_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB,
                Type.MESSAGE_CONSTRAINTS);

        // invalid nric -- donor
        assertParseFailure(parser, TYPE_DESC_JOHN + INVALID_NRIC_DESC + NAME_DESC_JOHN + PHONE_DESC_JOHN
                + AGE_DESC_JOHN, Nric.MESSAGE_CONSTRAINTS);

        // invalid nric -- patient
        assertParseFailure(parser, TYPE_DESC_IRENE + INVALID_NRIC_DESC + NAME_DESC_IRENE + PHONE_DESC_IRENE
                + AGE_DESC_IRENE + PRIORITY_DESC_IRENE, Nric.MESSAGE_CONSTRAINTS);

        // invalid name -- donor
        assertParseFailure(parser, TYPE_DESC_JOHN + NRIC_DESC_JOHN + INVALID_NAME_DESC + PHONE_DESC_JOHN
                + AGE_DESC_JOHN, Name.MESSAGE_CONSTRAINTS);

        // invalid name -- patient
        assertParseFailure(parser, TYPE_DESC_IRENE + NRIC_DESC_IRENE + INVALID_NAME_DESC + PHONE_DESC_IRENE
                + AGE_DESC_IRENE + PRIORITY_DESC_IRENE, Name.MESSAGE_CONSTRAINTS);

        // invalid phone -- donor
        assertParseFailure(parser, TYPE_DESC_JOHN + NRIC_DESC_JOHN + NAME_DESC_JOHN + INVALID_PHONE_DESC
                + AGE_DESC_JOHN, Phone.MESSAGE_CONSTRAINTS);

        // invalid phone -- patient
        assertParseFailure(parser, TYPE_DESC_IRENE + NRIC_DESC_IRENE + NAME_DESC_IRENE + INVALID_PHONE_DESC
                + AGE_DESC_IRENE + PRIORITY_DESC_IRENE, Phone.MESSAGE_CONSTRAINTS);

        // invalid age -- donor
        assertParseFailure(parser, TYPE_DESC_JOHN + NRIC_DESC_JOHN + NAME_DESC_JOHN + PHONE_DESC_JOHN
                + INVALID_AGE_DESC, Age.MESSAGE_CONSTRAINTS);

        // invalid age -- patient
        assertParseFailure(parser, TYPE_DESC_IRENE + NRIC_DESC_IRENE + NAME_DESC_IRENE + PHONE_DESC_IRENE
                + INVALID_AGE_DESC + PRIORITY_DESC_IRENE, Age.MESSAGE_CONSTRAINTS);

        //invalid priority -- patient
        assertParseFailure(parser, TYPE_DESC_IRENE + NRIC_DESC_IRENE + NAME_DESC_IRENE + PHONE_DESC_IRENE
                + AGE_DESC_IRENE + INVALID_PRIORITY_DESC, Priority.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported -- donor
        assertParseFailure(parser, TYPE_DESC_JOHN + NRIC_DESC_JOHN + INVALID_NAME_DESC + INVALID_PHONE_DESC
                + AGE_DESC_JOHN, Name.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported -- patient
        assertParseFailure(parser, TYPE_DESC_IRENE + NRIC_DESC_IRENE + INVALID_NAME_DESC + INVALID_PHONE_DESC
                + AGE_DESC_IRENE + PRIORITY_DESC_IRENE, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble -- donor
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TYPE_DESC_JOHN + NRIC_DESC_JOHN + NAME_DESC_JOHN
                + PHONE_DESC_JOHN + AGE_DESC_JOHN,
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        // non-empty preamble -- patient
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TYPE_DESC_BOB + NRIC_DESC_BOB + NAME_DESC_BOB
                + PHONE_DESC_BOB + AGE_DESC_BOB,
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
