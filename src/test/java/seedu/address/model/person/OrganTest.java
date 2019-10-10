package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class OrganTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Organ(null));
    }

    @Test
    public void constructor_invalidOrgan_throwsIllegalArgumentException() {
        String invalidOrgan = "";
        assertThrows(IllegalArgumentException.class, () -> new Organ(invalidOrgan));
    }

    @Test
    public void isValidOrgan() {
        // null organ
        assertThrows(NullPointerException.class, () -> Organ.isValidOrgan(null));

        // invalid organ
        assertFalse(Organ.isValidOrgan("")); // empty string
        assertFalse(Organ.isValidOrgan(" ")); // spaces only
        assertFalse(Organ.isValidOrgan("^")); // only non-alphanumeric characters
        assertFalse(Organ.isValidOrgan("kidney*")); // contains non-alphanumeric characters
        assertFalse(Organ.isValidOrgan("12345")); // only numbers
        assertFalse(Organ.isValidOrgan("kidney1")); // alphanumeric characters

        // valid organ
        assertTrue(Organ.isValidOrgan("kidney")); // alphabets only
        assertTrue(Organ.isValidOrgan("Kidney")); // with capital letters
    }
}
