package cliniccaresystem.view;

import java.io.IOException;

import cliniccaresystem.Main;
import cliniccaresystem.model.ResultCode;
import cliniccaresystem.model.USState;
import cliniccaresystem.viewmodel.SignUpViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpCodeBehind {
	
	@FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

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
    private Label errorLabel;

    private SignUpViewModel viewmodel;

    
	public SignUpCodeBehind() {
		this.viewmodel = new SignUpViewModel();
	}

	@FXML 
	void initialize() { 
		this.bindToViewModel(); 
		this.setupChangeListeners();
	}

	private void bindToViewModel() {
		this.usernameTextField.textProperty().bindBidirectional(this.viewmodel.usernameProperty());
		this.passwordTextField.textProperty().bindBidirectional(this.viewmodel.passwordProperty());
		this.firstNameTextField.textProperty().bindBidirectional(this.viewmodel.firstNameProperty());
		this.lastNameTextField.textProperty().bindBidirectional(this.viewmodel.lastNameProperty());
		this.dobDatePicker.valueProperty().bindBidirectional(this.viewmodel.dateOfBirthProperty());
		this.phoneNumberTextField.textProperty().bindBidirectional(this.viewmodel.phoneNumberProperty());
		this.streetTextField.textProperty().bindBidirectional(this.viewmodel.streetProperty());
		this.cityTextField.textProperty().bindBidirectional(this.viewmodel.cityProperty());
		this.zipcodeTextField.textProperty().bindBidirectional(this.viewmodel.zipCodeProperty());
		
		this.stateComboBox.getItems().addAll(USState.values());
		this.stateComboBox.valueProperty().bindBidirectional(this.viewmodel.stateProperty());
	}
	
	private void setupChangeListeners() {
		this.usernameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("^.{0,20}")) {
				if (oldValue == null || oldValue.isEmpty()) {
					this.usernameTextField.setText("");
				} else {
					this.usernameTextField.setText(oldValue);
				}
			}
		});
		
		this.passwordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("^.{0,20}")) {
				if (oldValue == null || oldValue.isEmpty()) {
					this.passwordTextField.setText("");
				} else {
					this.passwordTextField.setText(oldValue);
				}
			}
		});
		
		this.firstNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("^[a-zA-Z]{0,30}")) {
				if (oldValue == null || oldValue.isEmpty()) {
					this.firstNameTextField.setText("");
				} else {
					this.firstNameTextField.setText(oldValue);
				}
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
		});
		
		this.phoneNumberTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("^[0-9]{0,10}")) {
				if (oldValue == null || oldValue.isEmpty()) {
					this.phoneNumberTextField.setText("");
				} else {
					this.phoneNumberTextField.setText(oldValue);
				}
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
		});
		
		this.cityTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("^[a-zA-Z]{0,100}")) {
				if (oldValue == null || oldValue.isEmpty()) {
					this.cityTextField.setText("");
				} else {
					this.cityTextField.setText(oldValue);
				}
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
		});
	}
    
    @FXML
    void onSignUp(ActionEvent event) {
    	ResultCode result = this.viewmodel.signUp();
    	
    	if (!result.equals(ResultCode.Success)) {
    		this.errorLabel.textProperty().setValue(result.toString());
    	} else {
    		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        	
        	try {
    			Main.changeScene(currentStage, Main.LOGIN_PAGE_PATH, Main.LOGIN_PAGE_TITLE);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
    }
    
    @FXML
    void onCancel(ActionEvent event) {
    	Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	
    	try {
			Main.changeScene(currentStage, Main.LOGIN_PAGE_PATH, Main.LOGIN_PAGE_TITLE);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}
