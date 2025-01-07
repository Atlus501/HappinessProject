package pageFiles.homeSubPages;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import databases.InfoDatabase;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import pageFiles.Page;

public class JournalingPage extends Page{

	private Label question = new Label("What Would You Like To Write About?");
	
	//private HBox date = new HBox(10);
	private ChoiceBox<Date> dates = new ChoiceBox<Date>();
	
	private TextArea journalEntry = new TextArea();
	private VBox body = new VBox(8);
	
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
	
	private Button save = new Button("Save Entry");
	
	public JournalingPage() {
		this.setUpPage();
	}
	
	/**
	 * sets up the individual parts of the page
	 */
	public void setUpPage() {
		setupDates();
		styleNodes();
		body.getChildren().addAll(padding, question, dates, save, journalEntry);
		this.root = new StackPane(body);
	}
	
	/**
	 * Method that sets up dates in choicebox
	 */
	private void setupDates() {
		
		fillDates(dates);
		
		dates.setOnAction(event -> this.loadEntry());
		save.setOnAction(event -> this.saveEntry());
	}
	
	/**
	 * The method that saves the entry to the database
	 */
	private void saveEntry() {
		InfoDatabase.insertEntry(dates.getValue(), journalEntry.getText());
	}
	
	/**
	 * the method that loads the entry from the database
	 */
	private void loadEntry() {
		String entry = InfoDatabase.getEntry(dates.getValue());
		journalEntry.setText(entry);
		System.out.println("Entry "+entry+" has been loaded");
	}
	
	/**
	 * Helper method that styles nodes
	 */
	private void styleNodes() {
		styleNode(question, 350, 30, 18);
		question.setAlignment(Pos.BOTTOM_CENTER);
		question.setTextFill(new Color(1, 0, 1, 1));
		journalEntry.setMaxWidth(900);
		body.setAlignment(Pos.TOP_CENTER);
		styleNode(save, 150, 20, 15);
		
		journalEntry.setText(InfoDatabase.getEntry(Date.valueOf(LocalDate.now())));
	}
	
}
