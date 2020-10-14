package cliniccaresystem.view;

import java.io.IOException;

import cliniccaresystem.Main;
import cliniccaresystem.model.ActiveUser;
import cliniccaresystem.model.Patient;
import cliniccaresystem.viewmodel.HomepageViewModel;
import cliniccaresystem.viewmodel.LoginViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class HomepageCodeBehind {

    @FXML
    private ListView<Patient> patientListView;

    @FXML
    private Label userInfoLabel;
    
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
    	
    }

}
