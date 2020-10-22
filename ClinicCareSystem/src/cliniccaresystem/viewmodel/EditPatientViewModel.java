package cliniccaresystem.viewmodel;

import java.sql.SQLException;
import java.time.LocalDate;

import cliniccaresystem.model.ActiveUser;
import cliniccaresystem.model.Gender;
import cliniccaresystem.model.MailingAddress;
import cliniccaresystem.model.Patient;
import cliniccaresystem.model.PatientDatabaseClient;
import cliniccaresystem.model.ResultCode;
import cliniccaresystem.model.USState;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class EditPatientViewModel {

	private SimpleStringProperty firstNameProperty;
	private SimpleStringProperty lastNameProperty;
	private SimpleObjectProperty<LocalDate> dateOfBirthProperty;
	private SimpleStringProperty phoneNumberProperty;
	private SimpleStringProperty streetProperty;
	private SimpleStringProperty cityProperty;
	private SimpleObjectProperty<USState> stateProperty;
	private SimpleObjectProperty<Gender> genderProperty;
	private SimpleStringProperty zipCodeProperty;
	private SimpleStringProperty nurseInfoProperty;
	
	private Patient selectedPatient;
	
	public EditPatientViewModel() {
		this.firstNameProperty = new SimpleStringProperty();
		this.lastNameProperty = new SimpleStringProperty();
		this.dateOfBirthProperty = new SimpleObjectProperty<LocalDate>();
		this.phoneNumberProperty = new SimpleStringProperty();
		this.streetProperty = new SimpleStringProperty();
		this.cityProperty = new SimpleStringProperty();
		this.stateProperty = new SimpleObjectProperty<USState>();
		this.genderProperty = new SimpleObjectProperty<Gender>();
		this.zipCodeProperty = new SimpleStringProperty();
		
		this.nurseInfoProperty = new SimpleStringProperty();
		this.nurseInfoProperty.setValue(ActiveUser.getActiveUser().toString());
	}
	
	public ResultCode updatePatient() {
		var isInfoValid = this.checkIfPatientInfoIsValid().equals(ResultCode.IsValid);
		
		if (isInfoValid) {
			try {
				Patient patient = this.createUpdatedPatient();
				var result = PatientDatabaseClient.updatePatient(patient);
				
				if (result.equals(ResultCode.Success)) {
					ActiveUser.getPatients().remove(this.selectedPatient);
					ActiveUser.getPatients().add(patient);
					
					return ResultCode.Success;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return ResultCode.ConnectionError;
		} else {
			return ResultCode.IncorrectInput;
		}
	}
	
	private Patient createUpdatedPatient() {
		MailingAddress mAddress = this.createMailingAddress();
		
		String firstName = this.firstNameProperty.getValue();
		String lastName = this.lastNameProperty.getValue();
		LocalDate dateOfBirth = this.dateOfBirthProperty.getValue();
		String phoneNumber = this.phoneNumberProperty.getValue();
		Gender gender = this.genderProperty.getValue();
		Patient patient = new Patient(firstName, lastName, dateOfBirth, mAddress, phoneNumber, gender);
		
		patient.setPatientId(this.selectedPatient.getPatientId());
		patient.setUserId(this.selectedPatient.getUserId());
		
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

	public ResultCode checkIfPatientInfoIsValid() {
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
		
		if (this.stateProperty.getValue() == null) {
			return ResultCode.IncorrectInput;
		}
		
		if (this.genderProperty.getValue() == null) {
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
	
	public SimpleStringProperty nurseInfoProperty() {
		return nurseInfoProperty;
	}

	public void setSelectedPatient(Patient patient) {
		this.selectedPatient = patient;
		this.populateProperties();
	}

	private void populateProperties() {
		this.firstNameProperty.setValue(this.selectedPatient.getFirstName());
		this.lastNameProperty.setValue(this.selectedPatient.getLastName());
		this.dateOfBirthProperty.setValue(this.selectedPatient.getDateOfBirth());
		this.phoneNumberProperty.setValue(this.selectedPatient.getPhoneNumber());
		this.genderProperty.setValue(this.selectedPatient.getGender());
		
		var address = this.selectedPatient.getMailingAddress();
		this.streetProperty.setValue(address.getStreet());
		this.cityProperty.setValue(address.getCity());
		this.stateProperty.setValue(address.getState());
		this.zipCodeProperty.setValue(address.getZipcode());
	}
}
