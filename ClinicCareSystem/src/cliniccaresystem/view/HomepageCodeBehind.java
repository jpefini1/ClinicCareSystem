package cliniccaresystem.view;

import java.io.IOException;
import java.time.LocalDate;

import cliniccaresystem.Main;
import cliniccaresystem.model.ActiveUser;
import cliniccaresystem.model.Appointment;
import cliniccaresystem.model.Gender;
import cliniccaresystem.model.MailingAddress;
import cliniccaresystem.model.Patient;
import cliniccaresystem.model.ResultCode;
import cliniccaresystem.viewmodel.HomepageViewModel;
import cliniccaresystem.viewmodel.LoginViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class HomepageCodeBehind {

    @FXML
    private TableView<Patient> patientsTableView;
    
    @FXML
    private TableColumn<Patient, String> firstNameColumn;
    
    @FXML
    private TableColumn<Patient, String> lastNameColumn;
    
    @FXML
    private TableColumn<Patient, Gender> genderColumn;
    
    @FXML
    private TableColumn<Patient, LocalDate> dobColumn;
    
    @FXML
    private TableColumn<Patient, MailingAddress> addressColumn;
    
    @FXML
    private TableColumn<Patient, String> phoneColumn;
    
    @FXML
    private TableColumn<Patient, String> idColumn;
    
    @FXML
    private TableView<Appointment> appointmentTableView;

    @FXML
    private TableColumn<Appointment, LocalDate> appointmentDateColumn;

    @FXML
    private TableColumn<Appointment, String> appointmentTimeColumn;

    @FXML
    private TableColumn<Appointment, String> appointmentReasonColumn;

    @FXML
    private Label userInfoLabel;
    
    @FXML
    private Button editPatientButton;
    
    @FXML
    private Button createAppointmentButton;
    
    @FXML
    private Button editAppointmentButton;
    
    @FXML
    private Button searchButton;

    @FXML
    private DatePicker dobDatePicker;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private Button showAllButton;
    
    private HomepageViewModel viewmodel;
    
    public HomepageCodeBehind() {
		this.viewmodel = new HomepageViewModel();
	}

	@FXML 
	void initialize() { 
		this.initializeTableView();
		this.bindToViewModel(); 
	}

	private void bindToViewModel() {
		this.userInfoLabel.textProperty().bindBidirectional(this.viewmodel.userInfoProperty());
		this.patientsTableView.setItems(this.viewmodel.patientListProperty());
		this.firstNameTextField.textProperty().bindBidirectional(this.viewmodel.firstNameSearchProperty());
		this.lastNameTextField.textProperty().bindBidirectional(this.viewmodel.lastNameSearchProperty());
		this.dobDatePicker.valueProperty().bindBidirectional(this.viewmodel.dobSearchProperty());
		
		this.editPatientButton.disableProperty().bind(this.patientsTableView.getSelectionModel().selectedItemProperty().isNull());
		this.createAppointmentButton.disableProperty().bind(this.patientsTableView.getSelectionModel().selectedItemProperty().isNull());
		this.editAppointmentButton.disableProperty().bind(this.appointmentTableView.getSelectionModel().selectedItemProperty().isNull());
	}
	
	private void initializeTableView() {
		this.firstNameColumn.setCellValueFactory(new PropertyValueFactory<Patient, String>("firstName"));
		this.lastNameColumn.setCellValueFactory(new PropertyValueFactory<Patient, String>("lastName"));
		this.genderColumn.setCellValueFactory(new PropertyValueFactory<Patient, Gender>("gender"));
		this.dobColumn.setCellValueFactory(new PropertyValueFactory<Patient, LocalDate>("dateOfBirth"));
		this.addressColumn.setCellValueFactory(new PropertyValueFactory<Patient, MailingAddress>("mailingAddress"));
		this.phoneColumn.setCellValueFactory(new PropertyValueFactory<Patient, String>("phoneNumber"));
		this.idColumn.setCellValueFactory(new PropertyValueFactory<Patient, String>("patientId"));
	}


    @FXML
    void onLogout(ActionEvent event) {
    	this.viewmodel.logout();
    	
    	Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();	
    	try {
			Main.changeScene(currentStage, Main.LOGIN_PAGE_PATH, Main.LOGIN_PAGE_TITLE);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void onRegisterPatient(ActionEvent event) {
    	Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();	
    	try {
			Main.changeScene(currentStage, Main.PATIENT_REGISTRATION_PAGE_PATH, Main.PATIENT_REGISTRATION_PAGE_TITLE);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void onEditPatient(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource(Main.EDIT_PATIENT_PAGE_PATH));
		loader.load();
		
		var codeBehind = (EditPatientCodeBehind) loader.getController();
		codeBehind.setSelectedPatient(this.patientsTableView.getSelectionModel().getSelectedItem());
		
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene newScene = new Scene(loader.getRoot());
		currentStage.setScene(newScene);
		currentStage.setTitle(Main.EDIT_PATIENT_PAGE_TITLE);
    }
    
    @FXML
    void onSearch(ActionEvent event) {
    	var result = this.viewmodel.searchPatients();
    	
    	if (result == ResultCode.Success) {
    		this.showAllButton.setDisable(false);
    	}
    }

    @FXML
    void onShowAll(ActionEvent event) {
    	this.viewmodel.showAllPatients();
    	this.showAllButton.setDisable(true);
    }
    
    @FXML
    void onCreateAppointment(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource(Main.CREATE_APPOINTMENT_PAGE_PATH));
		loader.load();
		
		var codeBehind = (CreateAppointmentCodeBehind) loader.getController();
		codeBehind.setSelectedPatient(this.patientsTableView.getSelectionModel().getSelectedItem());
		
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene newScene = new Scene(loader.getRoot());
		currentStage.setScene(newScene);
		currentStage.setTitle(Main.CREATE_APPOINTMENT_PAGE_TITLE);
    }

    @FXML
    void onEditAppointment(ActionEvent event) {

    }
}
