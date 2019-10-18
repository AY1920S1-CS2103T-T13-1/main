package organice.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import organice.commons.core.LogsCenter;
import organice.commons.exceptions.DataConversionException;
import organice.commons.exceptions.IllegalValueException;
import organice.commons.util.FileUtil;
import organice.commons.util.JsonUtil;
import organice.model.ReadOnlyOrganice;

/**
 * A class to access Organice data stored as a json file on the hard disk.
 */
public class JsonOrganiceStorage implements OrganiceStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonOrganiceStorage.class);

    private Path filePath;

    public JsonOrganiceStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getOrganiceFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyOrganice> readOrganice() throws DataConversionException {
        return readOrganice(filePath);
    }

    /**
     * Similar to {@link #readOrganice()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyOrganice> readOrganice(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableOrganice> jsonOrganice = JsonUtil.readJsonFile(
                filePath, JsonSerializableOrganice.class);
        if (!jsonOrganice.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonOrganice.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveOrganice(ReadOnlyOrganice addressBook) throws IOException {
        saveOrganice(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveOrganice(ReadOnlyOrganice)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveOrganice(ReadOnlyOrganice addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableOrganice(addressBook), filePath);
    }

}
