package cliniccaresystem.viewmodel;

import java.sql.SQLException;
import java.time.LocalDate;

import cliniccaresystem.model.ActiveUser;
import cliniccaresystem.model.Credentials;
import cliniccaresystem.model.DatabaseClient;
import cliniccaresystem.model.Gender;
import cliniccaresystem.model.MailingAddress;
import cliniccaresystem.model.Nurse;
import cliniccaresystem.model.Patient;
import cliniccaresystem.model.PatientDatabaseClient;
import cliniccaresystem.model.ResultCode;
import cliniccaresystem.model.USState;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class PatientRegistrationViewModel {
	
	private SimpleStringProperty firstNameProperty;
	private SimpleStringProperty lastNameProperty;
	private SimpleObjectProperty<LocalDate> dateOfBirthProperty;
	private SimpleStringProperty phoneNumberProperty;
	private SimpleStringProperty streetProperty;
	private SimpleStringProperty cityProperty;
	private SimpleObjectProperty<USState> stateProperty;
	private SimpleObjectProperty<Gender> genderProperty;
	private SimpleStringProperty zipCodeProperty;
	
	/**
	 * Instantiates a new login gui view model.
	 */
	public PatientRegistrationViewModel() {
		this.firstNameProperty = new SimpleStringProperty();
		this.lastNameProperty = new SimpleStringProperty();
		this.dateOfBirthProperty = new SimpleObjectProperty<LocalDate>();
		this.phoneNumberProperty = new SimpleStringProperty();
		this.streetProperty = new SimpleStringProperty();
		this.cityProperty = new SimpleStringProperty();
		this.stateProperty = new SimpleObjectProperty<USState>();
		this.genderProperty = new SimpleObjectProperty<Gender>();
		this.zipCodeProperty = new SimpleStringProperty();
	}
	
	public ResultCode registerPatient() {
		var isInfoValid = this.checkIfPatientInfoIsValid().equals(ResultCode.IsValid);
		
		if (isInfoValid) {
			try {
				Patient patient = this.createPatient();
				ResultCode result = PatientDatabaseClient.AddPatient(patient);
				
				if (result.equals(ResultCode.Success)) {
					ActiveUser.addPatient(patient);
					result = ResultCode.Success;
				} else {
					result = ResultCode.ConnectionError;
				}
				
				return result;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return ResultCode.ConnectionError;
		} else {
			return ResultCode.IncorrectInput;
		}
	}

	private Patient createPatient() {
		MailingAddress mAddress = this.createMailingAddress();
		
		String firstName = this.firstNameProperty.getValue();
		String lastName = this.lastNameProperty.getValue();
		LocalDate dateOfBirth = this.dateOfBirthProperty.getValue();
		String phoneNumber = this.phoneNumberProperty.getValue();
		Gender gender = this.genderProperty.getValue();
		Patient patient = new Patient(firstName, lastName, dateOfBirth, mAddress, phoneNumber, gender);
		
		return patient;
	}

	private MailingAddress createMailingAddress() {
		String street = this.streetProperty.getValue();
		String city = this.cityProperty.getValue();
		USState state = this.stateProperty.getValue();
		String zipcode = this.zipCodeProperty.getValue();
		MailingAddress mAddress = new MailingAddress(street, city, state, zipcode);
		return mAddress;
	}
	
	private ResultCode checkIfPatientInfoIsValid() {
		var personalInfoIsValid = this.checkIfPersonalInfoIsValid().equals(ResultCode.IsValid);
		var mailingAddressIsValid = this.checkIfMailingAddressInfoIsValid().equals(ResultCode.IsValid);
		
		if (personalInfoIsValid && mailingAddressIsValid) {
			return ResultCode.IsValid;
		} else {
			return ResultCode.IncorrectInput;
		}
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
	
	public SimpleObjectProperty<Gender> genderProperty() {
		return genderProperty;
	}

	public SimpleStringProperty zipCodeProperty() {
		return zipCodeProperty;
	}
}
