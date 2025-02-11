package pageFiles;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import databases.InfoDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * This is the superclass that all pages are based off of
 */
public abstract class Page {
	
	protected Pane root = null;
	protected static Label padding = new Label(" ");
	protected static Date today = Date.valueOf(LocalDate.now());
	
	protected Button logout = new Button("Logout");
	
	/**
	 * This is the method that gets the root of the page
	 * @return
	 */
	protected Pane getRoot() {
		return this.root;
	}
	
	/**
	 * This is the method that fills the dates that 
	 * @param dates
	 */
	protected void fillDates(ChoiceBox<Date> dates) {
		ArrayList<Date> date = InfoDatabase.retrieveDates();
		dates.getItems().addAll(date);
		
		if(!dates.getItems().contains(today))
			dates.getItems().add(today);
		
		dates.setValue(today);
	}
	
	/**
	 * This is the method that sets up the exit method for the logout button
	 */
	public void setUpExit() {
		logout.setOnAction(event -> PageManager.setPage(0));
	}
	
	/**
	 * This is a method that is useful to quickly style nodes in the subclass pages.
	 * Its parameters include the node, length, width, and ftsize that the user desires.
	 */
	protected static Node styleNode(Node e, int len, int wid, int ftSize) {
		
		if(e instanceof Button) {
			((Button) e).setMaxSize(len , wid);
			((Button) e).setFont(new Font("Arial", ftSize));
			((Button) e).setAlignment(Pos.CENTER);
			return e;
		}
		
		if(e instanceof Label) {
			((Label) e).setPrefSize(len, wid);
			((Label) e).setFont(new Font("Arial", ftSize));
			((Label) e).setAlignment(Pos.CENTER);
			return e;
		}
		
		if(e instanceof TextField) {
			((TextField) e).setMaxSize(len , wid);
			((TextField) e).setFont(new Font("Arial", ftSize));
			
			return e;
		}
		
		if(e instanceof ChoiceBox) {
			((ChoiceBox<String>) e).setMaxSize(len , wid);
			return e;
		}
		return null;
	}
	
	
	protected static Node setBackground(Node e, Color c) {
		if(e instanceof Label) {
			((Label) e).setBackground(new Background(new BackgroundFill(c, 
					CornerRadii.EMPTY, Insets.EMPTY)));
			return e;
		}
		
		if(e instanceof Button) {
			((Button) e).setBackground(new Background(new BackgroundFill(c, 
					CornerRadii.EMPTY, Insets.EMPTY)));
			return e;
		}
		
		return null;
	}
	
	protected static void setBorder(Node e) {
		
		BorderStroke borderStroke = new BorderStroke(
	     Color.BLACK, // Border color
	            BorderStrokeStyle.SOLID, // Border style
	            CornerRadii.EMPTY, // Corner radii
	            new BorderWidths(2) // Border width
	        );
	        
	        // Create a Border with the BorderStroke
	        Border border = new Border(borderStroke);
		
		
		if(e instanceof Label) {
	        // Set the border to the label
	        ((Label) e).setBorder(border);
	        return;
		}
		
		if(e instanceof Button) {
			((Button) e).setBorder(border);
	        return;
		}
		
	
	}
	
	protected static void setUpChoiceHelper(ChoiceBox<Integer> choice){
		ObservableList<Integer> items = FXCollections.observableArrayList(0, 1, 2, 3, 4, 5,
				6, 7, 8, 9);
		choice.setItems(items);
		choice.setValue(0);
	}
	
	protected static void setUpChoiceHelper2(ChoiceBox<Integer> choice){
		ObservableList<Integer> items = FXCollections.observableArrayList(0, 1, 2, 3, 4, 5);
		choice.setItems(items);
		choice.setValue(0);
	}
	
	protected static String oneUpper(String str) {
		return str.substring(0,1).toUpperCase() + str.substring(1).toLowerCase();
	}

}
