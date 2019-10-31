package organice.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import javafx.collections.ObservableList;
import organice.commons.exceptions.IllegalValueException;
import organice.model.AddressBook;
import organice.model.ReadOnlyAddressBook;
import organice.model.person.Doctor;
import organice.model.person.Donor;
import organice.model.person.Patient;
import organice.model.person.Person;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("patient") List<JsonAdaptedPatient> patients,
            @JsonProperty("donor") List<JsonAdaptedDonor> donors,
            @JsonProperty("doctor") List<JsonAdaptedDoctor> doctors) {
        persons.addAll(patients);
        persons.addAll(donors);
        persons.addAll(doctors);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        //persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));

        ObservableList<Person> personList = source.getPersonList();

        for (Person person : personList) {
            if (person instanceof Donor) {
                persons.add(new JsonAdaptedDonor((Donor) person));
            } else if (person instanceof Doctor) {
                persons.add(new JsonAdaptedDoctor((Doctor) person));
            } else if (person instanceof Patient) {
                persons.add(new JsonAdaptedPatient((Patient) person));
            } else {
                assert true : "personList should not contain objects that are not Donor, Doctor, Patient!";
            }
        }
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person;
            if (jsonAdaptedPerson instanceof JsonAdaptedPatient) {
                person = ((JsonAdaptedPatient) jsonAdaptedPerson).toModelType();
            } else if (jsonAdaptedPerson instanceof JsonAdaptedDoctor) {
                person = ((JsonAdaptedDoctor) jsonAdaptedPerson).toModelType();
            } else if (jsonAdaptedPerson instanceof JsonAdaptedDonor) {
                person = ((JsonAdaptedDonor) jsonAdaptedPerson).toModelType();
            } else {
                throw new IllegalValueException("Should not have types that are not Patient, "
                        + "Donor or Doctor!");
            }

            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
        }
        return addressBook;
    }

}
