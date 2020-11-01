package cliniccaresystem.model;

import java.time.LocalDateTime;

public class Appointment {

	private int appointmentId;
	
	private int patientId;
	
	private LocalDateTime appointmentTime;
	
	private String reasonForVisit;
	
	public Appointment(int patientId, LocalDateTime appointmentTime, String reasonForVisit) {
		
		this.patientId = patientId;
		this.appointmentTime = appointmentTime;
		this.reasonForVisit = reasonForVisit;
	}
	
	public Appointment(int appointmentId, int patientId, LocalDateTime appointmentTime, String reasonForVisit) {
		
		this.patientId = patientId;
		this.appointmentTime = appointmentTime;
		this.reasonForVisit = reasonForVisit;
	}

	public int getAppointmentId() {
		return this.appointmentId;
	}

	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}

	public int getPatientId() {
		return this.patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public LocalDateTime getAppointmentTime() {
		return this.appointmentTime;
	}

	public void setAppointmentTime(LocalDateTime appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	public String getReasonForVisit() {
		return this.reasonForVisit;
	}

	public void setReasonForVisit(String reasonForVisit) {
		this.reasonForVisit = reasonForVisit;
	}
	
	
}
