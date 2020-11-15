package cliniccaresystem.view;

import cliniccaresystem.model.ResultCode;
import cliniccaresystem.model.Test;
import cliniccaresystem.viewmodel.CreateAppointmentViewModel;
import cliniccaresystem.viewmodel.InputTestResultsViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class InputTestResultsCodeBehind {

    @FXML
    private Label nurseInfoLabel;

    @FXML
    private Label testTypeLabel;

    @FXML
    private DatePicker datePerformedDatePicker;

    @FXML
    private ComboBox<Integer> hourComboBox;

    @FXML
    private ComboBox<String> minuteComboBox;

    @FXML
    private RadioButton amRadioButton;

    @FXML
    private ToggleGroup periodGroup;

    @FXML
    private RadioButton pmRadioButton;

    @FXML
    private TextArea resultsTextArea;

    @FXML
    private CheckBox isAbnormalCheckBox;

    @FXML
    private Button inputResultsButton;

    @FXML
    private Button cancelButton;
    
    InputTestResultsViewModel viewmodel;

    public InputTestResultsCodeBehind() {
   		this.viewmodel = new InputTestResultsViewModel();
   	}

   	@FXML 
   	void initialize() { 
   		this.initializeTimeComboBoxes();
   		this.bindToViewModel(); 
   		this.setupChangeListeners();
   	}

	private void bindToViewModel() {
   		this.resultsTextArea.textProperty().bindBidirectional(this.viewmodel.resultsProperty());
   		this.datePerformedDatePicker.valueProperty().bindBidirectional(this.viewmodel.testPerformedDateProperty());  		
   		this.hourComboBox.valueProperty().bindBidirectional(this.viewmodel.hourProperty());
   		this.minuteComboBox.valueProperty().bindBidirectional(this.viewmodel.minuteProperty());
   		this.amRadioButton.selectedProperty().bindBidirectional(this.viewmodel.amProperty());
   		this.pmRadioButton.selectedProperty().bindBidirectional(this.viewmodel.pmProperty());
   		this.nurseInfoLabel.textProperty().bindBidirectional(this.viewmodel.nurseInfoProperty());
   		this.testTypeLabel.textProperty().bindBidirectional(this.viewmodel.testTypeProperty());
   		this.isAbnormalCheckBox.selectedProperty().bindBidirectional(this.viewmodel.isAbnormalProperty());
   	}
	
	private void initializeTimeComboBoxes() {
		this.hourComboBox.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
   		this.minuteComboBox.getItems().addAll("00", "15", "30", "45");
	}
	
	private void setupChangeListeners() {
		this.resultsTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				if (this.viewmodel.areTestResultsValid()) {
					this.inputResultsButton.setDisable(false);
				} else {
					this.inputResultsButton.setDisable(true);
				}
			}
		});
		
		this.datePerformedDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				if (this.viewmodel.areTestResultsValid()) {
					this.inputResultsButton.setDisable(false);
				} else {
					this.inputResultsButton.setDisable(true);
				}
			}
		});
		
		this.hourComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				if (this.viewmodel.areTestResultsValid()) {
					this.inputResultsButton.setDisable(false);
				} else {
					this.inputResultsButton.setDisable(true);
				}
			}
		});
		
		this.minuteComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				if (this.viewmodel.areTestResultsValid()) {
					this.inputResultsButton.setDisable(false);
				} else {
					this.inputResultsButton.setDisable(true);
				}
			}
		});
		
		this.amRadioButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				if (this.viewmodel.areTestResultsValid()) {
					this.inputResultsButton.setDisable(false);
				} else {
					this.inputResultsButton.setDisable(true);
				}
				
				if (newValue) {
					this.pmRadioButton.setSelected(false);
				}
			}
		});
		
		this.pmRadioButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				if (this.viewmodel.areTestResultsValid()) {
					this.inputResultsButton.setDisable(false);
				} else {
					this.inputResultsButton.setDisable(true);
				}
			}
			
			if (newValue) {
				this.amRadioButton.setSelected(false);
			}
		});
			
		this.isAbnormalCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				if (this.viewmodel.areTestResultsValid()) {
					this.inputResultsButton.setDisable(false);
				} else {
					this.inputResultsButton.setDisable(true);
				}
			}
		});
	}
	
	
	
	@FXML
    void onCancel(ActionEvent event) {
		this.closeStage();
    }
	
	private void closeStage() {
		Stage stage = (Stage) this.cancelButton.getScene().getWindow();
	    stage.close();
	}

    @FXML
    void onInputResults(ActionEvent event) {
    	var result = this.viewmodel.inputResults();
    	
    	if (result.equals(ResultCode.Success)) {
    		this.closeStage();
    	}
    }
    
    public void setSelectedTest(Test test) {
    	this.viewmodel.setSelectedTest(test);
    }

}

