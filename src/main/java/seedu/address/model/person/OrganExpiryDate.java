package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

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

    public static final Map<Integer, Integer> NUMBER_OF_DAYS_IN_MONTHS = new HashMap<>() {{
            put(1, 31);
            put(2, 29);
            put(3, 31);
            put(4, 30);
            put(5, 31);
            put(6, 30);
            put(7, 31);
            put(8, 31);
            put(9, 30);
            put(10, 31);
            put(11, 30);
            put(12, 31);
        }};
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
        checkArgument(isValidExpiryDate(expiryDate), MESSAGE_CONSTRAINTS);
        value = expiryDate.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid organ's expiry date.
     */
    public static boolean isValidExpiryDate(String test) {
        try {
            test = test.substring(0, 3) + test.substring(3, 4).toUpperCase()
                    + test.substring(4); //Make the first letter of the month capital so that it can be parsed.
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
            LocalDate date = LocalDate.parse(test, formatter); //this parse will allow 31-Feb-YYYY

            int day = Integer.parseInt(test.split("-")[0]);
            int month = date.getMonthValue();
            if (day > NUMBER_OF_DAYS_IN_MONTHS.get(month)) { //check if day is more than number of days in that month
                return false;
            }
            if (day > 28 && month == 2 && !date.isLeapYear()) { //check for leap year
                return false;
            }
            if (date.isBefore(LocalDate.now())) { //check if inputted date is before today's date.
                return false;
            }
            return true;
        } catch (StringIndexOutOfBoundsException | NumberFormatException | DateTimeParseException e) {
            return false;
        } catch (Exception e) {
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
