package cliniccaresystem.viewmodel;

import java.time.LocalDate;

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
	private SimpleObjectProperty<LocalDate> dateOfBirthProperty;
	private SimpleStringProperty phoneNumberProperty;
	private SimpleStringProperty streetProperty;
	private SimpleStringProperty cityProperty;
	private SimpleObjectProperty<USState> stateProperty;
	private SimpleStringProperty zipCodeProperty;
	
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
		return null;
	}
	
	
	
	
	private ResultCode checkIfSignUpInfoIsValid() {
		if (this.usernameProperty.getValue() == null || this.usernameProperty.getValue().isBlank()) {
			return ResultCode.IncorrectInput;
		}
		
		if (this.passwordProperty.getValue() == null || this.passwordProperty.getValue().isBlank()) {
			return ResultCode.IncorrectInput;
		}
		
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
		
		if (this.streetProperty.getValue() == null || this.firstNameProperty.getValue().isBlank()) {
			return ResultCode.IncorrectInput;
		}
		
		if (this.cityProperty.getValue() == null || this.firstNameProperty.getValue().isBlank()) {
			return ResultCode.IncorrectInput;
		}
		
		if (this.stateProperty.getValue() == null) {
			return ResultCode.IncorrectInput;
		}
		
		if (this.phoneNumberProperty.getValue() == null || this.phoneNumberProperty.getValue().isBlank()
				|| this.phoneNumberProperty.getValue().length() < 5) {
			return ResultCode.IncorrectInput;
		}
		
		return ResultCode.Success;
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
