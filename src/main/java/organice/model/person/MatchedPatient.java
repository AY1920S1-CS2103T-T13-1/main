package organice.model.person;

import java.util.Objects;

/**
 * Represents a Person in ORGANice.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class MatchedPatient extends Donor {

    /**
     * Every field must be present and not null.
     */
    public MatchedPatient(Type type, Nric nric, Name name, Phone phone, Age age,
            BloodType bloodType, TissueType tissueType, Organ organ, OrganExpiryDate organExpiryDate) {
        super(type, nric, name, phone, age, bloodType, tissueType, organ, organExpiryDate);
    }

    /**
     * Alternative constructor which takes in a {@code Donor} and constructs a {@code MatchedDonor}
     */
    public MatchedPatient(Donor toAdd) {
        this(toAdd.getType(), toAdd.getNric(), toAdd.getName(), toAdd.getPhone(), toAdd.getAge(), toAdd.getBloodType(),
                toAdd.getTissueType(), toAdd.getOrgan(), toAdd.getOrganExpiryDate());
    }
    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MatchedPatient)) {
            return false;
        }

        MatchedPatient otherPerson = (MatchedPatient) other;
        return otherPerson.getNric().equals(getNric())
                && otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getType().equals(getType());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(type, nric, name, phone);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Person Type: ")
                .append(getType())
                .append(" Nric: ")
                .append(getNric())
                .append(" Phone: ")
                .append(getPhone());
        return builder.toString();
    }

}
