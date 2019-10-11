package seedu.address.testutil;

import seedu.address.model.person.Age;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Patient;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Priority;
import seedu.address.model.person.Type;

/**
 * A utility class to build Patient objects.
 */
public class PatientBuilder extends PersonBuilder {
    public static final String DEFAULT_AGE = "20";
    public static final String DEFAULT_PRIORITY = "high";

    private Age age;
    private Priority priority;

    public PatientBuilder() {
        super();
        age = new Age(DEFAULT_AGE);
        type = new Type("patient");
        priority = new Priority(DEFAULT_PRIORITY);
    }

    /**
     * Initialises the PatientBuilder with the data of {@code patientToCopy}
     */
    public PatientBuilder(Patient patientToCopy) {
        super(patientToCopy);
        age = patientToCopy.getAge();
        priority = patientToCopy.getPriority();
    }

    /**
     * Sets the {@code Age} of the {@code Patient} we are building
     */
    public PatientBuilder withAge(String age) {
        this.age = new Age(age);
        return this;
    }

    /**
     * Sets the {@code Nric} of the {@code Patient} that we are building.
     */
    @Override
    public PatientBuilder withNric(String nric) {
        this.nric = new Nric(nric);
        return this;
    }
    /**
     * Sets the {@code Name} of the {@code Patient} that we are building.
     */
    @Override
    public PatientBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Patient} that we are building.
     */
    @Override
    public PatientBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code Patient} that we are building.
     */
    public PatientBuilder withPriority(String priority) {
        this.priority = new Priority(priority);
        return this;
    }

    public Patient build() {
        return new Patient(type, nric, name, phone, age, priority);
    }
}
