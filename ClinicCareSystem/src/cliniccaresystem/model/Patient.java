package cliniccaresystem.model;

import java.time.LocalDate;

public class Patient extends User {

private int patientId;
	
	public Patient(int patientId, String firstName, String lastName, LocalDate dateOfBirth, MailingAddress mailingAddress, String phoneNumber) {
		super(patientId, null, firstName, lastName, dateOfBirth, mailingAddress, phoneNumber);
	}
	
	public Patient(String firstName, String lastName, LocalDate dateOfBirth, MailingAddress mailingAddress, String phoneNumber) {
		super(null, firstName, lastName, dateOfBirth, mailingAddress, phoneNumber);
		//this.setPatientId(patientId);
	}
	
	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int nurseId) {
		this.patientId = nurseId;
	}
}
