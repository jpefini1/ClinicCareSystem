package cliniccaresystem.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {

	private int appointmentId;
	
	private int patientId;
	
	private LocalDateTime appointmentDateTime;
	
	private String reasonForVisit;
	private String formattedDateTime;

	
	public Appointment(int patientId, LocalDateTime appointmentDateTime, String reasonForVisit) {
		
		this.patientId = patientId;
		this.appointmentDateTime = appointmentDateTime;
		this.reasonForVisit = reasonForVisit;
		this.formattedDateTime = appointmentDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE) + " " + appointmentDateTime.format(DateTimeFormatter.ISO_LOCAL_TIME);
	}
	
	public Appointment(int appointmentId, int patientId, LocalDateTime appointmentTime, String reasonForVisit) {
		
		this.patientId = patientId;
		this.appointmentDateTime = appointmentTime;
		this.reasonForVisit = reasonForVisit;
		this.formattedDateTime = appointmentDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE) + " " + appointmentDateTime.format(DateTimeFormatter.ISO_LOCAL_TIME);
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

	public LocalDateTime getAppointmentDateTime() {
		return this.appointmentDateTime;
	}

	public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
		this.appointmentDateTime = appointmentDateTime;
	}

	public String getReasonForVisit() {
		return this.reasonForVisit;
	}

	public void setReasonForVisit(String reasonForVisit) {
		this.reasonForVisit = reasonForVisit;
	}
		
	public String getFormattedDateTime() {
		return this.formattedDateTime;
	}

	public void setFormattedDateTime(String formattedDateTime) {
		this.formattedDateTime = formattedDateTime;
	}
}
