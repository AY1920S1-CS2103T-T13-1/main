package organice.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import organice.commons.exceptions.IllegalValueException;
import organice.model.Organice;
import organice.model.ReadOnlyOrganice;
import organice.model.person.Person;

/**
 * An Immutable Organice that is serializable to JSON format.
 */
@JsonRootName(value = "organice")
class JsonSerializableOrganice {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableOrganice} with the given persons.
     */
    @JsonCreator
    public JsonSerializableOrganice(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyOrganice} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableOrganice}.
     */
    public JsonSerializableOrganice(ReadOnlyOrganice source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this organice into the model's {@code Organice} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Organice toModelType() throws IllegalValueException {
        Organice addressBook = new Organice();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
        }
        return addressBook;
    }

}
