package cliniccaresystem.view;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import cliniccaresystem.Main;
import cliniccaresystem.model.Appointment;
import cliniccaresystem.model.Gender;
import cliniccaresystem.model.MailingAddress;
import cliniccaresystem.model.Patient;
import cliniccaresystem.model.ResultCode;
import cliniccaresystem.model.Test;
import cliniccaresystem.model.TestResult;
import cliniccaresystem.viewmodel.AppointmentDetailsViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AppointmentDetailsCodeBehind {

    @FXML
    private Label nurseInfoLabel;

    @FXML
    private Label patientInfoLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private Label routineResulsLabel;

    @FXML
    private Label reasonForVisitLabel;

    @FXML
    private Label systolicBPLabel;

    @FXML
    private Label diastolicBPLabel;

    @FXML
    private Label pulseLabel;

    @FXML
    private Label weightLabel;

    @FXML
    private Label tempLabel;

    @FXML
    private TextField systolicBPTextField;

    @FXML
    private TextField diastolicBPTextField;

    @FXML
    private TextField pulseTextField;

    @FXML
    private TextField weightTextField;

    @FXML
    private TextField tempTextField;

    @FXML
    private Button inputResultsButton;

    @FXML
    private Button updateButton;
    
    @FXML
    private TableView<Test> testTableView;

    @FXML
    private TableColumn<Test, String> testNameTableColumn;

    @FXML
    private TableColumn<Test, String> testTimeTableColumn;

    @FXML
    private TableColumn<Test, String> testResultTableColumn;

    @FXML
    private TableColumn<Test, String> isTestAbnormalTableColumn;

    @FXML
    private Label testsOrderedLabel;

    @FXML
    private Button orderTestButton;

    @FXML
    private CheckBox WBCTestCheckBox;

    @FXML
    private Label availableTestsLabel;

    @FXML
    private CheckBox LDLTestCheckBox;

    @FXML
    private CheckBox hepatitisATestCheckBox;

    @FXML
    private CheckBox hepatitisBTestCheckBox;

    @FXML
    private CheckBox hepatitisCTestCheckBox;
    
    @FXML
    private Button inputTestResultsButton;
    
    @FXML
    private Label finalDiagnosisLabel;

    @FXML
    private TextArea finalDiagnosisTextArea;

    @FXML
    private Button makeDiagnosisButton;
    
    @FXML
    private Label doctorLabel;
    
    private AppointmentDetailsViewModel viewmodel;
    
    public AppointmentDetailsCodeBehind() {
   		this.viewmodel = new AppointmentDetailsViewModel();
   	}

   	@FXML 
   	void initialize() { 
   		this.bindToViewModel(); 
   		this.setupChangeListeners();
   		this.initializeTestsTableView();
   	}
   	
   	private void initializeTestsTableView() {
		this.testNameTableColumn.setCellValueFactory(new PropertyValueFactory<Test, String>("name"));
		this.testTimeTableColumn.setCellValueFactory(new PropertyValueFactory<Test, String>("formattedTimePerformed"));
		this.testResultTableColumn.setCellValueFactory(new PropertyValueFactory<Test, String>("testResultsSummary"));
		this.isTestAbnormalTableColumn.setCellValueFactory(new PropertyValueFactory<Test, String>("isAbnormal"));
	}

	private void bindToViewModel() {
   		this.systolicBPTextField.textProperty().bindBidirectional(this.viewmodel.systolicBPProperty());
   		this.diastolicBPTextField.textProperty().bindBidirectional(this.viewmodel.diastolicBPProperty());
   		this.pulseTextField.textProperty().bindBidirectional(this.viewmodel.pulseProperty());
   		this.weightTextField.textProperty().bindBidirectional(this.viewmodel.weightProperty());
   		this.tempTextField.textProperty().bindBidirectional(this.viewmodel.tempProperty());
   		
   		this.reasonForVisitLabel.textProperty().bindBidirectional(this.viewmodel.reasonForVisitProperty());
   		this.timeLabel.textProperty().bindBidirectional(this.viewmodel.timeProperty());
   		this.dateLabel.textProperty().bindBidirectional(this.viewmodel.dateProperty());
   		this.nurseInfoLabel.textProperty().bindBidirectional(this.viewmodel.nurseInfoProperty());
   		this.patientInfoLabel.textProperty().bindBidirectional(this.viewmodel.patientInfoProperty());
   		this.doctorLabel.textProperty().bindBidirectional(this.viewmodel.doctorProperty());
   		this.inputResultsButton.disableProperty().bindBidirectional(this.viewmodel.inputCheckResultsIsDisabledProperty());
   		this.updateButton.visibleProperty().bindBidirectional(this.viewmodel.updateIsVisibleProperty());
   		this.inputResultsButton.visibleProperty().bindBidirectional(this.viewmodel.inputCheckResultsIsVisible());
   		
   		this.testTableView.setItems(this.viewmodel.testListProperty());
   		this.testsOrderedLabel.disableProperty().bindBidirectional(this.viewmodel.testViewIsDisabledProperty());
   		this.testTableView.disableProperty().bindBidirectional(this.viewmodel.testViewIsDisabledProperty());
   		this.availableTestsLabel.disableProperty().bindBidirectional(this.viewmodel.testViewIsDisabledProperty());
   		this.hepatitisATestCheckBox.disableProperty().bindBidirectional(this.viewmodel.HepATestIsDisabledProperty());
   		this.hepatitisBTestCheckBox.disableProperty().bindBidirectional(this.viewmodel.HepBTestIsDisabledProperty());
   		this.hepatitisCTestCheckBox.disableProperty().bindBidirectional(this.viewmodel.HepCTestIsDisabledProperty());
   		this.LDLTestCheckBox.disableProperty().bindBidirectional(this.viewmodel.LDLTestIsDisabledProperty());
   		this.WBCTestCheckBox.disableProperty().bindBidirectional(this.viewmodel.WBCTestIsDisabledProperty());
   		this.orderTestButton.disableProperty().bindBidirectional(this.viewmodel.orderTestsIsDisabledProperty());
   		
   		this.finalDiagnosisTextArea.textProperty().bindBidirectional(this.viewmodel.finalDiagnosisProperty());
   		this.finalDiagnosisLabel.disableProperty().bindBidirectional(this.viewmodel.finalDiagnosisIsDisabledProperty());
   		this.finalDiagnosisTextArea.disableProperty().bindBidirectional(this.viewmodel.finalDiagnosisIsDisabledProperty());
   	}
	
	private void setupChangeListeners() {
		this.systolicBPTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.matches("^[0-9]{1,3}") || newValue.isBlank()) {
				if (this.viewmodel.areRoutineResultsValid().equals(ResultCode.IsValid)) {
					this.updateButton.setDisable(false);
				} else {
					this.updateButton.setDisable(true);
				}
			} else {
				if (oldValue == null) {
					this.systolicBPTextField.setText("");
				} else {
					this.systolicBPTextField.setText(oldValue);
				}
			}
		});
		
		this.diastolicBPTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.matches("^[0-9]{1,3}") || newValue.isBlank()) {
				if (this.viewmodel.areRoutineResultsValid().equals(ResultCode.IsValid)) {
					this.updateButton.setDisable(false);
				} else {
					this.updateButton.setDisable(true);
				}
			} else {
				if (oldValue == null) {
					this.diastolicBPTextField.setText("");
				} else {
					this.diastolicBPTextField.setText(oldValue);
				}
			}
		});
		
		this.pulseTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.matches("^[0-9]{1,3}") || newValue.isBlank()) {
				if (this.viewmodel.areRoutineResultsValid().equals(ResultCode.IsValid)) {
					this.updateButton.setDisable(false);
				} else {
					this.updateButton.setDisable(true);
				}
			} else {
				if (oldValue == null) {
					this.pulseTextField.setText("");
				} else {
					this.pulseTextField.setText(oldValue);
				}
			}
		});
		
		this.weightTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.matches("^[1-9]{1}") || newValue.matches("^[1-9]{1}[0-9]{1,2}") || 
					newValue.matches("^[1-9]{1}[0-9]{1,2}\\.") || newValue.matches("^[1-9]{1}[0-9]{1,2}\\.[0-9]{1,2}") || newValue.isBlank()) {
				if (this.viewmodel.areRoutineResultsValid().equals(ResultCode.IsValid)) {
					this.updateButton.setDisable(false);
				} else {
					this.updateButton.setDisable(true);
				}
			} else {
				if (oldValue == null) {
					this.weightTextField.setText("");
				} else {
					this.weightTextField.setText(oldValue);
				}
			}
		});
		
		this.finalDiagnosisTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.isBlank() || newValue.isEmpty()) {
				this.makeDiagnosisButton.setDisable(true);
			} else {
				this.makeDiagnosisButton.setDisable(this.viewmodel.makeDiagnosisIsDisabledProperty().getValue());
			}
		});
		
		this.finalDiagnosisTextArea.disableProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				this.makeDiagnosisButton.setDisable(true);
			}
		});
		
		this.tempTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.matches("^[1-9]{1}") || newValue.matches("^[1-9]{1}[0-9]{1,2}") || 
					newValue.matches("^[1-9]{1}[0-9]{1,2}\\.") || newValue.matches("^[1-9]{1}[0-9]{1,2}\\.[0-9]{1,2}") || newValue.isBlank()) {
				if (this.viewmodel.areRoutineResultsValid().equals(ResultCode.IsValid)) {
					this.updateButton.setDisable(false);
				} else {
					this.updateButton.setDisable(true);
				}
			} else {
				if (oldValue == null) {
					this.tempTextField.setText("");
				} else {
					this.tempTextField.setText(oldValue);
				}
			}
		});
		
		this.WBCTestCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.booleanValue()) {
				this.viewmodel.selectTest(this.WBCTestCheckBox.getText());
			} else {
				this.viewmodel.deselectTest(this.WBCTestCheckBox.getText());
			}
		});
		
		this.LDLTestCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.booleanValue()) {
				this.viewmodel.selectTest(this.LDLTestCheckBox.getText());
			} else {
				this.viewmodel.deselectTest(this.LDLTestCheckBox.getText());
			}
		});
		
		this.hepatitisATestCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.booleanValue()) {
				this.viewmodel.selectTest(this.hepatitisATestCheckBox.getText());
			} else {
				this.viewmodel.deselectTest(this.hepatitisATestCheckBox.getText());
			}
		});
		
		this.hepatitisBTestCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.booleanValue()) {
				this.viewmodel.selectTest(this.hepatitisBTestCheckBox.getText());
			} else {
				this.viewmodel.deselectTest(this.hepatitisBTestCheckBox.getText());
			}
		});
		
		this.hepatitisCTestCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.booleanValue()) {
				this.viewmodel.selectTest(this.hepatitisCTestCheckBox.getText());
			} else {
				this.viewmodel.deselectTest(this.hepatitisCTestCheckBox.getText());
			}
		});
		
		this.testTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				if (newValue.getTestResults() != null) {
					this.inputTestResultsButton.setDisable(true);
				} else {
					this.inputTestResultsButton.setDisable(false);
				}
			} else {
				this.inputTestResultsButton.setDisable(true);
			}
		});
	}

    @FXML
    void onBack(ActionEvent event) {
    	Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	
    	try {
			Main.changeScene(currentStage, Main.HOMEPAGE_PAGE_PATH, Main.HOMEPAGE_PAGE_TITLE);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void onInputResults(ActionEvent event) {
    	this.setDisableRoutineCheckLabels(false);
    	this.setDisableRoutineCheckTextFields(false);
    	this.inputResultsButton.setDisable(true);
    }
    
    private void setDisableRoutineCheckTextFields(boolean isDisabled) {
    	this.systolicBPTextField.setDisable(isDisabled);
    	this.diastolicBPTextField.setDisable(isDisabled);
    	this.pulseTextField.setDisable(isDisabled);
    	this.weightTextField.setDisable(isDisabled);
    	this.tempTextField.setDisable(isDisabled);
    }
    
    private void setDisableRoutineCheckLabels(boolean isDisabled) {
    	this.routineResulsLabel.setDisable(isDisabled);
    	this.systolicBPLabel.setDisable(isDisabled);
    	this.diastolicBPLabel.setDisable(isDisabled);
    	this.pulseLabel.setDisable(isDisabled);
    	this.weightLabel.setDisable(isDisabled);
    	this.tempLabel.setDisable(isDisabled);
    }

    @FXML
    void onUpdate(ActionEvent event) {
    	var result = this.viewmodel.addRoutineCheckResults();
    	
    	if (result.equals(ResultCode.Success)) {
    		this.setDisableRoutineCheckTextFields(true);
    		this.updateButton.setDisable(true);
    		this.updateButton.setVisible(false);
    		this.inputResultsButton.setVisible(false);
    	}
    }
    
    @FXML
    void onOrderTest(ActionEvent event) {
    	this.viewmodel.orderTests();
    	this.disableTestCheckBoxes();
    }
    
    private void disableTestCheckBoxes() {
		this.hepatitisATestCheckBox.setSelected(false);
		this.hepatitisBTestCheckBox.setSelected(false);
		this.hepatitisCTestCheckBox.setSelected(false);
		this.LDLTestCheckBox.setSelected(false);
		this.WBCTestCheckBox.setSelected(false);
	}

	public void setPatientInfo(Patient patient) {
		this.viewmodel.setPatientInfo(patient);
	}
    
    public void setSelectedAppointment(Appointment appointment) {
		this.viewmodel.initializeAppointmentInfo(appointment);
	}
    
    @FXML
    void onInputTestResults(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource(Main.INPUT_TEST_RESULTS_PAGE_PATH));
			loader.load();
			
			var codeBehind = (InputTestResultsCodeBehind) loader.getController();
			codeBehind.setSelectedTest(this.testTableView.getSelectionModel().getSelectedItem());
			
			Scene newScene = new Scene(loader.getRoot());
			Stage newStage = new Stage();
			newStage.setScene(newScene);
			newStage.setTitle(Main.INPUT_TEST_RESULTS_PAGE_TITLE);
			newStage.initModality(Modality.APPLICATION_MODAL);
			newStage.showAndWait();
			
			this.viewmodel.refreshTestList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @FXML
    void onMakeDiagnosis(ActionEvent event) {
    	this.viewmodel.makeFinalDiagnosis();
    }
}

