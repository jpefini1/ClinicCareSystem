package cliniccaresystem.viewmodel;

import java.sql.SQLException;
import java.time.LocalDate;

import cliniccaresystem.model.ResultCode;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class CreateAppointmentViewModel {

	private SimpleObjectProperty<LocalDate> appointmentDateProperty;
	private SimpleObjectProperty<Integer> hourProperty;
	private SimpleObjectProperty<Integer> minuteProperty;
	private SimpleStringProperty reasonForVisitProperty;
	private SimpleBooleanProperty amProperty;
	private SimpleBooleanProperty pmProperty;

	public CreateAppointmentViewModel() {
		this.appointmentDateProperty = new SimpleObjectProperty<LocalDate>();
		this.hourProperty = new SimpleObjectProperty<Integer>();
		this.minuteProperty = new SimpleObjectProperty<Integer>();
		this.reasonForVisitProperty = new SimpleStringProperty();
		this.amProperty = new SimpleBooleanProperty();
		this.pmProperty = new SimpleBooleanProperty();
	}
	
	public ResultCode checkIfAppointmentInfoIsValid() {

		if (this.appointmentDateProperty.getValue() == null) {
			return ResultCode.IncorrectInput;
		}
		
		if (this.hourProperty.getValue() == null) {
			return ResultCode.IncorrectInput;
		}
		
		if (this.minuteProperty.getValue() == null) {
			return ResultCode.IncorrectInput;
		}
		
		if (this.reasonForVisitProperty.getValue() == null || this.reasonForVisitProperty.getValue().isBlank()) {
			return ResultCode.IncorrectInput;
		}
		
		if (!this.pmProperty.getValue() && !this.amProperty.getValue()) {
			return ResultCode.IncorrectInput;
		}
		
		return ResultCode.IsValid;
	}

	public SimpleObjectProperty<LocalDate> appointmentDateProperty() {
		return this.appointmentDateProperty;
	}

	public SimpleObjectProperty<Integer> hourProperty() {
		return this.hourProperty;
	}

	public SimpleObjectProperty<Integer> minuteProperty() {
		return this.minuteProperty;
	}

	public SimpleStringProperty reasonForVisitProperty() {
		return this.reasonForVisitProperty;
	}
	
	public SimpleBooleanProperty amProperty() {
		return this.amProperty;
	}
	
	public SimpleBooleanProperty pmProperty() {
		return this.pmProperty;
	}

}
