package pageFiles;

import databases.Database;
import databases.UserDatabase;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pageFiles.homeSubPages.AnalysisPage;

/**
 * This is the class that manages the pages of the GUI
 */
public class PageManager extends Application{
	
	// Global Settings for page size
	public static final int WINDOW_WIDTH = 1200;
	public static final int WINDOW_HEIGHT = 900;
	private static final String APP_TITLE = "The Happiness Project";
	public static Stage appStage;
	
	//setting up important data values
	public static Database database = new Database();
	public BackgroundImage backgroundImage;
	
	/**
	 * This is the main method that the program enters
	 * @param args
	 */
	public static void main(String[] args)  {
		launch(args);
	}
	
	/**
	 * This is the method that sets up the scene for the GUI
	 */
	public void start(Stage primaryStage) {
		try {
			//this makes sure that the database closes the connection when logged out
			primaryStage.setOnCloseRequest((WindowEvent event) -> database.closeConnection());

			// Store reference to stage
			appStage = primaryStage;
			primaryStage.setTitle(APP_TITLE);
			
			if(UserDatabase.emptyDatabase())
				this.setPage(1);
			else
				this.setPage(0);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This sets up the page that is going to be indicated by the system
	 */
	public static void setPage(int id) {
		//CreateAccountPage.setUpPage();
		//Pane root = CreateAccountPage.getRoot();
		
		Pane root;
		Page page = getPage(id);
		page.setUpExit();
		
		root = page.getRoot();
		changeScene(root);
	}
	
	/**
	 * This is the method that returns the root of the GUi to a specific page
	 * based on the id that the programmer selected
	 * @param id
	 * @return
	 */
	private static Page getPage(int id) {
		Page page = null;
		
		switch(id) {
		case(0):
			return new LoginPage();
		case(1):
			return new CreateAccountPage();
		case(2):
			return new HomePage();
		default:
			return new LoginPage();
	}
	}
	
	/**
	 * This is the method that changes the scene of the GUI
	 * @param root
	 */
	private static void changeScene(Pane root) {
		Scene newScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		appStage.setScene(newScene);
		appStage.show();
	}
}
