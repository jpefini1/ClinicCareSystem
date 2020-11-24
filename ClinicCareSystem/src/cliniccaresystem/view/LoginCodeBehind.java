package cliniccaresystem.view;

import java.io.IOException;

import cliniccaresystem.Main;
import cliniccaresystem.model.ResultCode;
import cliniccaresystem.model.USState;
import cliniccaresystem.viewmodel.LoginViewModel;
import cliniccaresystem.viewmodel.SignUpViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class LoginCodeBehind {

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;
    
    @FXML
    private Label errorLabel;
    
    @FXML
    private RadioButton nurseRadioButton;

    @FXML
    private ToggleGroup userTypeRadioGroup;

    @FXML
    private RadioButton adminRadioButton;
    
    private LoginViewModel viewmodel;

    public LoginCodeBehind() {
		this.viewmodel = new LoginViewModel();
	}

	@FXML 
	void initialize() { 
		this.bindToViewModel(); 
		this.setupChangeListeners();
		this.nurseRadioButton.setSelected(true);
	}

	private void bindToViewModel() {
		this.usernameTextField.textProperty().bindBidirectional(this.viewmodel.usernameProperty());
		this.passwordTextField.textProperty().bindBidirectional(this.viewmodel.passwordProperty());
		this.nurseRadioButton.selectedProperty().bindBidirectional(this.viewmodel.nurseLoginProperty());
		this.adminRadioButton.selectedProperty().bindBidirectional(this.viewmodel.adminLoginProperty());
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
	}

    @FXML
    void onLogin(ActionEvent event) {
    	ResultCode result = this.viewmodel.Login();
    	
    	if (result.equals(ResultCode.Success)) {
    		if (this.viewmodel.nurseLoginProperty().getValue()) {
    			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            	try {
        			Main.changeScene(currentStage, Main.HOMEPAGE_PAGE_PATH, Main.HOMEPAGE_PAGE_TITLE);
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
    		} else {
    			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            	try {
        			Main.changeScene(currentStage, Main.ADMIN_HOMEPAGE_PAGE_PATH, Main.ADMIN_HOMEPAGE_PAGE_TITLE);
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
    		}
    	} else {
    		this.errorLabel.textProperty().setValue("Invalid Credentials");
    	}
    }

    @FXML
    void onGoToSignUpPage(ActionEvent event) {
    	Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	
    	try {
			Main.changeScene(currentStage, Main.SIGN_UP_PAGE_PATH, Main.SIGN_UP_PAGE_TITLE);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}

