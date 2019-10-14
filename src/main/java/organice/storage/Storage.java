package organice.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import organice.commons.exceptions.DataConversionException;
import organice.model.ReadOnlyOrganice;
import organice.model.ReadOnlyUserPrefs;
import organice.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends OrganiceStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getOrganiceFilePath();

    @Override
    Optional<ReadOnlyOrganice> readOrganice() throws DataConversionException, IOException;

    @Override
    void saveOrganice(ReadOnlyOrganice addressBook) throws IOException;

}
