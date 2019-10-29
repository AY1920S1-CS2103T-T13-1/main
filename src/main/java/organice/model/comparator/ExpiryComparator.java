package organice.model.comparator;

import java.util.Comparator;

import organice.model.person.Donor;

/**
 * Comparator class for the expiry so that the donors will be sorted against time.
 */
public class ExpiryComparator implements Comparator<Donor> {
    public int compare(Donor a, Donor b) {
        if (a.getOrganExpiryDate() < b.getOrganExpiryDate()) {
            return -1;
        }
}

