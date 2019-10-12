package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_PERSON_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_PERSON_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_PERSON_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_PERSON_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_PERSON_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.testutil.EditPersonDescriptorBuilder;

public class EditPersonDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditPersonDescriptor descriptorWithSameValues = new EditPersonDescriptor(DESC_PERSON_AMY);
        assertTrue(DESC_PERSON_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_PERSON_AMY.equals(DESC_PERSON_AMY));

        // null -> returns false
        assertFalse(DESC_PERSON_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_PERSON_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_PERSON_AMY.equals(DESC_PERSON_BOB));

        // different nric -> returns false
        EditPersonDescriptor editedAmy = new EditPersonDescriptorBuilder(DESC_PERSON_AMY)
                .withName(VALID_NRIC_PERSON_BOB).build();
        assertFalse(DESC_PERSON_AMY.equals(editedAmy));

        // different name -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_PERSON_AMY).withName(VALID_NAME_PERSON_BOB).build();
        assertFalse(DESC_PERSON_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_PERSON_AMY).withPhone(VALID_PHONE_PERSON_BOB).build();
        assertFalse(DESC_PERSON_AMY.equals(editedAmy));

    }
}
