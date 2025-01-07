package pageFiles.homeSubPages;

import application.Timer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import pageFiles.Page;

/**
 * This is the class that sets up the page for user meditation
 */
public class MediationPage extends Page{

	private ChoiceBox<Integer> minutes1 = new ChoiceBox<Integer>();
	private ChoiceBox<Integer> minutes2 = new ChoiceBox<Integer>();
	private ChoiceBox<Integer> seconds1 = new ChoiceBox<Integer>();
	private ChoiceBox<Integer> seconds2 = new ChoiceBox<Integer>();
	private Label colon = new Label(":");
	
	private Label setTime = new Label("Let's Mediate Together");
	private Button startButton = new Button("Begin/Stop Timer");
	private Button restartButton = new Button("Restart");
	
	private VBox holder = new VBox(13);
	private HBox timerHolder = new HBox(5);
	
	private Timer timer = new Timer();
	private Label timeDisplay = new Label("00:00");
	
	public MediationPage() {
		this.setUpPage();
	}
	
	/**
	 * Sets up the general layout of page
	 */
	private void setUpPage() {
		setUpChoiceBox();
		styleNodes();
		timerHolder.getChildren().addAll(minutes1, minutes2, colon, seconds1, seconds2);
		holder.getChildren().addAll(setTime, timerHolder, timeDisplay, startButton, restartButton);
		this.root = new StackPane(holder);
	}
	
	/**
	 * sets up the styles of the nodes 
	 */
	private void styleNodes(){
		styleNode(setTime, 300, 30, 20);
		setTime.setTextFill(new Color(0, 0.7, 1, 1));
		holder.setAlignment(Pos.CENTER);
		timerHolder.setAlignment(Pos.CENTER);
		styleNode(timeDisplay, 200, 30, 18);
		styleNode(restartButton, 150, 30, 15);
		styleNode(startButton, 150, 30, 15);
		
		restartButton.setOnAction(event -> resetTimer());
		startButton.setOnAction(event -> beginTime());
	}
	
	/**
	 * Sets up the choice boxes to have the digits 0-9
	 */
	private void setUpChoiceBox() {
		setUpChoiceHelper2(minutes1);
		setUpChoiceHelper(minutes2);
		setUpChoiceHelper2(seconds1);
		setUpChoiceHelper(seconds2);
	}
	
	/**
	 * This is the method that sets the timer of the user
	 */
	private void resetTimer() {
		int minutes = returnValue(minutes1) * 10 + returnValue(minutes2);
		int seconds = returnValue(seconds1) * 10 + returnValue(seconds2);
		
		timeDisplay.setText(timer.setTime(minutes, seconds));
		
	}
	
	private void beginTime() {
		try {
			if(!timer.getCountDown()) {
				
				if(timer.getFinish()) {
					resetTimer();
					timer.beginCountDown(timeDisplay);
				}
					timer.setCountDown(true);
				}
			else {
				timer.setCountDown(false);
				}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * This is a helper method that returns sets the timer label to new time
	 * @param c
	 * @return
	 */
	private int returnValue(ChoiceBox c) {
		return ((Integer) c.getValue()).intValue();
	}
	
}
