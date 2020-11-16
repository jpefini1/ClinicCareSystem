package cliniccaresystem.model;

import java.time.LocalDate;

public class Doctor extends User{

	private int doctorId;
	
	public Doctor(int doctorId, String firstName, String lastName, LocalDate dateOfBirth, MailingAddress mailingAddress, String phoneNumber) {
		super(null, firstName, lastName, dateOfBirth, mailingAddress, phoneNumber);
		this.doctorId = doctorId;
	}
	
	public Doctor(String firstName, String lastName, LocalDate dateOfBirth, MailingAddress mailingAddress, String phoneNumber) {
		super(null, firstName, lastName, dateOfBirth, mailingAddress, phoneNumber);
	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}
	
	@Override
	public String toString() {
		return this.getFirstName() + " " + this.getLastName();
	}

}
