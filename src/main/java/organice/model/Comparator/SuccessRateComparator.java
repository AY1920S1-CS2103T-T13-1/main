package organice.model.Comparator;

import java.util.Comparator;

import organice.model.person.MatchedDonor;

/**
 * This comparator is used to sort a list of matched donors according to the success rate in descending order.
 * Order: From high to low success rates.
 */
public class SuccessRateComparator implements Comparator<MatchedDonor> {
    @Override
    public int compare(MatchedDonor o1, MatchedDonor o2) {
        return o2.getSuccessRate().compareTo(o1.getSuccessRate());
    }
}
