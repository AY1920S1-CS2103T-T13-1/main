package organice.ui;

import static organice.logic.parser.CliSyntax.PREFIX_NAME;
import static organice.logic.parser.CliSyntax.PREFIX_NRIC;
import static organice.logic.parser.CliSyntax.PREFIX_PHONE;
import static organice.logic.parser.CliSyntax.PREFIX_TYPE;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import organice.logic.commands.AddCommand;
import organice.logic.commands.CommandResult;
import organice.logic.commands.exceptions.CommandException;
import organice.logic.parser.exceptions.ParseException;
import organice.model.Model;
import organice.model.person.FormField;
import organice.model.person.Name;
import organice.model.person.Nric;
import organice.model.person.Phone;
import organice.model.person.Type;

public class FormUiManager {

    private MainWindow mainWindow;
    private Type formType;
    private Model model;

    private static final String PROMPT_NAME = "Enter name:";
    private static final String PROMPT_NRIC = "Enter NRIC:";
    private static final String PROMPT_PHONE = "Enter phone number:";

    private static final String PROMPT_DONE = "Please check again the details.\nType '/done' to confirm, '/abort' to cancel the addition";


    public FormUiManager(MainWindow mainWindow, Type formType, Model model) {
        this.mainWindow = mainWindow;
        this.formType = formType;
        this.model = model;
    }

    private CommandResult getName(String personName) throws ParseException {
        if (!Name.isValidName(personName)) {
            mainWindow.getResultDisplay().setFeedbackToUser(Name.MESSAGE_CONSTRAINTS);
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }

        formFillingAnimation(personName, FormField.NAME);

        getPersonNric();
        return new CommandResult(personName);
    }

    private CommandResult getNric(String personNric) throws ParseException{
        if (!Nric.isValidNric(personNric)) {
            mainWindow.getResultDisplay().setFeedbackToUser(Nric.MESSAGE_CONSTRAINTS);
            throw new ParseException(Nric.MESSAGE_CONSTRAINTS);
        } else if (model.hasDoctor(new Nric(personNric))) {
            mainWindow.getResultDisplay().setFeedbackToUser(AddCommand.MESSAGE_DUPLICATE_PERSON);
            throw new ParseException(AddCommand.MESSAGE_DUPLICATE_PERSON);
        }

        formFillingAnimation(personNric, FormField.NRIC);

        getPersonPhone();
        return new CommandResult(personNric);
    }

    private CommandResult getPhone(String personPhone) throws ParseException {
        if (!Phone.isValidPhone(personPhone)) {
            mainWindow.getResultDisplay().setFeedbackToUser(Phone.MESSAGE_CONSTRAINTS);
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }

        formFillingAnimation(personPhone, FormField.PHONE);

        if (formType.isDoctor()) {
            confirmPersonDetails();
        }
        return new CommandResult(personPhone);
    }

    public void getPersonDetails() {
        mainWindow.getCommandBoxPlaceholder().getChildren().clear();
        mainWindow.getCommandBoxPlaceholder().getChildren().add(new CommandBox(this::getName).getRoot());
        mainWindow.getResultDisplay().setFeedbackToUser(PROMPT_NAME);
    }

    public void getPersonNric() {
        mainWindow.getCommandBoxPlaceholder().getChildren().clear();
        mainWindow.getCommandBoxPlaceholder().getChildren().add(new CommandBox(this::getNric).getRoot());
        mainWindow.getResultDisplay().setFeedbackToUser(PROMPT_NRIC);
    }

    public void getPersonPhone() {
        mainWindow.getCommandBoxPlaceholder().getChildren().clear();
        mainWindow.getCommandBoxPlaceholder().getChildren().add(new CommandBox(this::getPhone).getRoot());
        mainWindow.getResultDisplay().setFeedbackToUser(PROMPT_PHONE);
    }

    public void confirmPersonDetails() {
        mainWindow.getCommandBoxPlaceholder().getChildren().clear();
        mainWindow.getCommandBoxPlaceholder().getChildren().add(new CommandBox(this::setPersonDetails).getRoot());
        mainWindow.getResultDisplay().setFeedbackToUser(PROMPT_DONE);

    }

    private CommandResult setPersonDetails(String commandText) throws ParseException, CommandException {
        if (formType.isDoctor()) {
            setDoctorDetails(commandText);
        }
        return new CommandResult(commandText);
    }

    private void setDoctorDetails(String commandText) throws ParseException, CommandException{
        ResultDisplay resultDisplay = mainWindow.getResultDisplay();
        PersonListPanel personListPanel = mainWindow.getPersonListPanel();
        CommandBox commandBox = mainWindow.getCommandBox();
        StackPane personListPanelPlaceholder = mainWindow.getPersonListPanelPlaceholder();
        StackPane commandBoxPlaceholder = mainWindow.getCommandBoxPlaceholder();
        if (commandText.equals("/done")) {
            DoctorForm form = (DoctorForm) mainWindow.getForm();
            String command = AddCommand.COMMAND_WORD + " " + PREFIX_TYPE + Type.DOCTOR + " " + PREFIX_NAME
                    + form.getName().getText() + " " + PREFIX_NRIC + form.getNric().getText() + " "
                    + PREFIX_PHONE + form.getPhone().getText();
            CommandResult commandResult = mainWindow.getLogic().execute(command);

            FadeTransition ft = new FadeTransition(Duration.millis(1000), personListPanelPlaceholder);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();

            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
            personListPanelPlaceholder.getChildren().clear();
            personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
            commandBoxPlaceholder.getChildren().clear();
            commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
        }
    }

    private void formFillingAnimation(String commandText, String formField) {
        final Animation animation = new Transition() {
            {
                setCycleDuration(Duration.millis(750));
            }

            protected void interpolate(double frac) {
                final int length = commandText.length();
                final int n = Math.round(length * (float) frac);
                switch (formField) {

                case FormField.NAME:
                    mainWindow.getForm().setName(commandText.substring(0, n));
                    break;
                case FormField.NRIC:
                    mainWindow.getForm().setNric(commandText.substring(0, n));
                    break;
                case FormField.PHONE:
                    mainWindow.getForm().setPhone(commandText.substring(0, n));
                    break;
                }

            }
        };
        animation.play();
    }
}
