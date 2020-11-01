package cliniccaresystem.view;

import java.io.IOException;

import cliniccaresystem.Main;
import cliniccaresystem.model.Appointment;
import cliniccaresystem.model.Patient;
import cliniccaresystem.model.ResultCode;
import cliniccaresystem.viewmodel.AppointmentDetailsViewModel;
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
   		this.setupChangeListeners();
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
   		this.updateButton.visibleProperty().bindBidirectional(this.viewmodel.updateIsVisibleProperty());
   		this.inputResultsButton.visibleProperty().bindBidirectional(this.viewmodel.inputCheckResultsIsVisible());
   	}
	
	private void setupChangeListeners() {
		this.systolicBPTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.matches("^[0-9]{1,3}") || newValue.isBlank()) {
				if (this.viewmodel.areRoutineResultsValid().equals(ResultCode.IsValid)) {
					this.updateButton.setDisable(false);
				} else {
					this.updateButton.setDisable(true);
				}
			} else {
				if (oldValue == null) {
					this.systolicBPTextField.setText("");
				} else {
					this.systolicBPTextField.setText(oldValue);
				}
			}
		});
		
		this.diastolicBPTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.matches("^[0-9]{1,3}") || newValue.isBlank()) {
				if (this.viewmodel.areRoutineResultsValid().equals(ResultCode.IsValid)) {
					this.updateButton.setDisable(false);
				} else {
					this.updateButton.setDisable(true);
				}
			} else {
				if (oldValue == null) {
					this.diastolicBPTextField.setText("");
				} else {
					this.diastolicBPTextField.setText(oldValue);
				}
			}
		});
		
		this.pulseTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.matches("^[0-9]{1,3}") || newValue.isBlank()) {
				if (this.viewmodel.areRoutineResultsValid().equals(ResultCode.IsValid)) {
					this.updateButton.setDisable(false);
				} else {
					this.updateButton.setDisable(true);
				}
			} else {
				if (oldValue == null) {
					this.pulseTextField.setText("");
				} else {
					this.pulseTextField.setText(oldValue);
				}
			}
		});
		
		this.weightTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.matches("^[1-9]{1}") || newValue.matches("^[1-9]{1}[0-9]{1,2}") || 
					newValue.matches("^[1-9]{1}[0-9]{1,2}\\.") || newValue.matches("^[1-9]{1}[0-9]{1,2}\\.[0-9]{1,2}") || newValue.isBlank()) {
				if (this.viewmodel.areRoutineResultsValid().equals(ResultCode.IsValid)) {
					this.updateButton.setDisable(false);
				} else {
					this.updateButton.setDisable(true);
				}
			} else {
				if (oldValue == null) {
					this.weightTextField.setText("");
				} else {
					this.weightTextField.setText(oldValue);
				}
			}
		});
		
		this.tempTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.matches("^[1-9]{1}") || newValue.matches("^[1-9]{1}[0-9]{1,2}") || 
					newValue.matches("^[1-9]{1}[0-9]{1,2}\\.") || newValue.matches("^[1-9]{1}[0-9]{1,2}\\.[0-9]{1,2}") || newValue.isBlank()) {
				if (this.viewmodel.areRoutineResultsValid().equals(ResultCode.IsValid)) {
					this.updateButton.setDisable(false);
				} else {
					this.updateButton.setDisable(true);
				}
			} else {
				if (oldValue == null) {
					this.tempTextField.setText("");
				} else {
					this.tempTextField.setText(oldValue);
				}
			}
		});
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
    	this.setDisableRoutineCheckLabels(false);
    	this.setDisableRoutineCheckTextFields(false);
    	this.inputResultsButton.setDisable(true);
    }
    
    private void setDisableRoutineCheckTextFields(boolean isDisabled) {
    	this.systolicBPTextField.setDisable(isDisabled);
    	this.diastolicBPTextField.setDisable(isDisabled);
    	this.pulseTextField.setDisable(isDisabled);
    	this.weightTextField.setDisable(isDisabled);
    	this.tempTextField.setDisable(isDisabled);
    }
    
    private void setDisableRoutineCheckLabels(boolean isDisabled) {
    	this.routineResulsLabel.setDisable(isDisabled);
    	this.systolicBPLabel.setDisable(isDisabled);
    	this.diastolicBPLabel.setDisable(isDisabled);
    	this.pulseLabel.setDisable(isDisabled);
    	this.weightLabel.setDisable(isDisabled);
    	this.tempLabel.setDisable(isDisabled);
    }

    @FXML
    void onUpdate(ActionEvent event) {
    	var result = this.viewmodel.addRoutineCheckResults();
    	
    	if (result.equals(ResultCode.Success)) {
    		this.setDisableRoutineCheckTextFields(true);
    		this.updateButton.setDisable(true);
    		this.updateButton.setVisible(false);
    		this.inputResultsButton.setVisible(false);
    	}
    }
    
    public void setPatientInfo(Patient patient) {
		this.viewmodel.setPatientInfo(patient);
	}
    
    public void setSelectedAppointment(Appointment appointment) {
		this.viewmodel.initializeAppointmentInfo(appointment);
	}

}

