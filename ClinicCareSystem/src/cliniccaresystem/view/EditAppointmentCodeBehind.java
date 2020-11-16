package cliniccaresystem.view;

import java.io.IOException;

import cliniccaresystem.Main;
import cliniccaresystem.model.Appointment;
import cliniccaresystem.model.Doctor;
import cliniccaresystem.model.Gender;
import cliniccaresystem.model.Patient;
import cliniccaresystem.model.ResultCode;
import cliniccaresystem.model.USState;
import cliniccaresystem.viewmodel.EditAppointmentViewModel;
import cliniccaresystem.viewmodel.EditPatientViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class EditAppointmentCodeBehind {

	@FXML
    private Label nurseInfoLabel;

    @FXML
    private Label patientInfoLabel;
    
    @FXML
    private ComboBox<Doctor> doctorComboBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<Integer> hourComboBox;

    @FXML
    private ComboBox<Integer> minuteComboBox;

    @FXML
    private TextField reasonTextField;

    @FXML
    private Button editAppointmentButton;

    @FXML
    private RadioButton amRadioButton;

    @FXML
    private ToggleGroup periodGroup;

    @FXML
    private RadioButton pmRadioButton;

    @FXML
    private Label errorLabel;
    
    private EditAppointmentViewModel viewmodel;
    
	public EditAppointmentCodeBehind() {
		this.viewmodel = new EditAppointmentViewModel();
	}

	@FXML 
	void initialize() { 
		this.initializeTimeComboBoxes();
		this.bindToViewModel(); 
		this.setupChangeListeners();
	}
	
	private void bindToViewModel() {
		this.reasonTextField.textProperty().bindBidirectional(this.viewmodel.reasonForVisitProperty());
		this.datePicker.valueProperty().bindBidirectional(this.viewmodel.appointmentDateProperty());  		
		this.hourComboBox.valueProperty().bindBidirectional(this.viewmodel.hourProperty());
		this.minuteComboBox.valueProperty().bindBidirectional(this.viewmodel.minuteProperty());
		this.amRadioButton.selectedProperty().bindBidirectional(this.viewmodel.amProperty());
		this.pmRadioButton.selectedProperty().bindBidirectional(this.viewmodel.pmProperty());
		this.nurseInfoLabel.textProperty().bindBidirectional(this.viewmodel.nurseInfoProperty());
		this.patientInfoLabel.textProperty().bindBidirectional(this.viewmodel.patientInfoProperty());
		this.doctorComboBox.valueProperty().bindBidirectional(this.viewmodel.doctorProperty());
	}

	private void initializeTimeComboBoxes() {
		this.hourComboBox.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
		this.minuteComboBox.getItems().addAll(00, 15, 30, 45);
		this.doctorComboBox.getItems().addAll(this.viewmodel.getAllDoctors());
	}
	
	private void setupChangeListeners() {
		this.reasonTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (this.viewmodel.checkIfAppointmentInfoIsValid().equals(ResultCode.IsValid) && this.viewmodel.hasAppointmentBeenChanged()) {
				this.editAppointmentButton.setDisable(false);
			} else {
				this.editAppointmentButton.setDisable(true);
			}
		});
		
		this.datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (this.viewmodel.checkIfAppointmentInfoIsValid().equals(ResultCode.IsValid) && this.viewmodel.hasAppointmentBeenChanged()) {
				this.editAppointmentButton.setDisable(false);
			} else {
				this.editAppointmentButton.setDisable(true);
			}
		});
		
		this.hourComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (this.viewmodel.checkIfAppointmentInfoIsValid().equals(ResultCode.IsValid) && this.viewmodel.hasAppointmentBeenChanged()) {
				this.editAppointmentButton.setDisable(false);
			} else {
				this.editAppointmentButton.setDisable(true);
			}
		});
		
		this.minuteComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (this.viewmodel.checkIfAppointmentInfoIsValid().equals(ResultCode.IsValid) && this.viewmodel.hasAppointmentBeenChanged()) {
				this.editAppointmentButton.setDisable(false);
			} else {
				this.editAppointmentButton.setDisable(true);
			}
		});
		
		this.amRadioButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (this.viewmodel.checkIfAppointmentInfoIsValid().equals(ResultCode.IsValid) && this.viewmodel.hasAppointmentBeenChanged()) {
				this.editAppointmentButton.setDisable(false);
			} else {
				this.editAppointmentButton.setDisable(true);
			}
		});
		
		this.pmRadioButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (this.viewmodel.checkIfAppointmentInfoIsValid().equals(ResultCode.IsValid) && this.viewmodel.hasAppointmentBeenChanged()) {
				this.editAppointmentButton.setDisable(false);
			} else {
				this.editAppointmentButton.setDisable(true);
			}
		});
		
		this.doctorComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (this.viewmodel.checkIfAppointmentInfoIsValid().equals(ResultCode.IsValid)) {
				this.editAppointmentButton.setDisable(false);
			} else {
				this.editAppointmentButton.setDisable(true);
			}
		});
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

    @FXML
    void onEditAppointment(ActionEvent event) {
    	var result = this.viewmodel.editAppointment();
    	
    	if (result.equals(ResultCode.Success)) {
    		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        	
        	try {
    			Main.changeScene(currentStage, Main.HOMEPAGE_PAGE_PATH, Main.HOMEPAGE_PAGE_TITLE);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	} else if (result.equals(ResultCode.AlreadyExists)) {
    		this.errorLabel.setText("Appointment already scheduled for this time");
    	}
    }
    
    public void setSelectedPatient(Patient patient) {
		this.viewmodel.setSelectedPatient(patient);
	}
    
    public void setSelectedAppointment(Appointment appointment) {
		this.viewmodel.populateAppointmentInfoFields(appointment);
	}
}

