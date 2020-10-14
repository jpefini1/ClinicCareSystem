package cliniccaresystem.viewmodel;

import java.util.ArrayList;
import java.util.List;

import cliniccaresystem.model.ActiveUser;
import cliniccaresystem.model.Patient;
import cliniccaresystem.model.ResultCode;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

public class HomepageViewModel {

	private SimpleStringProperty userInfoProperty;
	
	private final ListProperty<Patient> patientListProperty;
	private List<Patient> patientList;
	
	public HomepageViewModel() {
		this.patientList = new ArrayList<Patient>();
		this.patientListProperty = new SimpleListProperty<Patient>(FXCollections.observableArrayList(this.patientList));
		this.patientListProperty.set(FXCollections.observableArrayList(this.patientList));
		this.userInfoProperty = new SimpleStringProperty();
		
		System.out.println(ActiveUser.getActiveUser().toString());
		this.userInfoProperty.setValue(ActiveUser.getActiveUser().toString());
	}
	
	public void logout() {
		ActiveUser.setActiveUser(null);
	}
	
	public SimpleStringProperty userInfoProperty() {
		return userInfoProperty;
	}

	public ListProperty<Patient> patientListProperty() {
		return patientListProperty;
	}

}
