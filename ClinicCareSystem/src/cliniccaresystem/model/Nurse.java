package cliniccaresystem.model;

import java.time.LocalDate;

public class Nurse extends User{

	private int nurseId;
	
	public Nurse(int nurseId, String firstName, String lastName, LocalDate dateOfBirth, MailingAddress mailingAddress, String phoneNumber) {
		super(firstName, lastName, dateOfBirth, mailingAddress, phoneNumber);
		this.setNurseId(nurseId);
		// TODO Auto-generated constructor stub
	}
	
	public Nurse(String firstName, String lastName, LocalDate dateOfBirth, MailingAddress mailingAddress, String phoneNumber) {
		super(firstName, lastName, dateOfBirth, mailingAddress, phoneNumber);
		// TODO Auto-generated constructor stub
	}
	
	
	

	public int getNurseId() {
		return nurseId;
	}

	public void setNurseId(int nurseId) {
		this.nurseId = nurseId;
	}

}
