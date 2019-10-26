package organice.ui;

import static organice.logic.parser.CliSyntax.PREFIX_AGE;
import static organice.logic.parser.CliSyntax.PREFIX_BLOOD_TYPE;
import static organice.logic.parser.CliSyntax.PREFIX_DOCTOR_IN_CHARGE;
import static organice.logic.parser.CliSyntax.PREFIX_NAME;
import static organice.logic.parser.CliSyntax.PREFIX_NRIC;
import static organice.logic.parser.CliSyntax.PREFIX_ORGAN;
import static organice.logic.parser.CliSyntax.PREFIX_ORGAN_EXPIRY_DATE;
import static organice.logic.parser.CliSyntax.PREFIX_PHONE;
import static organice.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static organice.logic.parser.CliSyntax.PREFIX_TISSUE_TYPE;
import static organice.logic.parser.CliSyntax.PREFIX_TYPE;

import organice.logic.commands.AddCommand;
import organice.logic.commands.CommandResult;
import organice.logic.commands.exceptions.CommandException;
import organice.logic.parser.exceptions.ParseException;
import organice.model.Model;
import organice.model.person.Age;
import organice.model.person.BloodType;
import organice.model.person.Doctor;
import organice.model.person.DoctorInCharge;
import organice.model.person.FormField;
import organice.model.person.Name;
import organice.model.person.Nric;
import organice.model.person.Organ;
import organice.model.person.OrganExpiryDate;
import organice.model.person.Phone;
import organice.model.person.Priority;
import organice.model.person.TissueType;
import organice.model.person.Type;

/**
 * The manager of the Form UI component.
 */
public class FormUiManager {

    private static final String COMMAND_EXIT = "/abort";
    private static final String COMMAND_DONE = "/done";

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
    private static final String PROMPT_DONE = "Please check again the details."
            + "\nType '/done' to confirm, '/abort' to cancel the addition";

    private static final String MESSAGE_EXIT = "Form is abandoned!";

    private MainWindow mainWindow;
    private Type formType;
    private Model model;


    public FormUiManager(MainWindow mainWindow, Type formType, Model model) {
        this.mainWindow = mainWindow;
        this.formType = formType;
        this.model = model;
    }

    private CommandResult getName(String personName) throws ParseException {
        personName = personName.trim();
        if (personName.equals(COMMAND_EXIT)) {
            handleAbort();
            return new CommandResult(personName);
        }

        if (!Name.isValidName(personName)) {
            mainWindow.getResultDisplay().setFeedbackToUser(Name.MESSAGE_CONSTRAINTS);
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }

        mainWindow.getForm().setProgress();
        FormAnimation.typingAnimation(mainWindow, personName, FormField.NAME);
        getPersonField(new CommandBox(this::getNric), PROMPT_NRIC);

        return new CommandResult(personName);
    }

    private CommandResult getNric(String personNric) throws ParseException {
        personNric = personNric.trim();
        if (personNric.equals(COMMAND_EXIT)) {
            handleAbort();
            return new CommandResult(personNric);
        }

        if (!Nric.isValidNric(personNric)) {
            mainWindow.getResultDisplay().setFeedbackToUser(Nric.MESSAGE_CONSTRAINTS);
            throw new ParseException(Nric.MESSAGE_CONSTRAINTS);
        } else if (model.hasPerson(new Nric(personNric))) {
            mainWindow.getResultDisplay().setFeedbackToUser(AddCommand.MESSAGE_DUPLICATE_PERSON);
            throw new ParseException(AddCommand.MESSAGE_DUPLICATE_PERSON);
        }

        mainWindow.getForm().setProgress();
        FormAnimation.typingAnimation(mainWindow, personNric, FormField.NRIC);
        getPersonField(new CommandBox(this::getPhone), PROMPT_PHONE);

        return new CommandResult(personNric);
    }

    private CommandResult getPhone(String personPhone) throws ParseException {
        personPhone = personPhone.trim();
        if (personPhone.equals(COMMAND_EXIT)) {
            handleAbort();
            return new CommandResult(personPhone);
        }

        if (!Phone.isValidPhone(personPhone)) {
            mainWindow.getResultDisplay().setFeedbackToUser(Phone.MESSAGE_CONSTRAINTS);
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }

        mainWindow.getForm().setProgress();
        FormAnimation.typingAnimation(mainWindow, personPhone, FormField.PHONE);

        if (formType.isDoctor()) {
            confirmPersonDetails();
        } else {
            getPersonField(new CommandBox(this::getAge), PROMPT_AGE);
        }

        return new CommandResult(personPhone);
    }

    private CommandResult getAge(String personAge) throws ParseException {
        personAge = personAge.trim();
        if (personAge.equals(COMMAND_EXIT)) {
            handleAbort();
            return new CommandResult(personAge);
        }

        if (!Age.isValidAge(personAge)) {
            mainWindow.getResultDisplay().setFeedbackToUser(Age.MESSAGE_CONSTRAINTS);
            throw new ParseException(Age.MESSAGE_CONSTRAINTS);
        }

        mainWindow.getForm().setProgress();
        FormAnimation.typingAnimation(mainWindow, personAge, FormField.AGE);
        getPersonField(new CommandBox(this::getOrgan), PROMPT_ORGAN);

        return new CommandResult(personAge);
    }

    private CommandResult getOrgan(String personOrgan) throws ParseException {
        personOrgan = personOrgan.trim();
        if (personOrgan.equals(COMMAND_EXIT)) {
            handleAbort();
            return new CommandResult(personOrgan);
        }

        if (!Organ.isValidOrgan(personOrgan)) {
            mainWindow.getResultDisplay().setFeedbackToUser(Organ.MESSAGE_CONSTRAINTS);
            throw new ParseException(Organ.MESSAGE_CONSTRAINTS);
        }

        mainWindow.getForm().setProgress();
        FormAnimation.typingAnimation(mainWindow, personOrgan, FormField.ORGAN);
        getPersonField(new CommandBox(this::getBloodType), PROMPT_BLOOD_TYPE);

        return new CommandResult(personOrgan);
    }

    private CommandResult getBloodType(String personBloodType) throws ParseException {
        personBloodType = personBloodType.trim();
        if (personBloodType.equals(COMMAND_EXIT)) {
            handleAbort();
            return new CommandResult(personBloodType);
        }

        if (!BloodType.isValidBloodType(personBloodType)) {
            mainWindow.getResultDisplay().setFeedbackToUser(BloodType.MESSAGE_CONSTRAINTS);
            throw new ParseException(BloodType.MESSAGE_CONSTRAINTS);
        }

        mainWindow.getForm().setProgress();
        FormAnimation.typingAnimation(mainWindow, personBloodType, FormField.BLOOD_TYPE);
        getPersonField(new CommandBox(this::getTissueType), PROMPT_TISSUE_TYPE);

        return new CommandResult(personBloodType);
    }

    private CommandResult getTissueType(String personTissueType) throws ParseException {
        personTissueType = personTissueType.trim();
        if (personTissueType.equals(COMMAND_EXIT)) {
            handleAbort();
            return new CommandResult(personTissueType);
        }

        if (!TissueType.isValidTissueType(personTissueType)) {
            mainWindow.getResultDisplay().setFeedbackToUser(TissueType.MESSAGE_CONSTRAINTS);
            throw new ParseException(TissueType.MESSAGE_CONSTRAINTS);
        }

        mainWindow.getForm().setProgress();
        FormAnimation.typingAnimation(mainWindow, personTissueType, FormField.TISSUE_TYPE);

        if (formType.isPatient()) {
            getPersonField(new CommandBox(this::getPriority), PROMPT_PRIORITY);
        } else if (formType.isDonor()) {
            getPersonField(new CommandBox(this::getOrganExpiryDate), PROMPT_ORGAN_EXPIRY_DATE);
        }

        return new CommandResult(personTissueType);
    }

    private CommandResult getPriority(String personPriority) throws ParseException {
        personPriority = personPriority.trim();
        if (personPriority.equals(COMMAND_EXIT)) {
            handleAbort();
            return new CommandResult(personPriority);
        }

        if (!Priority.isValidPriority(personPriority)) {
            mainWindow.getResultDisplay().setFeedbackToUser(Priority.MESSAGE_CONSTRAINTS);
            throw new ParseException(Priority.MESSAGE_CONSTRAINTS);
        }

        mainWindow.getForm().setProgress();
        FormAnimation.typingAnimation(mainWindow, personPriority, FormField.PRIORITY);

        if (formType.isPatient()) {
            getPersonField(new CommandBox(this::getDoctorIc), PROMPT_DOCTOR_IC);
        }

        return new CommandResult(personPriority);
    }

    private CommandResult getDoctorIc(String personDoctorIc) throws ParseException {
        personDoctorIc = personDoctorIc.trim();
        if (personDoctorIc.equals(COMMAND_EXIT)) {
            handleAbort();
            return new CommandResult(personDoctorIc);
        }

        if (!DoctorInCharge.isValidDoctorInCharge(personDoctorIc)) {
            mainWindow.getResultDisplay().setFeedbackToUser(DoctorInCharge.MESSAGE_CONSTRAINTS);
            throw new ParseException(DoctorInCharge.MESSAGE_CONSTRAINTS);
        } else if (!model.hasDoctor(new Nric(personDoctorIc))) {
            mainWindow.getResultDisplay().setFeedbackToUser(AddCommand.MESSAGE_DOCTOR_NOT_FOUND);
            throw new ParseException(AddCommand.MESSAGE_DOCTOR_NOT_FOUND);
        }

        mainWindow.getForm().setProgress();
        FormAnimation.typingAnimation(mainWindow, personDoctorIc, FormField.DOCTOR_IC);

        if (formType.isPatient()) {
            confirmPersonDetails();
        }

        return new CommandResult(personDoctorIc);
    }

    private CommandResult getOrganExpiryDate(String personOrganExpiryDate) throws ParseException {
        personOrganExpiryDate = personOrganExpiryDate.trim();
        if (personOrganExpiryDate.equals(COMMAND_EXIT)) {
            handleAbort();
            return new CommandResult(personOrganExpiryDate);
        }

        if (!OrganExpiryDate.isValidExpiryDate(personOrganExpiryDate)) {
            mainWindow.getResultDisplay().setFeedbackToUser(OrganExpiryDate.MESSAGE_CONSTRAINTS);
            throw new ParseException(OrganExpiryDate.MESSAGE_CONSTRAINTS);
        }

        mainWindow.getForm().setProgress();
        FormAnimation.typingAnimation(mainWindow, personOrganExpiryDate, FormField.ORGAN_EXPIRY_DATE);

        if (formType.isDonor()) {
            confirmPersonDetails();
        }

        return new CommandResult(personOrganExpiryDate);
    }

    private void getPersonField(CommandBox commandBox, String prompt) {
        mainWindow.getCommandBoxPlaceholder().getChildren().clear();
        mainWindow.getCommandBoxPlaceholder().getChildren().add(commandBox.getRoot());
        mainWindow.getResultDisplay().setFeedbackToUser(prompt);
    }

    public void getPersonDetails() {
        getPersonField(new CommandBox(this::getName), PROMPT_NAME);
    }

    private void confirmPersonDetails() {
        getPersonField(new CommandBox(this::setPersonDetails), PROMPT_DONE);
    }

    private CommandResult setPersonDetails(String commandText) throws ParseException, CommandException {
        commandText = commandText.trim();
        if (commandText.equals(COMMAND_EXIT)) {
            handleAbort();
            return new CommandResult(commandText);
        } else if (commandText.equals(COMMAND_DONE)) {
            ResultDisplay resultDisplay = mainWindow.getResultDisplay();
            CommandResult commandResult = null;

            if (formType.isDoctor()) {
                commandResult = addDoctorToList();
            } else if (formType.isDonor()) {
                commandResult = addDonorToList();
            } else if (formType.isPatient()) {
                commandResult = addPatientToList();
            }

            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
            FormAnimation.fadingAnimation(mainWindow);
        }

        // Reset the UI display to the initial state
        mainWindow.resetInnerParts();

        return new CommandResult(commandText);
    }

    /**
     * Adds {@code Doctor} to the ORGANice list.
     */
    private CommandResult addDoctorToList() throws ParseException, CommandException {
        DoctorForm form = (DoctorForm) mainWindow.getForm();
        String command = AddCommand.COMMAND_WORD + " " + PREFIX_TYPE + Type.DOCTOR + " " + PREFIX_NAME
            + form.getName().getText() + " " + PREFIX_NRIC + form.getNric().getText() + " "
            + PREFIX_PHONE + form.getPhone().getText();

        CommandResult commandResult = mainWindow.getLogic().execute(command);
        return commandResult;
    }

    /**
     * Adds {@code Donor} to the ORGANice list.
     */
    private CommandResult addDonorToList() throws ParseException, CommandException {
        DonorForm form = (DonorForm) mainWindow.getForm();
        String command = AddCommand.COMMAND_WORD + " " + PREFIX_TYPE + Type.DONOR + " " + PREFIX_NAME
            + form.getName().getText() + " " + PREFIX_NRIC + form.getNric().getText() + " "
            + PREFIX_PHONE + form.getPhone().getText() + " " + PREFIX_AGE + form.getAge().getText() + " "
            + PREFIX_ORGAN + form.getOrgan().getText() + " " + PREFIX_BLOOD_TYPE + form.getBloodType().getText() + " "
            + PREFIX_TISSUE_TYPE + form.getTissueType().getText() + " "
            + PREFIX_ORGAN_EXPIRY_DATE + form.getOrganExpiryDate().getText();

        CommandResult commandResult = mainWindow.getLogic().execute(command);
        return commandResult;
    }

    /**
     * Adds {@code Patient} to the ORGANice list.
     */
    private CommandResult addPatientToList() throws ParseException, CommandException {
        PatientForm form = (PatientForm) mainWindow.getForm();
        String command = AddCommand.COMMAND_WORD + " " + PREFIX_TYPE + Type.PATIENT + " " + PREFIX_NAME
            + form.getName().getText() + " " + PREFIX_NRIC + form.getNric().getText() + " "
            + PREFIX_PHONE + form.getPhone().getText() + " " + PREFIX_AGE + form.getAge().getText() + " "
            + PREFIX_ORGAN + form.getOrgan().getText() + " " + PREFIX_BLOOD_TYPE + form.getBloodType().getText() + " "
            + PREFIX_TISSUE_TYPE + form.getTissueType().getText() + " " + PREFIX_PRIORITY + form.getPriority().getText()
            + " " + PREFIX_DOCTOR_IN_CHARGE + form.getDoctorIc().getText();

        CommandResult commandResult = mainWindow.getLogic().execute(command);
        return commandResult;
    }

    private void handleAbort() {
        mainWindow.getResultDisplay().setFeedbackToUser(MESSAGE_EXIT);
        mainWindow.resetInnerParts();
    }

    private void handleReset() {
        getPersonField(new CommandBox(this::getName), PROMPT_NAME);
    }
}
