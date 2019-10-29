package organice.model.Comparator;

import java.util.Comparator;

import organice.model.person.Donor;

public class ExpiryDateComparator implements Comparator<Donor> {

    @Override
    public int compare(Donor d1, Donor d2) {
        return d1.getOrganExpiryDate().compareTo(d2.getOrganExpiryDate());
    }
}
