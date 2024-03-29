package cliniccaresystem.view;

import java.io.IOException;

import cliniccaresystem.Main;
import cliniccaresystem.model.Gender;
import cliniccaresystem.model.ResultCode;
import cliniccaresystem.model.USState;
import cliniccaresystem.viewmodel.PatientRegistrationViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    private ComboBox<USState> stateComboBox;

    @FXML
    private ComboBox<Gender> genderComboBox;

    @FXML
    private Label errorLabel;
    
    @FXML
    private Label nurseInfoLabel;
    
    @FXML
    private Button registerButton;
    
    @FXML
    private Button closeButton;
    
    private PatientRegistrationViewModel viewmodel;

    
	public PatientRegistrationCodeBehind() {
		this.viewmodel = new PatientRegistrationViewModel();
	}

	@FXML 
	void initialize() { 
		this.bindToViewModel(); 
		this.setupChangeListeners();
	}

	private void bindToViewModel() {
		this.firstNameTextField.textProperty().bindBidirectional(this.viewmodel.firstNameProperty());
		this.lastNameTextField.textProperty().bindBidirectional(this.viewmodel.lastNameProperty());
		this.dobDatePicker.valueProperty().bindBidirectional(this.viewmodel.dateOfBirthProperty());
		this.phoneNumberTextField.textProperty().bindBidirectional(this.viewmodel.phoneNumberProperty());
		this.streetTextField.textProperty().bindBidirectional(this.viewmodel.streetProperty());
		this.cityTextField.textProperty().bindBidirectional(this.viewmodel.cityProperty());
		this.zipcodeTextField.textProperty().bindBidirectional(this.viewmodel.zipCodeProperty());
		
		this.stateComboBox.getItems().addAll(USState.values());
		this.stateComboBox.valueProperty().bindBidirectional(this.viewmodel.stateProperty());
		this.genderComboBox.getItems().addAll(Gender.values());
		this.genderComboBox.valueProperty().bindBidirectional(this.viewmodel.genderProperty());
	}
	
	private void setupChangeListeners() {
		this.firstNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("^[a-zA-Z]{0,30}")) {
				if (oldValue == null || oldValue.isEmpty()) {
					this.firstNameTextField.setText("");
				} else {
					this.firstNameTextField.setText(oldValue);
				}
			}
			
			if (this.viewmodel.checkIfPatientInfoIsValid().equals(ResultCode.IsValid)) {
				this.registerButton.setDisable(false);
			} else {
				this.registerButton.setDisable(true);
			}
		});
		
		this.lastNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("^[a-zA-Z]{0,30}")) {
				if (oldValue == null || oldValue.isEmpty()) {
					this.lastNameTextField.setText("");
				} else {
					this.lastNameTextField.setText(oldValue);
				}
			}
			
			if (this.viewmodel.checkIfPatientInfoIsValid().equals(ResultCode.IsValid)) {
				this.registerButton.setDisable(false);
			} else {
				this.registerButton.setDisable(true);
			}
		});
		
		this.phoneNumberTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("^[0-9]{0,10}")) {
				if (oldValue == null || oldValue.isEmpty()) {
					this.phoneNumberTextField.setText("");
				} else {
					this.phoneNumberTextField.setText(oldValue);
				}
			}
			
			if (this.viewmodel.checkIfPatientInfoIsValid().equals(ResultCode.IsValid)) {
				this.registerButton.setDisable(false);
			} else {
				this.registerButton.setDisable(true);
			}
		});
		
		this.streetTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("^.{0,100}")) {
				if (oldValue == null || oldValue.isEmpty()) {
					this.streetTextField.setText("");
				} else {
					this.streetTextField.setText(oldValue);
				}
			}
			
			if (this.viewmodel.checkIfPatientInfoIsValid().equals(ResultCode.IsValid)) {
				this.registerButton.setDisable(false);
			} else {
				this.registerButton.setDisable(true);
			}
		});
		
		this.cityTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("^[a-zA-Z]{0,100}")) {
				if (oldValue == null || oldValue.isEmpty()) {
					this.cityTextField.setText("");
				} else {
					this.cityTextField.setText(oldValue);
				}
			}
			
			if (this.viewmodel.checkIfPatientInfoIsValid().equals(ResultCode.IsValid)) {
				this.registerButton.setDisable(false);
			} else {
				this.registerButton.setDisable(true);
			}
		});
		
		this.zipcodeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("^[0-9]{0,5}")) {
				if (oldValue == null || oldValue.isEmpty()) {
					this.zipcodeTextField.setText("");
				} else {
					this.zipcodeTextField.setText(oldValue);
				}
			}
			
			if (this.viewmodel.checkIfPatientInfoIsValid().equals(ResultCode.IsValid)) {
				this.registerButton.setDisable(false);
			} else {
				this.registerButton.setDisable(true);
			}
		});
		
		this.dobDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (this.viewmodel.checkIfPatientInfoIsValid().equals(ResultCode.IsValid)) {
				this.registerButton.setDisable(false);
			} else {
				this.registerButton.setDisable(true);
			}
		});
		
		this.stateComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (this.viewmodel.checkIfPatientInfoIsValid().equals(ResultCode.IsValid)) {
				this.registerButton.setDisable(false);
			} else {
				this.registerButton.setDisable(true);
			}
		});
		
		this.genderComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (this.viewmodel.checkIfPatientInfoIsValid().equals(ResultCode.IsValid)) {
				this.registerButton.setDisable(false);
			} else {
				this.registerButton.setDisable(true);
			}
		});
	}

    @FXML
    void onRegister(ActionEvent event) {
    	var result = this.viewmodel.registerPatient();
    	
    	if (result.equals(ResultCode.Success)) {
    		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        	
        	try {
    			Main.changeScene(currentStage, Main.HOMEPAGE_PAGE_PATH, Main.PATIENT_REGISTRATION_PAGE_TITLE);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	} else {
    		this.errorLabel.textProperty().setValue("Invalid Info");
    	}
    }
    
    @FXML
    void onCancel(ActionEvent event) {
    	Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	
    	try {
			Main.changeScene(currentStage, Main.HOMEPAGE_PAGE_PATH, Main.HOMEPAGE_PAGE_TITLE);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}
