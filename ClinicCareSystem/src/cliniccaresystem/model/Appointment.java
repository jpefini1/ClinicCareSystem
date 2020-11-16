package cliniccaresystem.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Appointment {

	private int appointmentId;
	
	private int patientId;
	
	private LocalDateTime appointmentDateTime;
	
	private String reasonForVisit;
	private String formattedDateTime;

	private List<Test> orderedTests;
	private String finalDiagnosis;
	private Doctor doctor;
	
	public Appointment(int patientId, LocalDateTime appointmentDateTime, String reasonForVisit, Doctor doctor) {
		this.patientId = patientId;
		this.appointmentDateTime = appointmentDateTime;
		this.reasonForVisit = reasonForVisit;
		this.doctor = doctor;
		
		this.orderedTests = new ArrayList<Test>();
		this.finalDiagnosis = "";
		
		this.updateFormattedAppointmentTime();
	}
	
	public Appointment(int appointmentId, int patientId, LocalDateTime appointmentTime, String reasonForVisit, Doctor doctor) {
		this.appointmentId = appointmentId;
		this.patientId = patientId;
		this.appointmentDateTime = appointmentTime;
		this.reasonForVisit = reasonForVisit;
		this.doctor = doctor;
		
		this.orderedTests = new ArrayList<Test>();
		this.finalDiagnosis = "";
		
		this.updateFormattedAppointmentTime();
	}
	
	private void updateFormattedAppointmentTime() {
		this.formattedDateTime = appointmentDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE) + " " + 
				appointmentDateTime.toLocalTime().format(DateTimeFormatter.ofPattern("hh:mm a"));
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
		this.updateFormattedAppointmentTime();
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

	public List<Test> getOrderedTests() {
		return orderedTests;
	}

	public void setOrderedTests(List<Test> orderedTests) {
		this.orderedTests = orderedTests;
	}

	public String getFinalDiagnosis() {
		return finalDiagnosis;
	}

	public void setFinalDiagnosis(String finalDiagnosis) {
		this.finalDiagnosis = finalDiagnosis;
	}
	
	public Doctor getDoctor() {
		return this.doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
}
