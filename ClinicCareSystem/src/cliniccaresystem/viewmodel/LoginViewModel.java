package cliniccaresystem.viewmodel;

import java.sql.SQLException;
import java.time.LocalDate;

import cliniccaresystem.datalayer.CredentialsDatabaseClient;
import cliniccaresystem.datalayer.DatabaseClient;
import cliniccaresystem.datalayer.PatientDatabaseClient;
import cliniccaresystem.model.ActiveUser;
import cliniccaresystem.model.Credentials;
import cliniccaresystem.model.MailingAddress;
import cliniccaresystem.model.Nurse;
import cliniccaresystem.model.ResultCode;
import cliniccaresystem.model.USState;
import cliniccaresystem.model.User;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class LoginViewModel {
	
	private SimpleStringProperty usernameProperty;
	private SimpleStringProperty passwordProperty;
	private SimpleBooleanProperty nurseLoginProperty;
	private SimpleBooleanProperty adminLoginProperty;
	
	public LoginViewModel() {
		this.usernameProperty = new SimpleStringProperty();
		this.passwordProperty = new SimpleStringProperty();
		this.nurseLoginProperty = new SimpleBooleanProperty();
		this.adminLoginProperty = new SimpleBooleanProperty();
	}
	
	public SimpleStringProperty usernameProperty() {
		return this.usernameProperty;
	}

	public SimpleStringProperty passwordProperty() {
		return this.passwordProperty;
	}
	
	public SimpleBooleanProperty nurseLoginProperty() {
		return this.nurseLoginProperty;
	}
	
	public SimpleBooleanProperty adminLoginProperty() {
		return this.adminLoginProperty;
	}
	
	public ResultCode Login() {
		var isInfoValid = this.checkifInfoIsValid().equals(ResultCode.IsValid);
		User activeUser = null;
		
		try {
			if (isInfoValid) {
				if (this.nurseLoginProperty.getValue()) {
					activeUser = CredentialsDatabaseClient.validateNurseLogin(this.usernameProperty.getValue(), this.passwordProperty.getValue());
				} else {
					activeUser = CredentialsDatabaseClient.validateAdminLogin(this.usernameProperty.getValue(), this.passwordProperty.getValue());
				}
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
