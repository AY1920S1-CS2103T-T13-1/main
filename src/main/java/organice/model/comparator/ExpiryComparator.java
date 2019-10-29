package organice.model.comparator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import organice.model.person.Donor;
import organice.model.person.Person;

/**
 * Comparator class for the expiry so that the donors will be sorted against time.
 */
public class ExpiryComparator implements Comparator<Person> {
    Date aDate;
    Date bDate;
    public int compare(Person a, Person b) {
        try {
            Donor aDonor = (Donor) a;
            Donor bDonor = (Donor) b;
            aDate = new SimpleDateFormat("dd MMM yyyy").parse(aDonor.getOrganExpiryDate().toString());
            bDate = new SimpleDateFormat("dd MMM yyyy").parse(bDonor.getOrganExpiryDate().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (aDate.before(bDate)) {
            return -1;
        } else if (aDate.after(bDate)) {
            return 1;
        } else {
            return 0;
        }
    }
}

