package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NricTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Nric(null));
    }

    @Test
    public void constructor_invalidNric_throwsIllegalArgumentException() {
        String invalidNric = "";
        assertThrows(IllegalArgumentException.class, () -> new Nric(invalidNric));
    }

    @Test
    public void isValidNric() {
        // null nric
        assertThrows(NullPointerException.class, () -> Nric.isValidNric(null));

        // invalid nric
        assertFalse(Nric.isValidNric("")); // empty string
        assertFalse(Nric.isValidNric(" ")); // spaces only
        assertFalse(Nric.isValidNric("1234567")); // contains only number
        assertFalse(Nric.isValidNric("9312930R")); // begins with a number
        assertFalse(Nric.isValidNric("S123456A")); // contains less than 7 numbers
        assertFalse(Nric.isValidNric("S1234567")); // ends with a number
        assertFalse(Nric.isValidNric("AAAAAAAAA")); // contains only letters
        assertFalse(Nric.isValidNric("N1234567A")); // starts with letter other than S/T/F/G

        // valid nric
        assertTrue(Nric.isValidNric("s1234512b")); // should be case insensitive
        assertTrue(Nric.isValidNric("s1234512R")); // should be case insensitive
        assertTrue(Nric.isValidNric("T1234512a")); // should be case insensitive
        assertTrue(Nric.isValidNric("S1234567B")); // starts with 'S'
        assertTrue(Nric.isValidNric("T2222222A")); // starts with 'T'
        assertTrue(Nric.isValidNric("F3333333A")); // starts with 'F'
        assertTrue(Nric.isValidNric("G4444444A")); // starts with 'G'
    }
}
