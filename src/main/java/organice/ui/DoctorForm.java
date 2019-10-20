package organice.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import organice.model.person.Doctor;
import organice.model.person.Name;
import organice.model.person.Nric;
import organice.model.person.Phone;

/**
 * An UI component that displays information of a {@code Doctor}.
 */
public class DoctorForm extends UiPart<Region> {

    private static final String FXML = "DoctorForm.fxml";

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

    public DoctorForm() {
        super(FXML);
        name.setText("");
        phone.setText("");
        nric.setText("");
    }

    public Label getName() {
        return name;
    }

    public Label getPhone() {
        return phone;
    }

    public Label getNric() {
        return nric;
    }

    public void setName(Name name) {
        this.name.setText(name.fullName);
    }

    public void setPhone(Phone phone) {
        this.phone.setText(phone.value);
    }

    public void setNric(Nric nric) {
        this.nric.setText(nric.value);
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
        DoctorForm form = (DoctorForm) other;
        return name.getText().equals(form.name.getText())
            && phone.equals(form.phone.getText())
            && nric.equals(form.nric.getText());

    }
}
