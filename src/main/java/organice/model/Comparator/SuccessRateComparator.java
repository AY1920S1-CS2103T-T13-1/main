package organice.model.Comparator;

import java.util.Comparator;

import organice.model.person.MatchedDonor;

public class SuccessRateComparator implements Comparator<MatchedDonor> {
    @Override
    public int compare(MatchedDonor o1, MatchedDonor o2) {
        return o1.getSuccessRate().compareTo(o2.getSuccessRate());
    }
}
