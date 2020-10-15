package cliniccaresystem.viewmodel;

import java.time.LocalDate;

import cliniccaresystem.model.Credentials;
import cliniccaresystem.model.DatabaseClient;
import cliniccaresystem.model.MailingAddress;
import cliniccaresystem.model.Nurse;
import cliniccaresystem.model.ResultCode;
import cliniccaresystem.model.USState;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class SignUpViewModel {

	private SimpleStringProperty usernameProperty;
	private SimpleStringProperty passwordProperty;
	private SimpleStringProperty firstNameProperty;
	private SimpleStringProperty lastNameProperty;
	private SimpleStringProperty phoneNumberProperty;
	private SimpleStringProperty streetProperty;
	private SimpleStringProperty cityProperty;
	private SimpleStringProperty zipCodeProperty;
	private SimpleObjectProperty<USState> stateProperty;
	private SimpleObjectProperty<LocalDate> dateOfBirthProperty;
	
	/**
	 * Instantiates a new login gui view model.
	 */
	public SignUpViewModel() {
		this.usernameProperty = new SimpleStringProperty();
		this.passwordProperty = new SimpleStringProperty();
		this.firstNameProperty = new SimpleStringProperty();
		this.lastNameProperty = new SimpleStringProperty();
		this.dateOfBirthProperty = new SimpleObjectProperty<LocalDate>();
		this.phoneNumberProperty = new SimpleStringProperty();
		this.streetProperty = new SimpleStringProperty();
		this.cityProperty = new SimpleStringProperty();
		this.stateProperty = new SimpleObjectProperty<USState>();
		this.zipCodeProperty = new SimpleStringProperty();
	}
	
	public ResultCode signUp() {
		var infoIsValid = this.checkIfSignUpInfoIsValid().equals(ResultCode.IsValid);
		
		if (infoIsValid) {
			MailingAddress mAddress = this.createMailingAddress();
			Nurse nurse = this.createNurse(mAddress);
			Credentials credentials = this.createCredentials();
			
			return DatabaseClient.AddNurse(nurse, credentials);
		}
		
		return ResultCode.IncorrectInput;
	}

	private Credentials createCredentials() {
		String username = this.usernameProperty.getValue();
		String password = this.passwordProperty.getValue();
		Credentials credentials = new Credentials(username, password);
		return credentials;
	}

	private Nurse createNurse(MailingAddress mAddress) {
		String firstName = this.firstNameProperty.getValue();
		String lastName = this.lastNameProperty.getValue();
		LocalDate dateOfBirth = this.dateOfBirthProperty.getValue();
		String phoneNumber = this.phoneNumberProperty.getValue();
		Nurse nurse = new Nurse(firstName, lastName, dateOfBirth, mAddress, phoneNumber);
		return nurse;
	}

	private MailingAddress createMailingAddress() {
		String street = this.streetProperty.getValue();
		String city = this.cityProperty.getValue();
		USState state = this.stateProperty.getValue();
		String zipcode = this.zipCodeProperty.getValue();
		MailingAddress mAddress = new MailingAddress(street, city, state, zipcode);
		return mAddress;
	}
	
	private ResultCode checkIfSignUpInfoIsValid() {
		var credentialsAreValid = this.checkifCredentialsInfoIsValid().equals(ResultCode.IsValid);
		var personalInfoIsValid = this.checkIfPersonalInfoIsValid().equals(ResultCode.IsValid);
		var mailingAddressIsValid = this.checkIfMailingAddressInfoIsValid().equals(ResultCode.IsValid);
		
		if (credentialsAreValid && personalInfoIsValid && mailingAddressIsValid) {
			return ResultCode.IsValid;
		} else {
			return ResultCode.IncorrectInput;
		}
	}
	
	private ResultCode checkifCredentialsInfoIsValid() {
		if (this.usernameProperty.getValue() == null || this.usernameProperty.getValue().isBlank()) {
			return ResultCode.IncorrectInput;
		}
		
		if (this.passwordProperty.getValue() == null || this.passwordProperty.getValue().isBlank()) {
			return ResultCode.IncorrectInput;
		}
		
		return ResultCode.IsValid;
	}
	
	private ResultCode checkIfPersonalInfoIsValid() {
		if (this.firstNameProperty.getValue() == null || this.firstNameProperty.getValue().isBlank()) {
			return ResultCode.IncorrectInput;
		}
		
		if (this.lastNameProperty.getValue() == null || this.lastNameProperty.getValue().isBlank()) {
			return ResultCode.IncorrectInput;
		}
		
		if (this.dateOfBirthProperty.getValue() == null) {
			return ResultCode.IncorrectInput;
		}
		
		if (this.phoneNumberProperty.getValue() == null || this.phoneNumberProperty.getValue().isBlank()
				|| this.phoneNumberProperty.getValue().length() < 10) {
			return ResultCode.IncorrectInput;
		}
		
		return ResultCode.IsValid;
	}

	private ResultCode checkIfMailingAddressInfoIsValid() {
		if (this.streetProperty.getValue() == null || this.firstNameProperty.getValue().isBlank()) {
			return ResultCode.IncorrectInput;
		}
		
		if (this.cityProperty.getValue() == null || this.firstNameProperty.getValue().isBlank()) {
			return ResultCode.IncorrectInput;
		}
		
		if (this.stateProperty.getValue() == null) {
			return ResultCode.IncorrectInput;
		}
		
		if (this.zipCodeProperty.getValue() == null || this.zipCodeProperty.getValue().isBlank()
				|| this.zipCodeProperty.getValue().length() < 5) {
			return ResultCode.IncorrectInput;
		}
		
		return ResultCode.IsValid;
	}


	
	public SimpleStringProperty usernameProperty() {
		return usernameProperty;
	}

	public SimpleStringProperty passwordProperty() {
		return passwordProperty;
	}

	public SimpleStringProperty firstNameProperty() {
		return firstNameProperty;
	}

	public SimpleStringProperty lastNameProperty() {
		return lastNameProperty;
	}

	public SimpleObjectProperty<LocalDate> dateOfBirthProperty() {
		return dateOfBirthProperty;
	}

	public SimpleStringProperty phoneNumberProperty() {
		return phoneNumberProperty;
	}

	public SimpleStringProperty streetProperty() {
		return streetProperty;
	}

	public SimpleStringProperty cityProperty() {
		return cityProperty;
	}

	public SimpleObjectProperty<USState> stateProperty() {
		return stateProperty;
	}

	public SimpleStringProperty zipCodeProperty() {
		return zipCodeProperty;
	}

}
