package cliniccaresystem.model;

import java.time.LocalDate;

public class Admin extends User{

	private int adminId;
	
	public Admin(int adminId, String firstName, String lastName, LocalDate dateOfBirth, MailingAddress mailingAddress, String phoneNumber) {
		super(null, firstName, lastName, dateOfBirth, mailingAddress, phoneNumber);
		this.setAdminId(adminId);
	}
	
	public Admin(String firstName, String lastName, LocalDate dateOfBirth, MailingAddress mailingAddress, String phoneNumber) {
		super(null, firstName, lastName, dateOfBirth, mailingAddress, phoneNumber);
	}
	
	
	

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

}

