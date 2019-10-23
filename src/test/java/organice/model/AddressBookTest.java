package organice.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static organice.testutil.Assert.assertThrows;
import static organice.testutil.TypicalPersons.DOCTOR_ALICE;
import static organice.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import organice.model.person.Person;
import organice.model.person.exceptions.DuplicatePersonException;
import organice.testutil.DoctorBuilder;
import organice.testutil.PersonBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(DOCTOR_ALICE).withNric("S1532142A").build();
        List<Person> newPersons = Arrays.asList(DOCTOR_ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        Person person = null;
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(person));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(DOCTOR_ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(DOCTOR_ALICE);
        assertTrue(addressBook.hasPerson(DOCTOR_ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(DOCTOR_ALICE);
        Person editedAlice = new PersonBuilder(DOCTOR_ALICE).withNric("S1532142A").build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void hasDoctor_nullDoctor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasDoctor(null));
    }

    @Test
    public void hasDoctor_doctorNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasDoctor(DOCTOR_ALICE.getNric()));
    }

    @Test
    public void hasDoctor_doctorInAddressBook_returnsTrue() {
        addressBook.addPerson(DOCTOR_ALICE);
        assertTrue(addressBook.hasDoctor(DOCTOR_ALICE.getNric()));
    }

    @Test
    public void hasDoctor_doctorWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(DOCTOR_ALICE);
        Person editedAlice = new DoctorBuilder(DOCTOR_ALICE).withNric(DOCTOR_ALICE.getNric().toString()).build();
        assertTrue(addressBook.hasDoctor(editedAlice.getNric()));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }
    }

}
