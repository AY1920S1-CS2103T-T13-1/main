package organice.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static organice.testutil.Assert.assertThrows;
import static organice.testutil.TypicalPersons.DOCTOR_ALICE;
import static organice.testutil.TypicalPersons.PERSON_HOON;
import static organice.testutil.TypicalPersons.PERSON_IDA;
import static organice.testutil.TypicalPersons.getTypicalOrganice;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import organice.commons.exceptions.DataConversionException;
import organice.model.Organice;
import organice.model.ReadOnlyOrganice;

public class JsonOrganiceStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonOrganiceStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readOrganice_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readOrganice(null));
    }

    private java.util.Optional<ReadOnlyOrganice> readOrganice(String filePath) throws Exception {
        return new JsonOrganiceStorage(Paths.get(filePath)).readOrganice(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readOrganice("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readOrganice("notJsonFormatOrganice.json"));
    }

    @Test
    public void readOrganice_invalidPersonOrganice_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readOrganice("invalidPersonOrganice.json"));
    }

    @Test
    public void readOrganice_invalidAndValidPersonOrganice_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readOrganice("invalidAndValidPersonOrganice.json"));
    }

    @Test
    public void readAndSaveOrganice_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempOrganice.json");
        Organice original = getTypicalOrganice();
        JsonOrganiceStorage jsonOrganiceStorage = new JsonOrganiceStorage(filePath);

        // Save in new file and read back
        jsonOrganiceStorage.saveOrganice(original, filePath);
        ReadOnlyOrganice readBack = jsonOrganiceStorage.readOrganice(filePath).get();
        assertEquals(original, new Organice(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(PERSON_HOON);
        original.removePerson(DOCTOR_ALICE);
        jsonOrganiceStorage.saveOrganice(original, filePath);
        readBack = jsonOrganiceStorage.readOrganice(filePath).get();
        assertEquals(original, new Organice(readBack));

        // Save and read without specifying file path
        original.addPerson(PERSON_IDA);
        jsonOrganiceStorage.saveOrganice(original); // file path not specified
        readBack = jsonOrganiceStorage.readOrganice().get(); // file path not specified
        assertEquals(original, new Organice(readBack));

    }

    @Test
    public void saveOrganice_nullOrganice_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveOrganice(null, "SomeFile.json"));
    }

    /**
     * Saves {@code organice} at the specified {@code filePath}.
     */
    private void saveOrganice(ReadOnlyOrganice addressBook, String filePath) {
        try {
            new JsonOrganiceStorage(Paths.get(filePath))
                    .saveOrganice(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveOrganice_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveOrganice(new Organice(), null));
    }
}
