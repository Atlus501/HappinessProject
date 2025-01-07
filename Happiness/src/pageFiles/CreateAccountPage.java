package pageFiles;

import databases.UserDatabase;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class CreateAccountPage extends Page{

private static VBox entries = new VBox(10);
	
	private Label welcome = new Label("How Would You Name Your Account?");
	private TextField usernameEntry = new TextField();
	private PasswordField passwordEntry1 = new PasswordField();
	private PasswordField passwordEntry2 = new PasswordField();
	
	private HBox userRow = new HBox(10);
	private HBox passRow1 = new HBox(10);
	private HBox passRow2 = new HBox(10);
	
	private Label warning = new Label();
	private Button createAccount = new Button("Create Account");
	
	public CreateAccountPage() {
		this.setUpPage();
	}
	
	private void setUpEntry(){
		this.welcome.setTextFill(new Color(0, 1, 0.4, 1));
		
		styleNode(usernameEntry, 350, 30, 14);
		styleNode(passwordEntry1, 350, 30, 14);
		styleNode(passwordEntry2, 350, 30, 14);
		styleNode(createAccount, 200, 30, 14);
		styleNode(warning, 400, 30, 12);
		styleNode(welcome, 350, 30, 20);
		
		this.logout.setText("Return to the Login Page");
		styleNode(this.logout, 200, 30, 14);
		
		userRow.setAlignment(Pos.CENTER);
		passRow1.setAlignment(Pos.CENTER);
		passRow2.setAlignment(Pos.CENTER);
		
		userRow.getChildren().addAll(styleNode(new Label("Enter Your Username: "), 
				150, 30, 14), usernameEntry);
		passRow1.getChildren().addAll(styleNode(new Label("Enter Your Password: "),
				150, 30, 14), passwordEntry1);
		passRow2.getChildren().addAll(styleNode(new Label("Re-Enter Your Password: "),
				180, 30, 14), passwordEntry2);
		
		entries.setAlignment(Pos.CENTER);
		entries.getChildren().addAll(logout ,welcome, userRow, passRow1, passRow2,
				createAccount, warning);
		
		warning.setTextFill(Color.RED);
		
		createAccount.setOnAction(event -> createNewAccount());
	}
	
	private void createNewAccount() {
		String userNom = usernameEntry.getText().trim();
		String pass1 = passwordEntry1.getText().trim();
		String pass2 = passwordEntry2.getText().trim();
		
		if(userNom.equals("") || pass1.equals("") || pass2.equals("")) {
			warning.setText("Make sure to fill in all fields");
			return;
		}
		
		if(!pass1.equals(pass2)) {
			warning.setText("Make sure both of the passwords are the same");
			return;
		}
		
		if(pass1.contains("=") || userNom.contains("=")) {
			warning.setText("You can't user the character = in your password or username");
			return;
		}
		
		UserDatabase.register(usernameEntry.getText(), pass1);
		switchToHomePage();
	}
	
	public void setUpPage() {
		setUpEntry();
		this.root = new StackPane(entries);
	}
	
	private static void switchToHomePage() {
		PageManager.setPage(2);
	}
}
