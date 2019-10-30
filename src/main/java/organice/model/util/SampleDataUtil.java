package organice.model.util;

import organice.model.AddressBook;
import organice.model.ReadOnlyAddressBook;
import organice.model.person.Age;
import organice.model.person.BloodType;
import organice.model.person.Doctor;
import organice.model.person.DoctorInCharge;
import organice.model.person.Donor;
import organice.model.person.Name;
import organice.model.person.Nric;
import organice.model.person.Organ;
import organice.model.person.OrganExpiryDate;
import organice.model.person.Patient;
import organice.model.person.Person;
import organice.model.person.Phone;
import organice.model.person.Priority;
import organice.model.person.Status;
import organice.model.person.TissueType;
import organice.model.person.Type;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */

public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
                new Doctor(new Type("doctor"), new Nric("S1111111A"), new Name("Alex Yeoh"), new Phone("87438807")),
                new Donor(new Type("donor"), new Nric("S4444444R"), new Name("John Doe Donor 2"),
                          new Phone("99999999"), new Age("17"), new BloodType("A"),
                          new TissueType("1,2,3,4,5,6"), new Organ("kidney"),
                          new OrganExpiryDate("23-Oct-2019"), new Status("not processing")),
                new Donor(new Type("donor"), new Nric("S1212121A"), new Name("John Doe Donor"),
                          new Phone("98799879"), new Age("30"), new BloodType("O"),
                          new TissueType("1,2,3,4,7,8"), new Organ("kidney"),
                          new OrganExpiryDate("24-Oct-2019"), new Status("not processing")),
                new Patient(new Type("patient"), new Nric("S1234568R"), new Name("John Doe"), new Phone("98765432"),
                            new Age("21"), new Priority("high"), new BloodType("A"), new TissueType("1,2,3,4,5,6"),
                            new Organ("kidney"), new DoctorInCharge("S1111111A"), new Status("not processing")),
                new Patient(new Type("patient"), new Nric("S4444444G"), new Name("Sam"), new Phone("999988888"),
                            new Age("19"), new Priority("medium"), new BloodType("AB"), new TissueType("2,3,4,5,6,7"),
                            new Organ("kidney"), new DoctorInCharge("S1111111A"), new Status("not processing")),
                new Patient(new Type("patient"), new Nric("S9988776G"), new Name("Roy Kim"), new Phone("99944888"),
                            new Age("33"), new Priority("low"), new BloodType("O"), new TissueType("6,7,8,9,10,11"),
                            new Organ("kidney"), new DoctorInCharge("S1111111A"), new Status("not processing"))

        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }
}
