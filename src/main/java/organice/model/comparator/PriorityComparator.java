package organice.model.comparator;

import java.util.Comparator;

import organice.model.person.Patient;
import organice.model.person.Person;

/**
 * Comparator class for the priority so that the patients will be sorted against their priority.
 */
public class PriorityComparator implements Comparator<Person> {
    public int compare(Person a, Person b) {
        Patient aPatient = (Patient) a;
        Patient bPatient = (Patient) b;

        if (aPatient.getPriority().isLowPriority()) {
            if (bPatient.getPriority().isMediumPriority() || bPatient.getPriority().isHighPriority()) {
                return -1;
            } else {
                return 0;
            }
        } else if (aPatient.getPriority().isMediumPriority()) {
            if (bPatient.getPriority().isHighPriority()) {
                return -1;
            } else if (bPatient.getPriority().isLowPriority()) {
                return 1;
            } else {
                return 0;
            }
        } else { //aPatient will be high priority
            if (bPatient.getPriority().isMediumPriority() || bPatient.getPriority().isLowPriority()) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}

