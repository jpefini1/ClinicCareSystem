package cliniccaresystem.model;

import java.time.LocalDate;

public class User {

	private int userId;
	private String username;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;
	private MailingAddress mailingAddress;
	private String phoneNumber;
	
	public User(int userId, String username, String firstName, String lastName, LocalDate dateOfBirth, MailingAddress mailingAddress, String phoneNumber) {
		this.userId = userId;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.mailingAddress = mailingAddress;
		this.phoneNumber = phoneNumber;
	}
	
	public User(String username, String firstName, String lastName, LocalDate dateOfBirth, MailingAddress mailingAddress, String phoneNumber) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.mailingAddress = mailingAddress;
		this.phoneNumber = phoneNumber;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public MailingAddress getMailingAddress() {
		return mailingAddress;
	}

	public void setMailingAddress(MailingAddress mailingAddress) {
		this.mailingAddress = mailingAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String toString() {
		return this.firstName + " " + this.lastName + " : Username: " + this.username + " UserID: " + this.userId;
	}
}
