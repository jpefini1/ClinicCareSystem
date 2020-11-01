package cliniccaresystem.view;

import java.io.IOException;

import cliniccaresystem.Main;
import cliniccaresystem.model.Appointment;
import cliniccaresystem.model.Patient;
import cliniccaresystem.viewmodel.AppointmentDetailsViewModel;
import cliniccaresystem.viewmodel.CreateAppointmentViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AppointmentDetailsCodeBehind {

    @FXML
    private Label nurseInfoLabel;

    @FXML
    private Label patientInfoLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private Label routineResulsLabel;

    @FXML
    private Label reasonForVisitLabel;

    @FXML
    private Label systolicBPLabel;

    @FXML
    private Label diastolicBPLabel;

    @FXML
    private Label pulseLabel;

    @FXML
    private Label weightLabel;

    @FXML
    private Label tempLabel;

    @FXML
    private TextField systolicBPTextField;

    @FXML
    private TextField diastolicBPTextField;

    @FXML
    private TextField pulseTextField;

    @FXML
    private TextField weightTextField;

    @FXML
    private TextField tempTextField;

    @FXML
    private Button inputResultsButton;

    @FXML
    private Button updateButton;
    
    private AppointmentDetailsViewModel viewmodel;
    
    public AppointmentDetailsCodeBehind() {
   		this.viewmodel = new AppointmentDetailsViewModel();
   	}

   	@FXML 
   	void initialize() { 
   		this.bindToViewModel(); 
   	}

	private void bindToViewModel() {
   		this.systolicBPTextField.textProperty().bindBidirectional(this.viewmodel.systolicBPProperty());
   		this.diastolicBPTextField.textProperty().bindBidirectional(this.viewmodel.diastolicBPProperty());
   		this.pulseTextField.textProperty().bindBidirectional(this.viewmodel.pulseProperty());
   		this.weightTextField.textProperty().bindBidirectional(this.viewmodel.weightProperty());
   		this.tempTextField.textProperty().bindBidirectional(this.viewmodel.tempProperty());
   		
   		this.reasonForVisitLabel.textProperty().bindBidirectional(this.viewmodel.reasonForVisitProperty());
   		this.timeLabel.textProperty().bindBidirectional(this.viewmodel.timeProperty());
   		this.dateLabel.textProperty().bindBidirectional(this.viewmodel.dateProperty());
   		this.nurseInfoLabel.textProperty().bindBidirectional(this.viewmodel.nurseInfoProperty());
   		this.patientInfoLabel.textProperty().bindBidirectional(this.viewmodel.patientInfoProperty());
   		this.inputResultsButton.disableProperty().bindBidirectional(this.viewmodel.inputCheckResultsIsDisabled());
   	}

    @FXML
    void onBack(ActionEvent event) {
    	Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	
    	try {
			Main.changeScene(currentStage, Main.HOMEPAGE_PAGE_PATH, Main.HOMEPAGE_PAGE_TITLE);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void onInputResults(ActionEvent event) {
    	this.routineResulsLabel.setDisable(false);
    	this.systolicBPLabel.setDisable(false);
    	this.systolicBPTextField.setDisable(false);
    	this.diastolicBPLabel.setDisable(false);
    	this.diastolicBPTextField.setDisable(false);
    	this.pulseLabel.setDisable(false);
    	this.pulseTextField.setDisable(false);
    	this.weightLabel.setDisable(false);
    	this.weightTextField.setDisable(false);
    	this.tempLabel.setDisable(false);
    	this.tempTextField.setDisable(false);
    	
    	this.inputResultsButton.setDisable(true);
    }

    @FXML
    void onUpdate(ActionEvent event) {

    }
    
    public void setPatientInfo(Patient patient) {
		this.viewmodel.setPatientInfo(patient);
	}
    
    public void setSelectedAppointment(Appointment appointment) {
		this.viewmodel.initializeAppointmentInfo(appointment);
	}

}

