package cliniccaresystem;
	
import java.io.IOException;

import cliniccaresystem.datalayer.DatabaseClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class Main extends Application {
	
	public static final String LOGIN_PAGE_PATH = "view/LoginGUI.fxml";
	public static final String LOGIN_PAGE_TITLE = "Login";
	
	public static final String HOMEPAGE_PAGE_PATH = "view/HomepageGUI.fxml";
	public static final String HOMEPAGE_PAGE_TITLE = "Home";
	
	public static final String PATIENT_REGISTRATION_PAGE_PATH = "view/PatientRegistrationGUI.fxml";
	public static final String PATIENT_REGISTRATION_PAGE_TITLE = "Register patient";
	
	public static final String SIGN_UP_PAGE_PATH = "view/SignUpGUI.fxml";
	public static final String SIGN_UP_PAGE_TITLE = "Sign Up";
	
	public static final String EDIT_PATIENT_PAGE_PATH = "view/EditPatientGUI.fxml";
	public static final String EDIT_PATIENT_PAGE_TITLE = "Edit patient";
	
	public static final String CREATE_APPOINTMENT_PAGE_PATH = "view/CreateAppointmentGUI.fxml";
	public static final String CREATE_APPOINTMENT_PAGE_TITLE = "Schedule Appointment";
	
	public static final String EDIT_APPOINTMENT_PAGE_PATH = "view/EditAppointmentGUI.fxml";
	public static final String EDIT_APPOINTMENT_PAGE_TITLE = "Edit Appointment";
	
	public static final String APPOINTMENT_DETAILS_PAGE_PATH = "view/AppointmentDetailsGUI.fxml";
	public static final String APPOINTMENT_DETAILS_PAGE_TITLE = "Appointment Details";
	
	public static final String INPUT_TEST_RESULTS_PAGE_PATH = "view/InputTestResultsGUI.fxml";
	public static final String INPUT_TEST_RESULTS_PAGE_TITLE = "Input Test Results";
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource(Main.LOGIN_PAGE_PATH));
		loader.load();
		
		Scene scene = new Scene(loader.getRoot());
		primaryStage.setTitle(Main.LOGIN_PAGE_TITLE);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		//DatabaseClient.addFinalDiagnosisColumnToAppointment();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Changes the scene, closing the current one.
	 *
	 * @param currentStage the current stage
	 * @param viewSourceLocation the view source location
	 * @param windowTitle the window title
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void changeScene(Stage currentStage, String viewSourceLocation, String windowTitle) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource(viewSourceLocation));
		loader.load();
		Scene newScene = new Scene(loader.getRoot());
		currentStage.setScene(newScene);
		currentStage.setTitle(windowTitle);
	}
}
