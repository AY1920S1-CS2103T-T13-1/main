package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Donor organ's expiry date in ORGANice.
 * Guarantees: immutable; is valid as declared in {@link #isValidExpiryDate(String)} (String)}
 */
public class OrganExpiryDate {

    public static final String MESSAGE_CONSTRAINTS = "Organ's expiry date must be in the format DD-MMM-YYYY"
            + " and is after current date. An example will be 27-Jan-2020";

    public final String value;

    /**
     * Constructs an {@code OrganExpiryDate}.
     *
     * @param expiryDate A valid organ's expiry date.
     */
    public OrganExpiryDate(String expiryDate) {
        requireNonNull(expiryDate);
        try {
            expiryDate = expiryDate.substring(0, 3) + expiryDate.substring(3, 4).toUpperCase()
                    + expiryDate.substring(4).toLowerCase();
            checkArgument(isValidExpiryDate(expiryDate), MESSAGE_CONSTRAINTS);
            value = expiryDate;
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns true if a given string is a valid organ's expiry date.
     */
    public static boolean isValidExpiryDate(String test) {
        DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        formatter.setLenient(false);
        try {
            formatter.parse(test);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof OrganExpiryDate // instanceof handles nulls
            && value.equals(((OrganExpiryDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
