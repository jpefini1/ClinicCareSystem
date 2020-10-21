package cliniccaresystem.view;

import java.io.IOException;

import cliniccaresystem.Main;
import cliniccaresystem.model.ActiveUser;
import cliniccaresystem.model.Patient;
import cliniccaresystem.model.ResultCode;
import cliniccaresystem.viewmodel.HomepageViewModel;
import cliniccaresystem.viewmodel.LoginViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class HomepageCodeBehind {

    @FXML
    private ListView<Patient> patientListView;

    @FXML
    private Label userInfoLabel;
    
    @FXML
    private Button editPatientButton;
    
    @FXML
    private Button searchButton;

    @FXML
    private DatePicker dobDatePicker;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private Button showAllButton;
    
    private HomepageViewModel viewmodel;
    
    public HomepageCodeBehind() {
		this.viewmodel = new HomepageViewModel();
	}

	@FXML 
	void initialize() { 
		this.bindToViewModel(); 
	}

	private void bindToViewModel() {
		this.userInfoLabel.textProperty().bindBidirectional(this.viewmodel.userInfoProperty());
		this.patientListView.setItems(this.viewmodel.patientListProperty());
		this.editPatientButton.disableProperty().bind(this.patientListView.getSelectionModel().selectedItemProperty().isNull());
		this.firstNameTextField.textProperty().bindBidirectional(this.viewmodel.firstNameSearchProperty());
		this.lastNameTextField.textProperty().bindBidirectional(this.viewmodel.lastNameSearchProperty());
		this.dobDatePicker.valueProperty().bindBidirectional(this.viewmodel.dobSearchProperty());
	}


    @FXML
    void onLogout(ActionEvent event) {
    	this.viewmodel.logout();
    	
    	Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();	
    	try {
			Main.changeScene(currentStage, Main.LOGIN_PAGE_PATH, Main.LOGIN_PAGE_TITLE);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void onRegisterPatient(ActionEvent event) {
    	Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();	
    	try {
			Main.changeScene(currentStage, Main.PATIENT_REGISTRATION_PAGE_PATH, Main.PATIENT_REGISTRATION_PAGE_TITLE);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void onEditPatient(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource(Main.EDIT_PATIENT_PAGE_PATH));
		loader.load();
		
		var codeBehind = (EditPatientCodeBehind) loader.getController();
		codeBehind.setSelectedPatient(this.patientListView.getSelectionModel().getSelectedItem());
		
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene newScene = new Scene(loader.getRoot());
		currentStage.setScene(newScene);
		currentStage.setTitle(Main.EDIT_PATIENT_PAGE_TITLE);
    }
    
    @FXML
    void onSearch(ActionEvent event) {
    	var result = this.viewmodel.searchPatients();
    	
    	if (result == ResultCode.Success) {
    		this.showAllButton.setDisable(false);
    	}
    }

    @FXML
    void onShowAll(ActionEvent event) {
    	this.viewmodel.showAllPatients();
    	this.showAllButton.setDisable(true);
    }
}
