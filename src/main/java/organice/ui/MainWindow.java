package organice.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import organice.commons.core.GuiSettings;
import organice.commons.core.LogsCenter;
import organice.logic.Logic;
import organice.logic.commands.Command;
import organice.logic.commands.CommandResult;
import organice.logic.commands.exceptions.CommandException;
import organice.logic.parser.exceptions.ParseException;
import organice.model.person.Name;
import organice.model.person.Nric;
import organice.model.person.Phone;
import organice.model.person.Type;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private DoctorForm doctorForm;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        doctorForm = new DoctorForm();

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }

    private CommandResult getName(String commandText) {
        if (!Name.isValidName(commandText)) {
            resultDisplay.setFeedbackToUser(Name.MESSAGE_CONSTRAINTS);
            return new CommandResult(commandText);
        }
        doctorForm.setName(new Name(commandText));
        CommandBox commandBox = new CommandBox(this::getNric);
        commandBoxPlaceholder.getChildren().clear();
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
        return new CommandResult(commandText);
    }

    private CommandResult getNric(String commandText) {
        if (!Nric.isValidNric(commandText)) {
            resultDisplay.setFeedbackToUser(Nric.MESSAGE_CONSTRAINTS);
            return new CommandResult(commandText);
        }
        doctorForm.setNric(new Nric(commandText));
        CommandBox commandBox = new CommandBox(this::getPhone);
        commandBoxPlaceholder.getChildren().clear();
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
        return new CommandResult(commandText);
    }

    private CommandResult getPhone(String commandText) {
        if (!Phone.isValidPhone(commandText)) {
            resultDisplay.setFeedbackToUser(Phone.MESSAGE_CONSTRAINTS);
            return new CommandResult(commandText);
        }
        doctorForm.setPhone(new Phone(commandText));
        CommandBox commandBox = new CommandBox(this::addDone);
        commandBoxPlaceholder.getChildren().clear();
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
        return new CommandResult(commandText);
    }

    private CommandResult addDone(String commandText) throws ParseException, CommandException{
        if (commandText.equals("/done")) {
            String command = "add t/doctor " + "n/" + doctorForm.getName().getText() + " ic/" + doctorForm.getNric().getText()
                    + " p/" + doctorForm.getPhone().getText();
            CommandResult commandResult = logic.execute(command);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
            personListPanelPlaceholder.getChildren().clear();
            personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
            commandBoxPlaceholder.getChildren().clear();
            commandBoxPlaceholder.getChildren().add(new CommandBox(this::executeCommand).getRoot());
            return new CommandResult(commandText);
        }
        return new CommandResult(commandText);
    }

    /**
     * Executes the command and returns the result.
     *
     * @see organice.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isForm()) {
                CommandBox commandBox = new CommandBox(this::getName);
                commandBoxPlaceholder.getChildren().clear();
                commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
                Type formType = commandResult.getFormType();
                if (formType.isDoctor()) {
                    doctorForm = new DoctorForm();
                    personListPanelPlaceholder.getChildren().clear();
                    personListPanelPlaceholder.getChildren().add(doctorForm.getRoot());
                }
            }

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
