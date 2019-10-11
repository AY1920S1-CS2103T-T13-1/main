package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TissuetypeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tissuetype(null));
    }

    @Test
    public void constructor_invalidTissuetype_throwsIllegalArgumentException() {
        String invalidTissuetype = "";
        assertThrows(IllegalArgumentException.class, () -> new Tissuetype(invalidTissuetype));
    }

    @Test
    public void isValidTissuetype() {
        // null tissuetype
        assertThrows(NullPointerException.class, () -> Tissuetype.isValidTissuetype(null));

        // invalid tissuetype
        assertFalse(Tissuetype.isValidTissuetype("")); // empty string
        assertFalse(Tissuetype.isValidTissuetype(" ")); // spaces only
        assertFalse(Tissuetype.isValidTissuetype("a,b,c,d,e,f")); // wrong tissuetype
        assertFalse(Tissuetype.isValidTissuetype("1")); // single numeric
        assertFalse(Tissuetype.isValidTissuetype("1,,2,,3,,4,,5,,6")); // wrong usage of commas
        assertFalse(Tissuetype.isValidTissuetype("1 2 3 4 5 6")); // spaces within tissuetype

        // valid tissuetype
        assertTrue(Tissuetype.isValidTissuetype("1,2,3,4,5,6")); // exactly 6 tissuetype
        assertTrue(Tissuetype.isValidTissuetype("6,7,8,9,10,11")); //2 digits tissuetype
    }

    @Test
    public void toStringTest() {
        assertEquals(new Tissuetype("1,2,3,4,5,6").toString(), "1,2,3,4,5,6");
        assertEquals(new Tissuetype("10,11,12,13,14,15").toString(), "10,11,12,13,14,15");
    }

    @Test
    public void equals() {
        Tissuetype tissuetype = new Tissuetype("1,2,3,4,5,6");

        assertFalse(tissuetype.equals(null));
        assertFalse(tissuetype.equals(new Tissuetype("1,2,3,4,5,7")));
        assertTrue(tissuetype.equals(tissuetype));
        assertTrue(tissuetype.equals(new Tissuetype("1,2,3,4,5,6")));
    }

    @Test
    public void hashCodeTest() {
        Tissuetype tissuetype = new Tissuetype("2,4,6,8,10,12");

        assertEquals(tissuetype.hashCode(), new Tissuetype("2,4,6,8,10,12").hashCode());
        assertNotEquals(tissuetype.hashCode(), new Tissuetype("1,3,5,7,9,11").hashCode());
    }
}
