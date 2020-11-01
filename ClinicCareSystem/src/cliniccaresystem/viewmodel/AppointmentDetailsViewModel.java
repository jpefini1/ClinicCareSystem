package cliniccaresystem.viewmodel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import cliniccaresystem.model.ActiveUser;
import cliniccaresystem.model.Appointment;
import cliniccaresystem.model.Patient;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class AppointmentDetailsViewModel {
	
	private SimpleStringProperty systolicBPProperty;
	private SimpleStringProperty diastolicBPProperty;
	private SimpleStringProperty pulseProperty;
	private SimpleStringProperty weightProperty;
	private SimpleStringProperty tempProperty;
	private SimpleStringProperty reasonForVisitProperty;
	private SimpleStringProperty timeProperty;
	private SimpleStringProperty dateProperty;
	private SimpleStringProperty nurseInfoProperty;
	private SimpleStringProperty patientInfoProperty;
	private SimpleBooleanProperty inputCheckResultsIsDisabled;
	
	private Appointment selectedAppointment = null;
	
	
	public AppointmentDetailsViewModel() {
		this.systolicBPProperty = new SimpleStringProperty();
		this.diastolicBPProperty = new SimpleStringProperty();
		this.pulseProperty = new SimpleStringProperty();
		this.weightProperty = new SimpleStringProperty();
		this.tempProperty = new SimpleStringProperty();
		this.reasonForVisitProperty = new SimpleStringProperty();
		this.timeProperty = new SimpleStringProperty();
		this.dateProperty = new SimpleStringProperty();
		this.nurseInfoProperty = new SimpleStringProperty();
		this.patientInfoProperty = new SimpleStringProperty();
		this.inputCheckResultsIsDisabled = new SimpleBooleanProperty();
		
		this.nurseInfoProperty.setValue(ActiveUser.getActiveUser().toString());
	}


	public SimpleStringProperty systolicBPProperty() {
		return this.systolicBPProperty;
	}


	public SimpleStringProperty diastolicBPProperty() {
		return this.diastolicBPProperty;
	}


	public SimpleStringProperty pulseProperty() {
		return this.pulseProperty;
	}


	public SimpleStringProperty weightProperty() {
		return this.weightProperty;
	}


	public SimpleStringProperty tempProperty() {
		return this.tempProperty;
	}


	public SimpleStringProperty reasonForVisitProperty() {
		return this.reasonForVisitProperty;
	}


	public SimpleStringProperty timeProperty() {
		return this.timeProperty;
	}


	public SimpleStringProperty dateProperty() {
		return this.dateProperty;
	}


	public SimpleStringProperty nurseInfoProperty() {
		return this.nurseInfoProperty;
	}


	public SimpleStringProperty patientInfoProperty() {
		return this.patientInfoProperty;
	}
	
	public SimpleBooleanProperty inputCheckResultsIsDisabled() {
		return this.inputCheckResultsIsDisabled;
	}


	public void setPatientInfo(Patient patient) {
		this.patientInfoProperty.setValue(patient.toString());
	}


	public void initializeAppointmentInfo(Appointment appointment) {
		this.selectedAppointment = appointment;
		
		this.dateProperty.setValue(appointment.getAppointmentDateTime().toLocalDate().toString());
		this.timeProperty.setValue(appointment.getAppointmentDateTime().toLocalTime().format(DateTimeFormatter.ofPattern("hh:mm a")));
		this.reasonForVisitProperty.setValue(appointment.getReasonForVisit());
		
		this.inputCheckResultsIsDisabled.setValue(!this.hasAppointmentTimeElapsed());
	}
	
	private boolean hasAppointmentTimeElapsed() {
		return this.selectedAppointment.getAppointmentDateTime().isBefore(LocalDateTime.now());
	}
}
