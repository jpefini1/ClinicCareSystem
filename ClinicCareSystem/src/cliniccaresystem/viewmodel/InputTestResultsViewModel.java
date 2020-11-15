package cliniccaresystem.viewmodel;

import java.time.LocalDate;
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
	
	
}
