package organice.ui;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.util.Duration;
import organice.model.person.FormField;
import organice.model.person.Type;

public class FormAnimation {

    public static void fadingAnimation(MainWindow mainWindow) {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), mainWindow.getPersonListPanelPlaceholder());
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    public static void typingAnimation(MainWindow mainWindow, String commandText, String formField) {
        final Animation animation = new Transition() {
            {
                setCycleDuration(Duration.millis(750));
            }

            protected void interpolate(double frac) {
                Type formType = mainWindow.getForm().getType();
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
                    if (formType.isDonor()) {
                        ((DonorForm) mainWindow.getForm()).setAge(commandText.substring(0, n));
                    } else if (formType.isPatient()) {
                        ((PatientForm) mainWindow.getForm()).setAge(commandText.substring(0, n));
                    }
                    break;
                case FormField.ORGAN:
                    if (formType.isPatient()) {
                        ((PatientForm) mainWindow.getForm()).setOrgan(commandText.substring(0, n));
                    } else if (formType.isDonor()) {
                        ((DonorForm) mainWindow.getForm()).setOrgan(commandText.substring(0, n));
                    }
                    break;
                case FormField.BLOOD_TYPE:
                    if (formType.isPatient()) {
                        ((PatientForm) mainWindow.getForm()).setBloodType(commandText.substring(0, n));
                    } else if (formType.isDonor()) {
                        ((DonorForm) mainWindow.getForm()).setBloodType(commandText.substring(0, n));
                    }
                    break;
                case FormField.TISSUE_TYPE:
                    if (formType.isPatient()) {
                        ((PatientForm) mainWindow.getForm()).setTissueType(commandText.substring(0, n));
                    } else if (formType.isDonor()) {
                        ((DonorForm) mainWindow.getForm()).setTissueType(commandText.substring(0, n));
                    }
                    break;
                case FormField.PRIORITY:
                    ((PatientForm) mainWindow.getForm()).setPriority(commandText.substring(0, n));
                    break;
                case FormField.DOCTOR_IC:
                    ((PatientForm) mainWindow.getForm()).setDoctoIc(commandText.substring(0, n));
                    break;
                case FormField.ORGAN_EXPIRY_DATE:
                    ((DonorForm) mainWindow.getForm()).setOrganExpiryDate(commandText.substring(0, n));
                    break;
                }
            }
        };
        animation.play();
    }
}
