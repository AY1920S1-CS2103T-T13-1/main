package organice.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import organice.commons.exceptions.IllegalValueException;
import organice.model.person.Age;
import organice.model.person.BloodType;
import organice.model.person.Donor;
import organice.model.person.Name;
import organice.model.person.Nric;
import organice.model.person.Organ;
import organice.model.person.OrganExpiryDate;
import organice.model.person.Person;
import organice.model.person.Phone;
import organice.model.person.Status;
import organice.model.person.TissueType;
import organice.model.person.Type;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedDonor extends JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Patient's %s field is missing!";

    protected final String age;
    protected final String bloodType;
    protected final String tissueType;
    protected final String organ;
    protected final String organExpiryDate;
    protected final String status;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedDonor(@JsonProperty("type") String type, @JsonProperty("nric") String nric,
            @JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("age") String age, @JsonProperty("bloodType") String bloodType,
            @JsonProperty("tissueType") String tissueType, @JsonProperty("organ") String organ,
            @JsonProperty("organExpiryDate") String organExpiryDate, @JsonProperty("status") String status) {

        super(type, nric, name, phone);

        this.age = age;
        this.bloodType = bloodType;
        this.tissueType = tissueType;
        this.organ = organ;
        this.organExpiryDate = organExpiryDate;
        this.status = status;
    }

    /**
     * Converts a given {@code Donor} into this class for Jackson use.
     */
    public JsonAdaptedDonor(Donor source) {
        super(source.getType().value, source.getNric().value, source.getName().fullName, source.getPhone().value);


        age = source.getAge().value;
        bloodType = source.getBloodType().value;
        tissueType = source.getTissueType().value;
        organ = source.getOrgan().value;
        organExpiryDate = ((Donor) source).getOrganExpiryDate().value;
        status = "not processing";
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

        if (age == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Age.class.getSimpleName()));
        }

        if (!Age.isValidAge(age)) {
            throw new IllegalValueException(Age.MESSAGE_CONSTRAINTS);
        }
        final Age modelAge = new Age(age);

        if (bloodType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    BloodType.class.getSimpleName()));
        }

        if (!BloodType.isValidBloodType(bloodType)) {
            throw new IllegalValueException(BloodType.MESSAGE_CONSTRAINTS);
        }
        final BloodType modelBloodType = new BloodType(bloodType);

        if (tissueType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TissueType.class.getSimpleName()));
        }

        if (!TissueType.isValidTissueType(tissueType)) {
            throw new IllegalValueException(TissueType.MESSAGE_CONSTRAINTS);
        }
        final TissueType modelTissueType = new TissueType(tissueType);

        if (organ == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Organ.class.getSimpleName()));
        }

        if (!Organ.isValidOrgan(organ)) {
            throw new IllegalValueException(Organ.MESSAGE_CONSTRAINTS);
        }
        final Organ modelOrgan = new Organ(organ);

        if (organExpiryDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    OrganExpiryDate.class.getSimpleName()));
        }

        if (!OrganExpiryDate.isValidExpiryDate(organExpiryDate)) {
            throw new IllegalValueException(OrganExpiryDate.MESSAGE_CONSTRAINTS);
        }
        final OrganExpiryDate modelOrganExpiryDate = new OrganExpiryDate(organExpiryDate);

        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Status.class.getSimpleName()));
        }

        if (!Status.isValidStatus(status)) {
            throw new IllegalValueException(Status.MESSAGE_CONSTRAINTS);
        }
        final Status modelStatus = new Status(status);

        return new Donor(modelType, modelNric, modelName, modelPhone, modelAge, modelBloodType, modelTissueType,
                modelOrgan, modelOrganExpiryDate, modelStatus);
    }
}
