package organice.ui;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import organice.model.person.Doctor;
import organice.model.person.Name;
import organice.model.person.Nric;
import organice.model.person.Phone;
import organice.model.person.Type;

/**
 * An UI component that displays information of a {@code Doctor}.
 */
public class DoctorForm extends UiPart<Region> implements Form{

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

    @Override
    public Label getName() {
        return name;
    }

    @Override
    public Label getPhone() {
        return phone;
    }

    @Override
    public Label getNric() {
        return nric;
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
    public Type getType() {
        return new Type("doctor");
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
