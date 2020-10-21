package cliniccaresystem.model;

import java.time.LocalDate;

public class Patient extends User {

private int patientId;
private Gender gender;
	
	public Patient(int patientId, String firstName, String lastName, LocalDate dateOfBirth, MailingAddress mailingAddress, String phoneNumber, Gender gender) {
		super(0, null, firstName, lastName, dateOfBirth, mailingAddress, phoneNumber);
		this.patientId = patientId;
		this.gender = gender;
	}
	
	public Patient(String firstName, String lastName, LocalDate dateOfBirth, MailingAddress mailingAddress, String phoneNumber, Gender gender) {
		super(null, firstName, lastName, dateOfBirth, mailingAddress, phoneNumber);
		this.gender = gender;
	}
	
	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return this.getFirstName() + " " + this.getLastName() + " " + this.getDateOfBirth().toString() + " " + this.getGender() + " ID: " + this.patientId;
	}
}
