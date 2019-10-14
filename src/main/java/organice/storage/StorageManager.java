package organice.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import organice.commons.core.LogsCenter;
import organice.commons.exceptions.DataConversionException;
import organice.model.ReadOnlyOrganice;
import organice.model.ReadOnlyUserPrefs;
import organice.model.UserPrefs;

/**
 * Manages storage of Organice data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private OrganiceStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(OrganiceStorage addressBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ Organice methods ==============================

    @Override
    public Path getOrganiceFilePath() {
        return addressBookStorage.getOrganiceFilePath();
    }

    @Override
    public Optional<ReadOnlyOrganice> readOrganice() throws DataConversionException, IOException {
        return readOrganice(addressBookStorage.getOrganiceFilePath());
    }

    @Override
    public Optional<ReadOnlyOrganice> readOrganice(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readOrganice(filePath);
    }

    @Override
    public void saveOrganice(ReadOnlyOrganice addressBook) throws IOException {
        saveOrganice(addressBook, addressBookStorage.getOrganiceFilePath());
    }

    @Override
    public void saveOrganice(ReadOnlyOrganice addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveOrganice(addressBook, filePath);
    }

}
