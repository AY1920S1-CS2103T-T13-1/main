package organice.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import organice.commons.exceptions.DataConversionException;
import organice.model.ReadOnlyOrganice;

/**
 * Represents a storage for {@link organice.model.Organice}.
 */
public interface OrganiceStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getOrganiceFilePath();

    /**
     * Returns Organice data as a {@link ReadOnlyOrganice}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyOrganice> readOrganice() throws DataConversionException, IOException;

    /**
     * @see #getOrganiceFilePath()
     */
    Optional<ReadOnlyOrganice> readOrganice(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyOrganice} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveOrganice(ReadOnlyOrganice addressBook) throws IOException;

    /**
     * @see #saveOrganice(ReadOnlyOrganice)
     */
    void saveOrganice(ReadOnlyOrganice addressBook, Path filePath) throws IOException;

}
