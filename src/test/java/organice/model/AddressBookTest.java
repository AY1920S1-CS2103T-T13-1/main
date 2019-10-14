package organice.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static organice.testutil.Assert.assertThrows;
import static organice.testutil.TypicalPersons.DOCTOR_ALICE;
import static organice.testutil.TypicalPersons.getTypicalOrganice;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import organice.model.person.Person;
import organice.model.person.exceptions.DuplicatePersonException;
import organice.testutil.PersonBuilder;

public class OrganiceTest {

    private final Organice addressBook = new Organice();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyOrganice_replacesData() {
        Organice newData = getTypicalOrganice();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(DOCTOR_ALICE).withNric("S1532142A").build();
        List<Person> newPersons = Arrays.asList(DOCTOR_ALICE, editedAlice);
        OrganiceStub newData = new OrganiceStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInOrganice_returnsFalse() {
        assertFalse(addressBook.hasPerson(DOCTOR_ALICE));
    }

    @Test
    public void hasPerson_personInOrganice_returnsTrue() {
        addressBook.addPerson(DOCTOR_ALICE);
        assertTrue(addressBook.hasPerson(DOCTOR_ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInOrganice_returnsTrue() {
        addressBook.addPerson(DOCTOR_ALICE);
        Person editedAlice = new PersonBuilder(DOCTOR_ALICE).withNric("S1532142A").build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
    }

    /**
     * A stub ReadOnlyOrganice whose persons list can violate interface constraints.
     */
    private static class OrganiceStub implements ReadOnlyOrganice {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        OrganiceStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }
    }

}
