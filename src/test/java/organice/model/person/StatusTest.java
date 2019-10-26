package organice.model.person;

import static org.junit.jupiter.api.Assertions.*;
import static organice.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class StatusTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new BloodType(null));
    }

    @Test
    public void constructor_invalidBloodType_throwsIllegalArgumentException() {
        String invalidStatus = "";
        assertThrows(IllegalArgumentException.class, () -> new Status(invalidStatus));
    }

    @Test
    void isValidStatus() {
        // null status
        assertThrows(NullPointerException.class, () -> Status.isValidStatus(null));

        // invalid status
        assertFalse(Status.isValidStatus("")); // empty string
        assertFalse(Status.isValidStatus(" ")); // spaces only
        assertFalse(Status.isValidStatus("abc")); // wrong Status
        assertFalse(Status.isValidStatus("1")); // numeric
        assertFalse(Status.isValidStatus("not+processing")); // + within alphabets
        assertFalse(Status.isValidStatus("pro cess ing")); // spaces within alphabets
        assertFalse(Status.isValidStatus("procesing")); // typo


        // valid status
        assertTrue(Status.isValidStatus("processing"));
        assertTrue(Status.isValidStatus("not processing"));
        assertTrue(Status.isValidStatus("done"));
    }

    @Test
    void isProcessing() {
    }

    @Test
    void isNotProcessing() {
    }

    @Test
    void isDone() {
    }

    @Test
    void testToString() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
    }
}