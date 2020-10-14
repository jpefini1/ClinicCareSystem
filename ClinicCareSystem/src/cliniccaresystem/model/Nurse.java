package cliniccaresystem.model;

import java.time.LocalDate;

public class Nurse extends User{

	private int nurseId;
	
	public Nurse(int nurseId, String firstName, String lastName, LocalDate dateOfBirth, MailingAddress mailingAddress, String phoneNumber) {
		super(null, firstName, lastName, dateOfBirth, mailingAddress, phoneNumber);
		this.setNurseId(nurseId);
	}
	
	public Nurse(String firstName, String lastName, LocalDate dateOfBirth, MailingAddress mailingAddress, String phoneNumber) {
		super(null, firstName, lastName, dateOfBirth, mailingAddress, phoneNumber);
	}
	
	
	

	public int getNurseId() {
		return nurseId;
	}

	public void setNurseId(int nurseId) {
		this.nurseId = nurseId;
	}

}
