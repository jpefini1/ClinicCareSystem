package cliniccaresystem;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	public static final String LOGIN_PAGE_PATH = "view/Login.fxml";
	public static final String LOGIN_PAGE_TITLE = "Login";
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource(Main.LOGIN_PAGE_PATH));
		loader.load();
		
		Scene scene = new Scene(loader.getRoot());
		primaryStage.setTitle(Main.LOGIN_PAGE_TITLE);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
