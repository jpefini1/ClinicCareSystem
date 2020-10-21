package cliniccaresystem.viewmodel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import cliniccaresystem.model.ActiveUser;
import cliniccaresystem.model.Patient;
import cliniccaresystem.model.PatientDatabaseClient;
import cliniccaresystem.model.ResultCode;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.MultipleSelectionModel;

public class HomepageViewModel {

	private SimpleStringProperty userInfoProperty;
	private SimpleStringProperty firstNameSearchProperty;
	private SimpleStringProperty lastNameSearchProperty;
	private SimpleObjectProperty<LocalDate> dobSearchProperty;
	
	private final ListProperty<Patient> patientListProperty;
	private List<Patient> patientList;
	
	public HomepageViewModel() {
		this.patientList = ActiveUser.getPatients();
		this.patientListProperty = new SimpleListProperty<Patient>(FXCollections.observableArrayList(this.patientList));
		this.patientListProperty.set(FXCollections.observableArrayList(this.patientList));
		this.userInfoProperty = new SimpleStringProperty();
		this.userInfoProperty.setValue(ActiveUser.getActiveUser().toString());
		this.firstNameSearchProperty = new SimpleStringProperty();
		this.lastNameSearchProperty = new SimpleStringProperty();
		this.dobSearchProperty = new SimpleObjectProperty<LocalDate>();
	}
	
	public void logout() {
		ActiveUser.setActiveUser(null);
	}
	
	public ResultCode searchPatients() {
		String fName = this.firstNameSearchProperty.getValue();
		String lName = this.lastNameSearchProperty.getValue();
		LocalDate dob = this.dobSearchProperty.getValue();
		
		if (this.isValidSearch()) {
			try {
				this.patientList = PatientDatabaseClient.searchForPatients(fName, lName, dob);
				this.patientListProperty.set(FXCollections.observableArrayList(this.patientList));
				return ResultCode.Success;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return ResultCode.ConnectionError;
		}
		return ResultCode.IncorrectInput;
	}
	
	public void showAllPatients() {
		try {
			this.patientList = PatientDatabaseClient.getAllPatients();
			this.patientListProperty.set(FXCollections.observableArrayList(this.patientList));
			
			this.firstNameSearchProperty.setValue("");
			this.lastNameSearchProperty.setValue("");
			this.dobSearchProperty.setValue(null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private boolean isValidSearch() {
		
		if (this.firstNameSearchProperty.getValue() != null && this.lastNameSearchProperty.getValue() != null) {
			if (!this.firstNameSearchProperty.getValue().isBlank() && !this.lastNameSearchProperty.getValue().isBlank()) {
				return true;
			}
		}
		
		if (this.dobSearchProperty.getValue() != null) {
			return true;
		}
		
		return false;
	}
	
	public SimpleStringProperty userInfoProperty() {
		return userInfoProperty;
	}

	public ListProperty<Patient> patientListProperty() {
		return patientListProperty;
	}

	public SimpleStringProperty firstNameSearchProperty() {
		return firstNameSearchProperty;
	}

	public SimpleStringProperty lastNameSearchProperty() {
		return lastNameSearchProperty;
	}

	public SimpleObjectProperty<LocalDate> dobSearchProperty() {
		return dobSearchProperty;
	}
}
