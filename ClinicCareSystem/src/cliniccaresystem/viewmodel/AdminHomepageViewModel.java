package cliniccaresystem.viewmodel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cliniccaresystem.datalayer.AppointmentDatabaseClient;
import cliniccaresystem.datalayer.DatabaseClient;
import cliniccaresystem.model.ActiveUser;
import cliniccaresystem.model.Appointment;
import cliniccaresystem.model.Patient;
import cliniccaresystem.model.ResultCode;
import cliniccaresystem.model.TestSummary;
import cliniccaresystem.model.VisitInformation;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AdminHomepageViewModel {
	
	private SimpleObjectProperty<LocalDate> startDateProperty;
	private SimpleObjectProperty<LocalDate> endDateProperty;
	private SimpleStringProperty searchQueryProperty;
	private SimpleStringProperty adminInfoProperty;
	
	private final ListProperty<ObservableList<String>> queryResultListProperty;
	private List<ObservableList<String>> queryResultList;
	
	private final ListProperty<VisitInformation> dateSearchResultListProperty;
	private List<VisitInformation> dateSearchResultList;
	
	private final ListProperty<TestSummary> testListProperty;
	private List<TestSummary> testList;
	
	public AdminHomepageViewModel() {
		this.startDateProperty = new SimpleObjectProperty<LocalDate>();
		this.endDateProperty = new SimpleObjectProperty<LocalDate>();
		this.searchQueryProperty = new SimpleStringProperty();
		
		this.adminInfoProperty = new SimpleStringProperty();
		this.adminInfoProperty.setValue(ActiveUser.getActiveUser().toString());
		
		this.queryResultList = new ArrayList<ObservableList<String>>();
		this.queryResultListProperty = new SimpleListProperty<ObservableList<String>>(FXCollections.observableArrayList(this.queryResultList));
		this.queryResultListProperty.set(FXCollections.observableArrayList(this.queryResultList));
		
		this.dateSearchResultList = new ArrayList<VisitInformation>();
		this.dateSearchResultListProperty = new SimpleListProperty<VisitInformation>(FXCollections.observableArrayList(this.dateSearchResultList));
		this.dateSearchResultListProperty.set(FXCollections.observableArrayList(this.dateSearchResultList));
		
		this.testList = new ArrayList<TestSummary>();
		this.testListProperty = new SimpleListProperty<TestSummary>(FXCollections.observableArrayList(this.testList));
		this.testListProperty.set(FXCollections.observableArrayList(this.testList));
	}

	public SimpleObjectProperty<LocalDate> startDateProperty() {
		return this.startDateProperty;
	}

	public SimpleObjectProperty<LocalDate> endDateProperty() {
		return this.endDateProperty;
	}

	public SimpleStringProperty searchQueryProperty() {
		return this.searchQueryProperty;
	}

	public SimpleStringProperty adminInfoProperty() {
		return this.adminInfoProperty;
	}
	
	public ObservableList<ObservableList<String>> queryResultListProperty(){
		return this.queryResultListProperty;
	}

	public HashMap<String, ArrayList<String>> sendSearchQuery() {
		try {
			return DatabaseClient.sendSearchQuery(this.searchQueryProperty.getValue());
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<ObservableList<String>> addDataToTable(HashMap<String, ArrayList<String>> map, String firstColumnName) {
		for (int i = 0; i < map.get(firstColumnName).size(); i++) {
			ObservableList<String> row = FXCollections.observableArrayList();
			
			for (String columnName : map.keySet()) {
				if (map.get(columnName).size() <= i || map.get(columnName).get(i) == null) {
					row.add("Unavailable");
				}
				else {
					row.add(map.get(columnName).get(i));
				}
			}
			
			this.queryResultList.add(row);
		}
		
		this.queryResultListProperty.set(FXCollections.observableArrayList(this.queryResultList));
		return this.queryResultList;
	}

	public ResultCode searchDates() {
		
		if (this.endDateProperty.getValue().isBefore(this.startDateProperty.getValue())) {
			LocalDate newStartDate = this.endDateProperty.getValue();
			this.endDateProperty.setValue(this.startDateProperty.getValue());
			this.startDateProperty.setValue(newStartDate);
		}
		
		try {
			var visits = DatabaseClient.getAppointmentInformationBetweenDates(this.startDateProperty.getValue(), this.endDateProperty.getValue());
			this.dateSearchResultList.clear();
			this.dateSearchResultList.addAll(visits);
			this.dateSearchResultListProperty.set(FXCollections.observableArrayList(this.dateSearchResultList));
			return ResultCode.Success;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return ResultCode.ConnectionError;
		}
	}

	public void clearData() {
		this.queryResultList.clear();
		this.queryResultListProperty.set(FXCollections.observableArrayList(this.queryResultList));
	}
	
	public void showTests(VisitInformation visit) {
		this.testList = visit.getTests();
		this.testListProperty.set(FXCollections.observableArrayList(this.testList));
	}

	public ObservableList<VisitInformation> dateSearchResultListProperty() {
		return this.dateSearchResultListProperty;
	}
	
	public ObservableList<TestSummary> testListProperty() {
		return this.testListProperty;
	}

	public void logout() {
		ActiveUser.setActiveUser(null);
	}
}
