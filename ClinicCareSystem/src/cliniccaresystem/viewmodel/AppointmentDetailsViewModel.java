package cliniccaresystem.viewmodel;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import cliniccaresystem.datalayer.NurseDatabaseClient;
import cliniccaresystem.datalayer.RoutineCheckDatabaseClient;
import cliniccaresystem.model.ActiveUser;
import cliniccaresystem.model.Appointment;
import cliniccaresystem.model.Nurse;
import cliniccaresystem.model.Patient;
import cliniccaresystem.model.ResultCode;
import cliniccaresystem.model.RoutineCheckResults;
import javafx.beans.property.Property;
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
	private SimpleBooleanProperty updateIsVisibleProperty;
	private SimpleBooleanProperty inputCheckResultsIsVisible;
	
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
		this.updateIsVisibleProperty = new SimpleBooleanProperty();
		this.inputCheckResultsIsVisible = new SimpleBooleanProperty();
		
		this.nurseInfoProperty.setValue(ActiveUser.getActiveUser().toString());
	}
	
	public ResultCode addRoutineCheckResults() {
		try {
			var routineCheckResults = this.makeRoutineCheckResults();
			return RoutineCheckDatabaseClient.addRoutineCheck(routineCheckResults);
		} catch (SQLException e) {
			e.printStackTrace();
			return ResultCode.ConnectionError;
		}
	}
	
	private RoutineCheckResults makeRoutineCheckResults() throws SQLException {
		int appointmentId = this.selectedAppointment.getAppointmentId();
		int nurseId = NurseDatabaseClient.GetNurseIdOf(ActiveUser.getActiveUser());
		int systolicBP = Integer.parseInt(this.systolicBPProperty.getValue());
		int diastolicBP = Integer.parseInt(this.diastolicBPProperty.getValue());
		int pulse = Integer.parseInt(this.pulseProperty.getValue());
		double weight = Double.parseDouble(this.weightProperty.getValue());
		double bodyTemp = Double.parseDouble(this.tempProperty.getValue());
		
		return new RoutineCheckResults(appointmentId, nurseId, systolicBP, diastolicBP, pulse, weight, bodyTemp);
	}
	
	public ResultCode areRoutineResultsValid() {
		var systolicBP = this.systolicBPProperty.getValue();
		var diastolicBP = this.diastolicBPProperty.getValue();
		var pulse = this.pulseProperty.getValue();
		var weight = this.weightProperty.getValue();
		var temp = this.tempProperty.getValue();
		
		if (systolicBP == null || systolicBP.length() < 2) {
			return ResultCode.IncorrectInput;
		}
		
		if (diastolicBP == null || diastolicBP.length() < 2) {
			return ResultCode.IncorrectInput;
		}
		
		if (pulse == null || pulse.length() < 2) {
			return ResultCode.IncorrectInput;
		}
		
		if (weight == null || !weight.matches("^[0-9]{1,3}\\.[0-9]{1,2}")) {
			return ResultCode.IncorrectInput;
		}
		
		if (temp == null || !temp.matches("^[0-9]{2,3}\\.[0-9]{1,2}")) {
			return ResultCode.IncorrectInput;
		}
		
		return ResultCode.IsValid;
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
	
	public SimpleBooleanProperty updateIsVisibleProperty() {
		return this.updateIsVisibleProperty;
	}


	public void setPatientInfo(Patient patient) {
		this.patientInfoProperty.setValue(patient.toString());
	}


	public void initializeAppointmentInfo(Appointment appointment) {
		try {
			this.selectedAppointment = appointment;
			
			this.dateProperty.setValue(appointment.getAppointmentDateTime().toLocalDate().toString());
			this.timeProperty.setValue(appointment.getAppointmentDateTime().toLocalTime().format(DateTimeFormatter.ofPattern("hh:mm a")));
			this.reasonForVisitProperty.setValue(appointment.getReasonForVisit());
			
			this.initializeRoutineCheckResults();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initializeRoutineCheckResults() throws SQLException {
		var routineCheckResults = RoutineCheckDatabaseClient.getRoutineCheckIfExists(this.selectedAppointment);
		if (routineCheckResults != null) {
			this.systolicBPProperty.setValue(Integer.toString(routineCheckResults.getSystolicBP()));
			this.diastolicBPProperty.setValue(Integer.toString(routineCheckResults.getDiastolicBP()));
			this.pulseProperty.setValue(Integer.toString(routineCheckResults.getPulse()));
			this.weightProperty.setValue(Double.toString(routineCheckResults.getWeight()));
			this.tempProperty.setValue(Double.toString(routineCheckResults.getBodyTemp()));
			
			this.inputCheckResultsIsDisabled.setValue(true);
			this.updateIsVisibleProperty.setValue(false);
		} else {
			this.inputCheckResultsIsDisabled.setValue(!this.hasAppointmentTimeElapsed());
			this.inputCheckResultsIsVisible.setValue(this.hasAppointmentTimeElapsed());
			this.updateIsVisibleProperty.setValue(true);
		}
	}
	
	private boolean hasAppointmentTimeElapsed() {
		return this.selectedAppointment.getAppointmentDateTime().isBefore(LocalDateTime.now());
	}

	public SimpleBooleanProperty inputCheckResultsIsVisible() {
		return this.inputCheckResultsIsVisible;
	}

	
}
