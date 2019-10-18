package organice.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static organice.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import organice.commons.exceptions.IllegalValueException;
import organice.commons.util.JsonUtil;
import organice.model.Organice;
import organice.testutil.TypicalPersons;

public class JsonSerializableOrganiceTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableOrganiceTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsOrganice.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonOrganice.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonOrganice.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableOrganice dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableOrganice.class).get();
        Organice addressBookFromFile = dataFromFile.toModelType();
        Organice typicalPersonsOrganice = TypicalPersons.getTypicalOrganice();
        assertEquals(addressBookFromFile, typicalPersonsOrganice);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableOrganice dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableOrganice.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableOrganice dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableOrganice.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableOrganice.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

}
