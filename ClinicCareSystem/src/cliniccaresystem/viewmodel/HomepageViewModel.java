package cliniccaresystem.viewmodel;

import java.util.List;
import cliniccaresystem.model.ActiveUser;
import cliniccaresystem.model.Patient;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.MultipleSelectionModel;

public class HomepageViewModel {

	private SimpleStringProperty userInfoProperty;
	
	private final ListProperty<Patient> patientListProperty;
	private List<Patient> patientList;
	
	public HomepageViewModel() {
		this.patientList = ActiveUser.getPatients();
		this.patientListProperty = new SimpleListProperty<Patient>(FXCollections.observableArrayList(this.patientList));
		this.patientListProperty.set(FXCollections.observableArrayList(this.patientList));
		this.userInfoProperty = new SimpleStringProperty();
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
