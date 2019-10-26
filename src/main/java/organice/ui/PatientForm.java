package organice.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import organice.model.person.Type;

/**
 * An UI component that displays information of a {@code Doctor}.
 */
public class PatientForm extends UiPart<Region> implements Form {

    private static final String FXML = "PatientForm.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label nric;
    @FXML
    private Label age;
    @FXML
    private Label organ;
    @FXML
    private Label bloodType;
    @FXML
    private Label tissueType;
    @FXML
    private Label priority;
    @FXML
    private Label doctorIc;
    @FXML
    private Label progressPercentage;
    @FXML
    private ProgressBar progressBar;

    private final int numberOfFields = 9;
    private int filledFields = 0;

    public PatientForm() {
        super(FXML);
        name.setText("");
        phone.setText("");
        nric.setText("");
        age.setText("");
        organ.setText("");
        bloodType.setText("");
        tissueType.setText("");
        priority.setText("");
        doctorIc.setText("");
    }

    @Override
    public Label getName() {
        return name;
    }

    @Override
    public Label getPhone() {
        return phone;
    }

    @Override
    public Type getType() {
        return new Type(Type.PATIENT);
    }

    @Override
    public Label getNric() {
        return nric;
    }

    public Label getAge() {
        return age;
    }

    public Label getOrgan() {
        return organ;
    }

    public Label getBloodType() {
        return bloodType;
    }

    public Label getTissueType() {
        return tissueType;
    }

    public Label getPriority() {
        return priority;
    }

    public Label getDoctorIc() {
        return doctorIc;
    }

    @Override
    public void setName(String name) {
        this.name.setText(name);
    }

    @Override
    public void setNric(String nric) {
        this.nric.setText(nric);
    }

    @Override
    public void setPhone(String phone) {
        this.phone.setText(phone);
    }

    @Override
    public void setProgress() {
        filledFields ++;
        double currentProgress = (double)filledFields / numberOfFields;
        FormAnimation.percentageChangeAnimation(currentProgress,
            String.format("%.1f",currentProgress * 100), progressPercentage, progressBar);
    }

    public void setAge(String age) {
        this.age.setText(age);
    }

    public void setOrgan(String organ) {
        this.organ.setText(organ);
    }

    public void setBloodType(String bloodType) {
        this.bloodType.setText(bloodType);
    }

    public void setTissueType(String tissueType) {
        this.tissueType.setText(tissueType);
    }

    public void setPriority(String priority) {
        this.priority.setText(priority);
    }

    public void setDoctorIc(String doctorIc) {
        this.doctorIc.setText(doctorIc);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DoctorCard)) {
            return false;
        }

        // state check
        PatientForm form = (PatientForm) other;
        return name.getText().equals(form.name.getText())
            && phone.equals(form.phone.getText())
            && nric.equals(form.nric.getText())
            && age.equals(form.age.getText())
            && organ.equals(form.organ.getText())
            && bloodType.equals(form.bloodType.getText())
            && tissueType.equals(form.tissueType.getText())
            && priority.equals(form.priority.getText())
            && doctorIc.equals(form.doctorIc.getText());
    }
}
