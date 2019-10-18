package organice.testutil;

import organice.model.Organice;
import organice.model.person.Person;

/**
 * A utility class to help with building Organice objects.
 * Example usage: <br>
 *     {@code Organice og = new OrganiceBuilder().withPerson("John", "Doe").build();}
 */
public class OrganiceBuilder {

    private Organice addressBook;

    public OrganiceBuilder() {
        addressBook = new Organice();
    }

    public OrganiceBuilder(Organice addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Person} to the {@code Organice} that we are building.
     */
    public OrganiceBuilder withPerson(Person person) {
        addressBook.addPerson(person);
        return this;
    }

    public Organice build() {
        return addressBook;
    }
}
