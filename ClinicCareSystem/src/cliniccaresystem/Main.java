package cliniccaresystem;
	
import java.io.IOException;

import cliniccaresystem.model.DatabaseClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
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
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource(Main.LOGIN_PAGE_PATH));
		loader.load();
		
		Scene scene = new Scene(loader.getRoot());
		primaryStage.setTitle(Main.LOGIN_PAGE_TITLE);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		//DatabaseClient.dropTables();
		//DatabaseClient.createTables();
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
