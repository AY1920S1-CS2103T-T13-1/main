package organice.testutil;

import organice.model.AddressBook;
import organice.model.person.Person;

/**
 * A utility class to help with building Organice objects.
 * Example usage: <br>
 *     {@code Organice og = new OrganiceBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Person} to the {@code Organice} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        addressBook.addPerson(person);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
