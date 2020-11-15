package cliniccaresystem.viewmodel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import cliniccaresystem.datalayer.TestResultDatabaseClient;
import cliniccaresystem.model.ResultCode;
import cliniccaresystem.model.Test;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class InputTestResultsViewModel {

	private SimpleStringProperty resultsProperty;
	private SimpleStringProperty nurseInfoProperty;
	private SimpleStringProperty testTypeProperty;
	private SimpleObjectProperty<LocalDate> testPerformedDateProperty;
	private SimpleObjectProperty<Integer> hourProperty;
	private SimpleStringProperty minuteProperty;
	private SimpleBooleanProperty amProperty;
	private SimpleBooleanProperty pmProperty;
	private SimpleBooleanProperty isAbnormalProperty;
	
	private Test selectedTest = null;
	
	public InputTestResultsViewModel() {
		this.resultsProperty = new SimpleStringProperty();
		this.nurseInfoProperty = new SimpleStringProperty();
		this.testTypeProperty = new SimpleStringProperty();
		this.testPerformedDateProperty = new SimpleObjectProperty<LocalDate>();
		this.hourProperty = new SimpleObjectProperty<Integer>();
		this.minuteProperty = new SimpleStringProperty();
		this.amProperty = new SimpleBooleanProperty();
		this.pmProperty = new SimpleBooleanProperty();
		this.isAbnormalProperty = new SimpleBooleanProperty();
	}
	
	public ResultCode inputResults() {
		this.selectedTest.setTimePerformed(this.makePerformedDateTime());
		this.selectedTest.setResult(this.resultsProperty.getValue());
		this.selectedTest.setAbnormal(this.isAbnormalProperty.getValue().toString());
		
		try {
			return TestResultDatabaseClient.addResultsToTable(this.selectedTest);
		} catch (SQLException e) {
			e.printStackTrace();
			return ResultCode.ConnectionError;
		}
	}
	
	private LocalDateTime makePerformedDateTime() {
		LocalDate testDate = this.testPerformedDateProperty().getValue();
		LocalTime testTime = this.getLocalTimeIn24HourFormat();
		return LocalDateTime.of(testDate, testTime);
	}
	
	private LocalTime getLocalTimeIn24HourFormat() {
		var hour = this.hourProperty.getValue();
		
		if (this.pmProperty.getValue()) {
			hour += 12;
			
			if (hour >= 24) {
				hour = 0;
			}
		} else if (this.amProperty.getValue()) {
			if (hour == 12) {
				hour = 0;
			}
		}
		
		return LocalTime.of(hour, Integer.parseInt(this.minuteProperty.getValue()));
	}
	
	public boolean areTestResultsValid() {
		if (this.resultsProperty.getValue() == null || this.resultsProperty.getValue().isBlank()) {
			return false;
		}
		
		if (this.testPerformedDateProperty.getValue() == null) {
			return false;
		}
		
		if (this.hourProperty.getValue() == null) {
			return false;
		}
		
		if (this.minuteProperty.getValue() == null || this.minuteProperty.getValue().isBlank()) {
			return false;
		}
		
		if (!this.amProperty.getValue() && !this.pmProperty.getValue()) {
			return false;
		}
		
		return true;
	}

	public SimpleStringProperty resultsProperty() {
		return this.resultsProperty;
	}


	public SimpleStringProperty nurseInfoProperty() {
		return this.nurseInfoProperty;
	}


	public SimpleStringProperty testTypeProperty() {
		return this.testTypeProperty;
	}


	public SimpleObjectProperty<LocalDate> testPerformedDateProperty() {
		return this.testPerformedDateProperty;
	}


	public SimpleObjectProperty<Integer> hourProperty() {
		return this.hourProperty;
	}


	public SimpleStringProperty minuteProperty() {
		return this.minuteProperty;
	}


	public SimpleBooleanProperty amProperty() {
		return this.amProperty;
	}


	public SimpleBooleanProperty pmProperty() {
		return this.pmProperty;
	}


	public SimpleBooleanProperty isAbnormalProperty() {
		return this.isAbnormalProperty;
	}

	public void setSelectedTest(Test test) {
		this.selectedTest = test;
		this.testTypeProperty.setValue(test.getName());
	}
}
