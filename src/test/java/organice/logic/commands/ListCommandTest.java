package organice.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static organice.logic.commands.CommandTestUtil.assertCommandSuccess;
import static organice.logic.commands.CommandTestUtil.showPersonAtIndex;
import static organice.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static organice.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import organice.model.Model;
import organice.model.ModelManager;
import organice.model.UserPrefs;
import organice.model.person.Type;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private Type type;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        type = new Type("patient");
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(null), model, ListCommand.TYPE_NOT_FOUND, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(null), model, ListCommand.TYPE_NOT_FOUND, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsPatients() {
        type = new Type("patient");
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PATIENTS);
        assertCommandSuccess(new ListCommand(type), model, ListCommand.LIST_OF_PATIENTS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsDonors() {
        type = new Type("donor");
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_DONORS);
        assertCommandSuccess(new ListCommand(type), model, ListCommand.LIST_OF_DONORS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsDoctors() {
        type = new Type("doctor");
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_DOCTORS);
        assertCommandSuccess(new ListCommand(type), model, ListCommand.LIST_OF_DOCTORS, expectedModel);
    }

    @Test
    public void equals() {
        ListCommand listCommand = new ListCommand(type);

        // same values -> returns true
        assertTrue(listCommand.equals(new ListCommand(new Type("patient"))));

        // same object -> returns true
        assertTrue(listCommand.equals(listCommand));

        // null -> returns false
        assertFalse(listCommand.equals(null));

        // different type -> returns false
        assertFalse(listCommand.equals(5));

        // different type values -> returns false
        assertFalse(listCommand.equals(new Type("doctor")));
    }
}
