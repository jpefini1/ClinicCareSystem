package cliniccaresystem.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PatientRegistrationCodeBehind {

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private DatePicker dobDatePicker;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField streetTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private TextField zipcodeTextField;

    @FXML
    private ComboBox<?> stateComboBox;

    @FXML
    private ComboBox<?> genderComboBox;

    @FXML
    private Label errorLabel;

    @FXML
    void onRegister(ActionEvent event) {

    }

}
