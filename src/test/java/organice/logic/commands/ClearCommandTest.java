package organice.logic.commands;

import static organice.logic.commands.CommandTestUtil.assertCommandSuccess;
import static organice.testutil.TypicalPersons.getTypicalOrganice;

import org.junit.jupiter.api.Test;

import organice.model.Organice;
import organice.model.Model;
import organice.model.ModelManager;
import organice.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyOrganice_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyOrganice_success() {
        Model model = new ModelManager(getTypicalOrganice(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalOrganice(), new UserPrefs());
        expectedModel.setOrganice(new Organice());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
