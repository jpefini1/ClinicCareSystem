package cliniccaresystem.viewmodel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import cliniccaresystem.datalayer.AppointmentDatabaseClient;
import cliniccaresystem.datalayer.DoctorDatabaseClient;
import cliniccaresystem.model.ActiveUser;
import cliniccaresystem.model.Appointment;
import cliniccaresystem.model.Doctor;
import cliniccaresystem.model.Patient;
import cliniccaresystem.model.ResultCode;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class CreateAppointmentViewModel {

	private SimpleStringProperty nurseInfoProperty;
	private SimpleStringProperty patientInfoProperty;
	
	private SimpleObjectProperty<LocalDate> appointmentDateProperty;
	private SimpleObjectProperty<Integer> hourProperty;
	private SimpleObjectProperty<Integer> minuteProperty;
	private SimpleStringProperty reasonForVisitProperty;
	private SimpleBooleanProperty amProperty;
	private SimpleBooleanProperty pmProperty;
	
	private Patient selectedPatient = null;
	private Doctor selectedDoctor = null;

	public CreateAppointmentViewModel() {
		this.appointmentDateProperty = new SimpleObjectProperty<LocalDate>();
		this.hourProperty = new SimpleObjectProperty<Integer>();
		this.minuteProperty = new SimpleObjectProperty<Integer>();
		this.reasonForVisitProperty = new SimpleStringProperty();
		this.amProperty = new SimpleBooleanProperty();
		this.pmProperty = new SimpleBooleanProperty();
		this.nurseInfoProperty = new SimpleStringProperty();
		this.patientInfoProperty = new SimpleStringProperty();
		
		this.nurseInfoProperty.setValue(ActiveUser.getActiveUser().toString());
	}
	
	public ResultCode scheduleAppointment() {
		Appointment appointment = this.makeAppointment();
		
		try {
			if (AppointmentDatabaseClient.checkForDifferentAppointmentForSamePatientAtDateTimeOf(appointment)) {
				return ResultCode.AlreadyExists;
			}
			
			if (AppointmentDatabaseClient.checkForDoubleBookForDoctor(appointment)) {
				return ResultCode.DoubleBooked;
			}
			
			if (AppointmentDatabaseClient.scheduleAppointment(appointment) == ResultCode.Success) {
				return ResultCode.Success;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return ResultCode.ConnectionError;
		}
		
		return ResultCode.ConnectionError;
	}
	
	private Appointment makeAppointment() {
		int patientId = this.selectedPatient.getPatientId();
		LocalDateTime appointmentTime = this.makeAppointmentDateTime();
		String reasonForVisit = this.reasonForVisitProperty.getValue();
		
		return new Appointment(patientId, appointmentTime, reasonForVisit, this.selectedDoctor);
	} 
	
	private LocalDateTime makeAppointmentDateTime() {
		LocalDate appointmentDate = this.appointmentDateProperty.getValue();
		LocalTime appointmentTime = this.getLocalTimeIn24HourFormat();
		return LocalDateTime.of(appointmentDate, appointmentTime);
	}
	
	private LocalTime getLocalTimeIn24HourFormat() {
		var hour = this.hourProperty.getValue();
		
		if (this.pmProperty.getValue()) {
			hour += 12;
			
			if (hour >= 24) {
				hour = 0;
			}
		} else if (this.amProperty.getValue()) {
			if (hour == 12) {
				hour = 0;
			}
		}
		
		return LocalTime.of(hour, this.minuteProperty.getValue());
	}

	public ResultCode checkIfAppointmentInfoIsValid() {

		if (this.appointmentDateProperty.getValue() == null) {
			return ResultCode.IncorrectInput;
		}
		
		if (this.hourProperty.getValue() == null) {
			return ResultCode.IncorrectInput;
		}
		
		if (this.minuteProperty.getValue() == null) {
			return ResultCode.IncorrectInput;
		}
		
		if (this.reasonForVisitProperty.getValue() == null || this.reasonForVisitProperty.getValue().isBlank()) {
			return ResultCode.IncorrectInput;
		}
		
		if (!this.pmProperty.getValue() && !this.amProperty.getValue()) {
			return ResultCode.IncorrectInput;
		}
		
		if (this.selectedDoctor == null) {
			return ResultCode.IncorrectInput;
		}
		
		return ResultCode.IsValid;
	}

	public SimpleObjectProperty<LocalDate> appointmentDateProperty() {
		return this.appointmentDateProperty;
	}

	public SimpleObjectProperty<Integer> hourProperty() {
		return this.hourProperty;
	}

	public SimpleObjectProperty<Integer> minuteProperty() {
		return this.minuteProperty;
	}

	public SimpleStringProperty reasonForVisitProperty() {
		return this.reasonForVisitProperty;
	}
	
	public SimpleBooleanProperty amProperty() {
		return this.amProperty;
	}
	
	public SimpleBooleanProperty pmProperty() {
		return this.pmProperty;
	}

	public SimpleStringProperty nurseInfoProperty() {
		return this.nurseInfoProperty;
	}

	public SimpleStringProperty patientInfoProperty() {
		return this.patientInfoProperty;
	}

	public void setSelectedPatient(Patient patient) {
		this.selectedPatient = patient;
		this.patientInfoProperty.setValue(patient.toString());
	}

	public List<Doctor> getAllDoctors() {
		try {
			return DoctorDatabaseClient.getAllDoctors();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setSelectedDoctor(Doctor doctor) {
		this.selectedDoctor = doctor;
	}
}
