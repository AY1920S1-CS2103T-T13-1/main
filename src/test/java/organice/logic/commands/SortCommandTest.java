package organice.logic.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import organice.logic.commands.CommandResult;
import organice.logic.commands.SortCommand;
import organice.logic.parser.SortCommandParser;
import organice.model.Model;
import organice.model.ModelManager;
import organice.model.ModelStub;
import organice.model.UserPrefs;
import organice.model.person.Type;

import static organice.logic.commands.CommandTestUtil.assertCommandSuccess;
import static organice.testutil.Assert.assertThrows;
import static organice.testutil.TypicalPersons.getTypicalAddressBook;

class SortCommandTest {
    private static final Type TYPE_PATIENT = new Type("patient");
    private static final Type TYPE_DONOR = new Type("donor");

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void constructor_nullCategory_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SortCommand(null));
    }

    @Test
    void execute_sortByOrganExpiryDate_success() throws Exception {
        //put people inside model


        //create new commandresult, where the input is the model.
        String input = SortCommand.COMMAND_WORD + SortCommandParser.ORGAN_EXPIRY_DATE;
        SortCommand command = new SortCommand(input);
        CommandResult commandResult = command.execute(modelStub);
        CommandResult expectedCommandResult = new CommandResult(SortCommand.MESSAGE_SUCCESS);
        expectedCommandResult.setSort(true);

        //check model by creating a list of MatchedDonors, sorted by organ expiry date.
        expectedModel.sortByOrganExpiryDate();
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);

    }

    @Test
    void execute_sortByPriority_success() throws Exception {

        String input = SortCommand.COMMAND_WORD + SortCommandParser.PRIORITY;
        SortCommand command = new SortCommand(input);
        CommandResult commandResult = command.execute(modelStub);
        CommandResult expectedCommandResult = new CommandResult(SortCommand.MESSAGE_SUCCESS);
        expectedCommandResult.setSort(true);
        expectedModel.sortByPriority();
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
    }

    @Test
    void execute_sortBySuccessRate_success() throws Exception {

        String input = SortCommand.COMMAND_WORD + SortCommandParser.SUCCESS_RATE;
        SortCommand command = new SortCommand(input);
        CommandResult commandResult = command.execute(modelStub);
        CommandResult expectedCommandResult = new CommandResult(SortCommand.MESSAGE_SUCCESS);
        expectedCommandResult.setSort(true);
        expectedModel.sortBySuccessRate();
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
    }

    @Test
    void testEquals() {
    }
}
