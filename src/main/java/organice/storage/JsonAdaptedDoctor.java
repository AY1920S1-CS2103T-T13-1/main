package organice.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import organice.commons.exceptions.IllegalValueException;
import organice.model.person.Doctor;
import organice.model.person.Name;
import organice.model.person.Nric;
import organice.model.person.Person;
import organice.model.person.Phone;
import organice.model.person.Type;

/**
 * Jackson-friendly version of {@link Doctor}.
 */
class JsonAdaptedDoctor extends JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Doctor's %s field is missing!";

    /**
     * Constructs a {@code JsonAdaptedDoctor} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedDoctor(@JsonProperty("type") String type, @JsonProperty("nric") String nric,
            @JsonProperty("name") String name, @JsonProperty("phone") String phone) {
        super(type, nric, name, phone);
    }

    /**
     * Converts a given {@code Doctor} into this class for Jackson use.
     */
    public JsonAdaptedDoctor(Doctor source) {
        super(source.getType().value, source.getNric().value, source.getName().fullName, source.getPhone().value);
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    @Override
    public Person toModelType() throws IllegalValueException {
        if (type == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Type.class.getSimpleName()));
        }
        if (!Type.isValidType(type)) {
            throw new IllegalValueException(Type.MESSAGE_CONSTRAINTS);
        }

        final Type modelType = new Type(type);

        if (nric == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName()));
        }
        if (!Nric.isValidNric(nric)) {
            throw new IllegalValueException(Nric.MESSAGE_CONSTRAINTS);
        }
        final Nric modelNric = new Nric(nric);

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        return new Doctor(modelType, modelNric, modelName, modelPhone);
    }
}
