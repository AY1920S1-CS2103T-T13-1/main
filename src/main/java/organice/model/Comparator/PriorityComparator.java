package organice.model.Comparator;

import java.util.Comparator;

import organice.model.person.MatchedPatient;

public class PriorityComparator implements Comparator<MatchedPatient> {
    @Override
    public int compare(MatchedPatient o1, MatchedPatient o2) {
        return o1.getPriority().compareTo(o2.getPriority());
    }
}
