package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_PERSON_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_PERSON_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_PERSON_BOB;
import static seedu.address.testutil.TypicalPersons.DOCTOR_ALICE;
import static seedu.address.testutil.TypicalPersons.DONOR_JOHN;
import static seedu.address.testutil.TypicalPersons.PERSON_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.DonorBuilder;
import seedu.address.testutil.PersonBuilder;

public class DonorTest {

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(DONOR_JOHN.isSamePerson(DONOR_JOHN));

        // null -> returns false
        assertFalse(DONOR_JOHN.isSamePerson(null));

        Donor editedJohn = new DonorBuilder(DONOR_JOHN).build();

        // different nric -> returns false
        editedJohn = new DonorBuilder(DONOR_JOHN).withNric(VALID_NRIC_PERSON_BOB).build();
        assertFalse(DONOR_JOHN.isSamePerson(editedJohn));

        // different phone -> returns true
        editedJohn = new DonorBuilder(DONOR_JOHN).withPhone(VALID_PHONE_PERSON_BOB).build();
        assertTrue(DONOR_JOHN.isSamePerson(editedJohn));

        // different name -> returns true
        editedJohn = new DonorBuilder(DONOR_JOHN).withName(VALID_NAME_PERSON_BOB).build();
        assertTrue(DONOR_JOHN.isSamePerson(editedJohn));

        // different nric, different other attributes -> returns false
        editedJohn = new DonorBuilder(DONOR_JOHN).withNric(VALID_NRIC_PERSON_BOB).withName(VALID_NAME_PERSON_BOB)
                .withPhone(VALID_PHONE_PERSON_BOB).build();
        assertFalse(DONOR_JOHN.isSamePerson(editedJohn));

        // same nric, different other attributes -> returns true
        editedJohn = new DonorBuilder(DONOR_JOHN).withName(VALID_NAME_PERSON_BOB)
                .withPhone(VALID_PHONE_PERSON_BOB).build();
        assertTrue(DONOR_JOHN.isSamePerson(editedJohn));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Donor johnCopy = new DonorBuilder(DONOR_JOHN).build();
        assertTrue(DONOR_JOHN.equals(johnCopy));

        // same object -> returns true
        assertTrue(DONOR_JOHN.equals(DONOR_JOHN));

        // null -> returns false
        assertFalse(DONOR_JOHN.equals(null));

        // different type -> returns false
        assertFalse(DONOR_JOHN.equals(5));

        // different person -> returns false
        assertFalse(DONOR_JOHN.equals(PERSON_BOB));

        // different name -> returns false
        Donor editedJohn = new DonorBuilder(DONOR_JOHN).withName(VALID_NAME_PERSON_BOB).build();
        assertFalse(DONOR_JOHN.equals(editedJohn));

        // different phone -> returns false
        editedJohn = new DonorBuilder(DONOR_JOHN).withPhone(VALID_PHONE_PERSON_BOB).build();
        assertFalse(DONOR_JOHN.equals(editedJohn));

        // different nric -> returns false
        editedJohn = new DonorBuilder(DONOR_JOHN).withNric(VALID_NRIC_PERSON_BOB).build();
        assertFalse(DONOR_JOHN.equals(editedJohn));

        // different person type -> returns false
        Person editedAlice = new PersonBuilder(DOCTOR_ALICE).withType("doctor").build();
        assertFalse(editedAlice.equals(editedJohn));
    }

    @Test
    public void toStringTest() {
        Donor john = new DonorBuilder(DONOR_JOHN).build();
        assertEquals(john.toString().trim() , "John Person Type: donor Nric: T1312123P Phone: 81230942 Age: 60");
    }

    @Test
    public void hashCodeTest() {
        Donor john = new DonorBuilder(DONOR_JOHN).build();

        assertEquals(john.hashCode(), new DonorBuilder(DONOR_JOHN).build().hashCode());
        assertNotEquals(john.hashCode(),
                new DonorBuilder(DONOR_JOHN).withPhone(VALID_PHONE_PERSON_BOB).build().hashCode());
    }
}
