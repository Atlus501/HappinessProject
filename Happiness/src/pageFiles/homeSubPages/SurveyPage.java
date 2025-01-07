package pageFiles.homeSubPages;

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
import pageFiles.PageManager;

import java.sql.Date;
import java.util.ArrayList;

import databases.InfoDatabase;

/**
 * This is the class that sets up the Survey page.
 */
public class SurveyPage extends Page{
	
	Button complete = new Button("Submit Survey");
	VBox page = new VBox(15);

	private Label text = (Label) styleNode(new Label("Please complete "
			+ "this quick survey"), 400, 35, 18);
	
	private ChoiceBox<Date> dates = new ChoiceBox<Date>();
	
	//containers that hold the nodes
	private HBox question1 = new HBox();
	private HBox question2 = new HBox();
	private HBox question3 = new HBox();
	private HBox question4 = new HBox();
	private HBox question5 = new HBox();
	
	//choiceboxes that contain answer options for each question
	private ChoiceBox<Integer> sleepAnswer = new ChoiceBox<Integer>();
	private ChoiceBox<Integer> socialAnswer = new ChoiceBox<Integer>();
	private ChoiceBox<Integer> exerciseAnswer = new ChoiceBox<Integer>();
	private ChoiceBox<Integer> happinessAnswer = new ChoiceBox<Integer>();
	private ChoiceBox<Integer> mediAnswer = new ChoiceBox<Integer>();
	
	/**
	 * The constructor for the class.
	 */
	public SurveyPage() {
		this.setUpPage();
	}
	
	/**
	 * Method that adds the Nodes into the appropriate Panes and styles Nodes.
	 */
	private void setUpPage() {
		this.styleNodes();
		this.loadDates();
		
		page.setAlignment(Pos.TOP_CENTER); 
		page.getChildren().add(text);
		
		Label clarification = new Label("If there isn't an answer choice that matches your "
				+ "answer, pick the closest choice:");
		
		styleNode(clarification, 600, 20, 14);
		
		page.getChildren().addAll(dates, clarification,
				question1, question2, question3, question4, question5,
				complete);
		this.root = new StackPane(page);
	}
	
	/**
	 * The helper method that loads the dates into the choicebox
	 */
	private void loadDates() {
		dates.setOnAction(event -> loadSurveyData());
		fillDates(dates);
		loadSurveyData();
	}
	
	/**
	 * This is the method that loads the survey data from the database
	 */
	private void loadSurveyData() {
		ArrayList<Integer> answers = InfoDatabase.retrieveData(dates.getValue());
		
		if(answers.size() > 0) {
			happinessAnswer.setValue(answers.get(0));
			sleepAnswer.setValue(answers.get(1));
			exerciseAnswer.setValue(answers.get(2));
			mediAnswer.setValue(answers.get(3));
			socialAnswer.setValue(answers.get(4));
		}
	}
	
	/**
	 * Helper method that styles the Nodes
	 */
	private void styleNodes() {
		styleNode(complete, 150, 50, 15);
		text.setTextFill(new Color(0.3, 0.7, 0, 1));
		text.setAlignment(Pos.BOTTOM_CENTER);
		
		setUpHappinessBox();
		question1.getChildren().addAll(styleNode(new Label("How happy/satisifed do you"
				+ " feel today? "), 290, 20, 16), happinessAnswer);
		question1.setAlignment(Pos.CENTER);
		
		setUpSleepBox();
		question4.getChildren().addAll(styleNode(new Label("How many hours did you sleep? "), 
				240, 20, 16), sleepAnswer);
		question4.setAlignment(Pos.CENTER);
		
		setUpSocialBox();
		question2.getChildren().addAll(styleNode(new Label("How many minutes did you socialize? "),
				280, 20, 16), socialAnswer);
		question2.setAlignment(Pos.CENTER);
		
		setUpExerciseBox();
		question3.getChildren().addAll(styleNode(new Label("How many minutes did you exercise? "),
				280, 20, 16), exerciseAnswer);
		question3.setAlignment(Pos.CENTER);
		
		setUpMediBox();
		question5.getChildren().addAll(styleNode(new Label("How many minutes did you spend "
				+ "reflecting/meditating?"), 420, 20, 16), mediAnswer);
		question5.setAlignment(Pos.CENTER);
		
		complete.setOnAction(event -> switchToHomePage());
	}
	
	/**
	 * Sets up the answer choices for the mediation question
	 */
	private void setUpMediBox() {
		ObservableList<Integer> choices = FXCollections.observableArrayList(0, 15, 
				30, 45, 60);
		mediAnswer.setItems(choices);
	}
	
	/**
	 * Sets up the answer choices for the happiness rating question.
	 */
	private void setUpHappinessBox() {
		ObservableList<Integer> choices = FXCollections.observableArrayList(1
				, 2, 3, 4, 5);
		happinessAnswer.setItems(choices);
	}
	
	/**
	 * sets up the answer choices for the exercise question.
	 */
	private void setUpExerciseBox() {
		ObservableList<Integer> choices = FXCollections.observableArrayList(0
				, 15, 30, 45, 60);
		exerciseAnswer.setItems(choices);
	}
	
	/**
	 * sets up the answer choices for the sleep question
	 */
	private void setUpSleepBox() {
		ObservableList<Integer> choices = FXCollections.observableArrayList(4
				, 5, 6, 7, 8, 9, 10);
		sleepAnswer.setItems(choices);
	}
	
	/**
	 * sets up the answer choices for the socialization question
	 */
	private void setUpSocialBox() {
		ObservableList<Integer> choices = FXCollections.observableArrayList(0, 15
				, 30, 45, 60);
		socialAnswer.setItems(choices);
	}
	
	/**
	 * Method called in order to save the survey answers and switch to the home page.
	 */
	private void switchToHomePage() {
		
		int[] answer = new int[5];
		
		answer[1] = sleepAnswer.getValue();
		answer[4] = socialAnswer.getValue();
		answer[2] = exerciseAnswer.getValue();
		answer[0] = happinessAnswer.getValue();
		answer[3] = mediAnswer.getValue();
		
		InfoDatabase.updateInfo(answer);
		
		
	}
	
}