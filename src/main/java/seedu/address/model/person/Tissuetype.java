package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's tissue type in ORGANice.
 * Guarantees: immutable; is valid as declared in {@link #isValidTissuetype(String)}
 */
public class Tissuetype {

    public static final String MESSAGE_CONSTRAINTS =
            "Tissue types are 6 integers from 1 to 12 separated by commas"
                + " An example will be tt/1,2,3,4,5,6";
    public final String value;

    /**
     * Constructs a {@code Tissuetype}.
     *
     * @param tissuetype A valid tissue type
     */
    public Tissuetype (String tissuetype) {
        requireNonNull(tissuetype);
        checkArgument(isValidTissuetype(tissuetype), MESSAGE_CONSTRAINTS);
        value = tissuetype.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid tissue type.
     */
    public static boolean isValidTissuetype(String test) {
        String[] tissuetypeValue = test.split(",");
        if (tissuetypeValue.length != 6) {
            return false;
        } else {
            for (int i = 0; i < tissuetypeValue.length; i++) {
                try {
                    Integer tt = Integer.parseInt(tissuetypeValue[i]);
                } catch (NumberFormatException | NullPointerException nfe) {
                    return false;
                }
            }
            return true;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Tissuetype // instanceof handles nulls
                && value.equals(((Tissuetype) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
