package pageFiles;

import databases.InfoDatabase;
import databases.UserDatabase;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * This is the class that sets up the functionality and GUI of the login page.
 */
public class LoginPage extends Page{

	//This is the VBOx that adds in the components for logging in
	private VBox entries = new VBox(10);
	
	//Labels that welcomes user
	private Label welcome = new Label("Welcome! Please Enter");
	
	//Entries for entering username and password
	private TextField userEntry = new TextField();
	private PasswordField passwordEntry = new PasswordField();
	
	//Buttons for page changing 
	private Button login = new Button("Login!");
	private Button CreateAccount = new Button("Create New Account");
	
	//HBoxes to hold widgets
	private HBox loginRow = new HBox();
	private HBox passRow = new HBox();
	
	//Label to show warnings
	private Label warning = new Label();
	
	/**
	 * This is the constructor of the LoginPage object
	 */
	public LoginPage() {
		this.setUpPage();
	}
	
	/**
	 * This is the method that creates the login page and sets up the root pane
	 */	
	public void setUpPage() {
		this.entrySetUp();
		this.root = new StackPane(entries);
	}
	
	/**
	 * Sets up the styles of the entry boxes and other Nodes. It also attaches the appropriate
	 * methods to the Buttons and puts the Nodes in the appropriate Panes.
	 */
	private void entrySetUp() {
		this.userEntrySetUp();
		this.entries.setAlignment(Pos.CENTER);
		this.entries.setPrefWidth(300);
		
		this.loginRow.getChildren().addAll(styleNode(new Label("Username:"), 80, 30, 14), 
				userEntry);
		this.loginRow.setAlignment(Pos.CENTER);
		
		this.passRow.getChildren().addAll(styleNode(new Label("Password:"), 80, 30, 14),
				passwordEntry);
		this.passRow.setAlignment(Pos.CENTER);
		
		styleNode(warning, 400, 30, 12);
		warning.setTextFill(Color.RED);
		
		this.entries.getChildren().addAll(welcome, loginRow, passRow, login, CreateAccount, warning);
		this.login.setOnAction(event -> login());
		
		this.CreateAccount.setOnAction(event -> switchToCreateAccount());
	}
	
	/**
	 * Styles the nodes that will be used
	 */
	private void userEntrySetUp() {
		styleNode(userEntry, 350, 30, 14);
		styleNode(passwordEntry, 350, 30, 14);
		styleNode(login, 200, 30, 14);
		styleNode(CreateAccount, 200, 30, 14);
		styleNode(welcome, 250, 50, 20);
		welcome.setTextFill(new Color(0, 1, 0.5, 1));
	}
	
	/**
	 * The method the judges the appropriate login information of the user
	 */
	private void login() {
		String userNom = userEntry.getText();
		String passWord = passwordEntry.getText();
		
		//for security reasons, '=' won't be allowed
		if(userNom.contains("=") || passWord.contains("=")) {
			warning.setText("Passwords and usernames cannot contain =");
			return;
		}
		
		//checks if the user is able to login
		if(UserDatabase.login(userNom, passWord)) {
				PageManager.setPage(2);
			}
		else
			warning.setText("Invalid Login Information");
		
	}
	
	/**
	 * The method that switches to the create account page
	 */
	private static void switchToCreateAccount() {
		PageManager.setPage(1);
	}
	
}
