package cliniccaresystem.viewmodel;

import java.sql.SQLException;
import java.time.LocalDate;

import cliniccaresystem.model.ActiveUser;
import cliniccaresystem.model.Credentials;
import cliniccaresystem.model.CredentialsDatabaseClient;
import cliniccaresystem.model.DatabaseClient;
import cliniccaresystem.model.MailingAddress;
import cliniccaresystem.model.Nurse;
import cliniccaresystem.model.PatientDatabaseClient;
import cliniccaresystem.model.ResultCode;
import cliniccaresystem.model.USState;
import cliniccaresystem.model.User;
import javafx.beans.property.SimpleStringProperty;

public class LoginViewModel {
	
	private SimpleStringProperty usernameProperty;
	private SimpleStringProperty passwordProperty;
	
	public LoginViewModel() {
		this.usernameProperty = new SimpleStringProperty();
		this.passwordProperty = new SimpleStringProperty();
	}
	
	public SimpleStringProperty usernameProperty() {
		return usernameProperty;
	}

	public SimpleStringProperty passwordProperty() {
		return passwordProperty;
	}
	
	public ResultCode Login() {
		var isInfoValid = this.checkifInfoIsValid().equals(ResultCode.IsValid);
		User activeUser = null;
		
		try {
			if (isInfoValid) {
				activeUser = CredentialsDatabaseClient.ValidateCredentials(this.usernameProperty.getValue(), this.passwordProperty.getValue());
			}
		
			if (activeUser != null) {
				ActiveUser.setActiveUser(activeUser);
				ActiveUser.setPatients(PatientDatabaseClient.getAllPatients());
				return ResultCode.Success;
			} else {
				return ResultCode.InvalidLogin;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ResultCode.ConnectionError;
	}
	
	private ResultCode checkifInfoIsValid() {
		if (this.usernameProperty.getValue() == null || this.usernameProperty.getValue().isBlank()) {
			return ResultCode.IncorrectInput;
		}
		
		if (this.passwordProperty.getValue() == null || this.passwordProperty.getValue().isBlank()) {
			return ResultCode.IncorrectInput;
		}
		
		return ResultCode.IsValid;
	}
}
