package organice.ui;

import static organice.logic.parser.CliSyntax.PREFIX_AGE;
import static organice.logic.parser.CliSyntax.PREFIX_BLOOD_TYPE;
import static organice.logic.parser.CliSyntax.PREFIX_DOCTOR_IN_CHARGE;
import static organice.logic.parser.CliSyntax.PREFIX_NAME;
import static organice.logic.parser.CliSyntax.PREFIX_NRIC;
import static organice.logic.parser.CliSyntax.PREFIX_ORGAN;
import static organice.logic.parser.CliSyntax.PREFIX_PHONE;
import static organice.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static organice.logic.parser.CliSyntax.PREFIX_TISSUE_TYPE;
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
import organice.model.person.Age;
import organice.model.person.BloodType;
import organice.model.person.DoctorInCharge;
import organice.model.person.Donor;
import organice.model.person.FormField;
import organice.model.person.Name;
import organice.model.person.Nric;
import organice.model.person.Organ;
import organice.model.person.OrganExpiryDate;
import organice.model.person.Patient;
import organice.model.person.Phone;
import organice.model.person.Priority;
import organice.model.person.TissueType;
import organice.model.person.Type;

public class FormUiManager {

    private MainWindow mainWindow;
    private Type formType;
    private Model model;

    private static final String PROMPT_NAME = "Enter name:";
    private static final String PROMPT_NRIC = "Enter NRIC:";
    private static final String PROMPT_PHONE = "Enter phone number:";
    private static final String PROMPT_AGE = "Enter age:";
    private static final String PROMPT_ORGAN = "Enter organ:";
    private static final String PROMPT_BLOOD_TYPE = "Enter blood type:";
    private static final String PROMPT_TISSUE_TYPE = "Enter tissue type:";
    private static final String PROMPT_PRIORITY = "Enter priority:";
    private static final String PROMPT_DOCTOR_IC = "Enter doctor in charge's nric:";
    private static final String PROMPT_ORGAN_EXPIRY_DATE = "Enter organ expiry date:";

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
        } else if (model.hasPerson(new Nric(personNric))) {
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
        } else {
            getPersonAge();
        }
        return new CommandResult(personPhone);
    }

    private CommandResult getAge(String personAge) throws ParseException{
        if (!Age.isValidAge(personAge)) {
            mainWindow.getResultDisplay().setFeedbackToUser(Age.MESSAGE_CONSTRAINTS);
            throw new ParseException(Age.MESSAGE_CONSTRAINTS);
        }

        formFillingAnimation(personAge, FormField.AGE);

        getPersonOrgan();
        return new CommandResult(personAge);
    }

    private CommandResult getOrgan(String personOrgan) throws ParseException{
        if (!Organ.isValidOrgan(personOrgan)) {
            mainWindow.getResultDisplay().setFeedbackToUser(Organ.MESSAGE_CONSTRAINTS);
            throw new ParseException(Organ.MESSAGE_CONSTRAINTS);
        }

        formFillingAnimation(personOrgan, FormField.ORGAN);

        getPersonBloodType();
        return new CommandResult(personOrgan);
    }

    private CommandResult getBloodType(String personBloodType) throws ParseException{
        if (!BloodType.isValidBloodType(personBloodType)) {
            mainWindow.getResultDisplay().setFeedbackToUser(BloodType.MESSAGE_CONSTRAINTS);
            throw new ParseException(BloodType.MESSAGE_CONSTRAINTS);
        }

        formFillingAnimation(personBloodType, FormField.BLOOD_TYPE);

        getPersonTissueType();
        return new CommandResult(personBloodType);
    }

    private CommandResult getTissueType(String personTissueType) throws ParseException{
        if (!TissueType.isValidTissueType(personTissueType)) {
            mainWindow.getResultDisplay().setFeedbackToUser(TissueType.MESSAGE_CONSTRAINTS);
            throw new ParseException(TissueType.MESSAGE_CONSTRAINTS);
        }

        formFillingAnimation(personTissueType, FormField.TISSUE_TYPE);

        if (formType.isPatient()) {
            getPersonPriority();
        } else if (formType.isDonor()) {
            getPersonOrganExpiryDate();
        }

        return new CommandResult(personTissueType);
    }

    private CommandResult getPriority(String personPriority) throws ParseException{
        if (!Priority.isValidPriority(personPriority)) {
            mainWindow.getResultDisplay().setFeedbackToUser(Priority.MESSAGE_CONSTRAINTS);
            throw new ParseException(Priority.MESSAGE_CONSTRAINTS);
        }

        formFillingAnimation(personPriority, FormField.PRIORITY);

        if (formType.isPatient()) {
            getPersonDoctorIc();
        }

        return new CommandResult(personPriority);
    }

    private CommandResult getDoctorIc(String personDoctorIc) throws ParseException{
        if (!DoctorInCharge.isValidDoctorInCharge(personDoctorIc)) {
            mainWindow.getResultDisplay().setFeedbackToUser(DoctorInCharge.MESSAGE_CONSTRAINTS);
            throw new ParseException(DoctorInCharge.MESSAGE_CONSTRAINTS);
        } else if (!model.hasDoctor(new Nric(personDoctorIc))) {
            mainWindow.getResultDisplay().setFeedbackToUser(AddCommand.MESSAGE_DOCTOR_NOT_FOUND);
            throw new ParseException(AddCommand.MESSAGE_DOCTOR_NOT_FOUND);
        }

        formFillingAnimation(personDoctorIc, FormField.DOCTOR_IC);
        if (formType.isPatient()) {
            confirmPersonDetails();
        }

        return new CommandResult(personDoctorIc);
    }

    private CommandResult getOrganExpiryDate(String personOrganExpiryDate) throws ParseException{
        if (!OrganExpiryDate.isValidExpiryDate(personOrganExpiryDate)) {
            mainWindow.getResultDisplay().setFeedbackToUser(OrganExpiryDate.MESSAGE_CONSTRAINTS);
            throw new ParseException(OrganExpiryDate.MESSAGE_CONSTRAINTS);
        }

        formFillingAnimation(personOrganExpiryDate, FormField.ORGAN_EXPIRY_DATE);
        if (formType.isDonor()) {
            confirmPersonDetails();
        }

        return new CommandResult(personOrganExpiryDate);
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

    public void getPersonAge() {
        mainWindow.getCommandBoxPlaceholder().getChildren().clear();
        mainWindow.getCommandBoxPlaceholder().getChildren().add(new CommandBox(this::getAge).getRoot());
        mainWindow.getResultDisplay().setFeedbackToUser(PROMPT_AGE);
    }

    public void getPersonOrgan() {
        mainWindow.getCommandBoxPlaceholder().getChildren().clear();
        mainWindow.getCommandBoxPlaceholder().getChildren().add(new CommandBox(this::getOrgan).getRoot());
        mainWindow.getResultDisplay().setFeedbackToUser(PROMPT_ORGAN);
    }

    public void getPersonBloodType() {
        mainWindow.getCommandBoxPlaceholder().getChildren().clear();
        mainWindow.getCommandBoxPlaceholder().getChildren().add(new CommandBox(this::getBloodType).getRoot());
        mainWindow.getResultDisplay().setFeedbackToUser(PROMPT_BLOOD_TYPE);
    }

    public void getPersonTissueType() {
        mainWindow.getCommandBoxPlaceholder().getChildren().clear();
        mainWindow.getCommandBoxPlaceholder().getChildren().add(new CommandBox(this::getTissueType).getRoot());
        mainWindow.getResultDisplay().setFeedbackToUser(PROMPT_TISSUE_TYPE);
    }

    public void getPersonPriority() {
        mainWindow.getCommandBoxPlaceholder().getChildren().clear();
        mainWindow.getCommandBoxPlaceholder().getChildren().add(new CommandBox(this::getPriority).getRoot());
        mainWindow.getResultDisplay().setFeedbackToUser(PROMPT_PRIORITY);
    }

    public void getPersonDoctorIc() {
        mainWindow.getCommandBoxPlaceholder().getChildren().clear();
        mainWindow.getCommandBoxPlaceholder().getChildren().add(new CommandBox(this::getDoctorIc).getRoot());
        mainWindow.getResultDisplay().setFeedbackToUser(PROMPT_DOCTOR_IC);
    }

    public void getPersonOrganExpiryDate() {
        mainWindow.getCommandBoxPlaceholder().getChildren().clear();
        mainWindow.getCommandBoxPlaceholder().getChildren().add(new CommandBox(this::getOrganExpiryDate).getRoot());
        mainWindow.getResultDisplay().setFeedbackToUser(PROMPT_ORGAN_EXPIRY_DATE);
    }

    public void confirmPersonDetails() {
        mainWindow.getCommandBoxPlaceholder().getChildren().clear();
        mainWindow.getCommandBoxPlaceholder().getChildren().add(new CommandBox(this::setPersonDetails).getRoot());
        mainWindow.getResultDisplay().setFeedbackToUser(PROMPT_DONE);
    }

    private CommandResult setPersonDetails(String commandText) throws ParseException, CommandException {
        ResultDisplay resultDisplay = mainWindow.getResultDisplay();
        PersonListPanel personListPanel = mainWindow.getPersonListPanel();
        CommandBox commandBox = mainWindow.getCommandBox();
        StackPane personListPanelPlaceholder = mainWindow.getPersonListPanelPlaceholder();
        StackPane commandBoxPlaceholder = mainWindow.getCommandBoxPlaceholder();
        if (formType.isPatient()) {
            addPatientToList(commandText, personListPanelPlaceholder, resultDisplay);
        } else if (formType.isDonor()) {

        } else if (formType.isDoctor()) {
            addDoctorToList(commandText, personListPanelPlaceholder, resultDisplay);
        }

        // Setting up the UI with list of persons
        personListPanelPlaceholder.getChildren().clear();
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
        commandBoxPlaceholder.getChildren().clear();
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        return new CommandResult(commandText);
    }

    private void addDoctorToList(String commandText, StackPane personListPanelPlaceholder,
                                 ResultDisplay resultDisplay) throws ParseException, CommandException {

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
        }
    }

    private void addPatientToList(String commandText, StackPane personListPanelPlaceholder,
                                 ResultDisplay resultDisplay) throws ParseException, CommandException {

        if (commandText.equals("/done")) {
            PatientForm form = (PatientForm) mainWindow.getForm();
            String command = AddCommand.COMMAND_WORD + " " + PREFIX_TYPE + Type.PATIENT + " " + PREFIX_NAME
                + form.getName().getText() + " " + PREFIX_NRIC + form.getNric().getText() + " "
                + PREFIX_PHONE + form.getPhone().getText() + " " + PREFIX_AGE + form.getAge().getText() + " "
                + PREFIX_ORGAN + form.getOrgan().getText() + " " + PREFIX_BLOOD_TYPE + form.getBloodType().getText() + " "
                + PREFIX_TISSUE_TYPE + form.getTissueType().getText() + " " + PREFIX_PRIORITY + form.getPriority().getText() + " "
                + PREFIX_DOCTOR_IN_CHARGE + form.getDoctorIc().getText() + " ";
            CommandResult commandResult = mainWindow.getLogic().execute(command);

            FadeTransition ft = new FadeTransition(Duration.millis(1000), personListPanelPlaceholder);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();

            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
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
                case FormField.AGE:
                    ((PatientForm) mainWindow.getForm()).setAge(commandText.substring(0, n));
                    break;
                case FormField.ORGAN:
                    ((PatientForm) mainWindow.getForm()).setOrgan(commandText.substring(0, n));
                    break;
                case FormField.BLOOD_TYPE:
                    ((PatientForm) mainWindow.getForm()).setBloodType(commandText.substring(0, n));
                    break;
                case FormField.TISSUE_TYPE:
                    ((PatientForm) mainWindow.getForm()).setTissueType(commandText.substring(0, n));
                    break;
                case FormField.PRIORITY:
                    ((PatientForm) mainWindow.getForm()).setPriority(commandText.substring(0, n));
                    break;
                case FormField.DOCTOR_IC:
                    ((PatientForm) mainWindow.getForm()).setDoctoIc(commandText.substring(0, n));
                    break;
//                case FormField.ORGAN_EXPIRY_DATE:
//                    ((DonorForm) mainWindow.getForm()).setOrganExpiryDate(commandText.substring(0, n));
//                    break;
                }

            }
        };
        animation.play();
    }
}
