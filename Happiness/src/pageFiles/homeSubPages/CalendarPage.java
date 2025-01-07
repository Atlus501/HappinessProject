package pageFiles.homeSubPages;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import pageFiles.Page;

public class CalendarPage extends Page{

	private ChoiceBox<String> months = new ChoiceBox<String>();
	
	private Label question = new Label("Which day would you like to revisit?");
	
	private VBox page = new VBox(10);
	private Label month = new Label("Month: ");
	private Label year = new Label("  Year: ");
	
	//Choiceboxes for ever individual digit of year
	private ChoiceBox<Integer> year1 = new ChoiceBox<Integer>();
	private ChoiceBox<Integer> year2 = new ChoiceBox<Integer>();
	private ChoiceBox<Integer> year3 = new ChoiceBox<Integer>();
	private ChoiceBox<Integer> year4 = new ChoiceBox<Integer>();
	
	private HBox week = new HBox();
	private VBox sunday = new VBox();
	private VBox monday = new VBox();
	private VBox tuesday = new VBox();
	private VBox wednesday = new VBox();
	private VBox thursday = new VBox();
	private VBox friday = new VBox();
	private VBox saturday = new VBox();
	
	private VBox[] weekdays = {sunday, monday, tuesday, wednesday, thursday, friday, saturday};
	
	private HBox timeSetter = new HBox(10);
	private Button changeMonth = new Button("Change Month");
	
	public CalendarPage() {
		this.setUpPage();
	}
	
	private void setUpPage() {
		setUpChoiceBoxes();
		timeSetter.setAlignment(Pos.TOP_CENTER);
		week.setAlignment(Pos.TOP_CENTER);
		timeSetter.getChildren().addAll(month, months, year, year1, year2, year3, year4, 
				padding, changeMonth);
		week.getChildren().addAll(weekdays);
		page.getChildren().addAll(padding, question, timeSetter, week);
		this.root = new StackPane(page);
	}
	
	private void setUpChoiceBoxes() {
		setUpChoiceHelper(year1);
		setUpChoiceHelper(year2);
		setUpChoiceHelper(year3);
		setUpChoiceHelper(year4);
		page.setAlignment(Pos.TOP_CENTER);
		
		styleNode(question, 450, 30, 20);
		styleNode(month, 80, 30, 18);
		styleNode(year, 80, 30, 18);
		styleNode(changeMonth, 150, 30, 14);
		
		ObservableList<String> items = FXCollections.observableArrayList("January", "February",
				"March", "April", "May", "June", "July", "August", "September", "October", 
				"November", "December");
		months.setItems(items);
		
		LocalDate today = LocalDate.now();
		
		months.setValue(oneUpper(today.getMonth().toString()));
		String year = String.format("%s", today.getYear());
		
		year1.setValue(Character.getNumericValue(year.charAt(0)));
		year2.setValue(Character.getNumericValue(year.charAt(1)));
		year3.setValue(Character.getNumericValue(year.charAt(2)));
		year4.setValue(Character.getNumericValue(year.charAt(3)));
		
		setUpCalender();
	}
	
	private void setUpCalender() {
		
		DayOfWeek[] daysOfWeek = DayOfWeek.values();
		int weekday = 0;
		
		for (DayOfWeek day : DayOfWeek.values()) {
				Label thisDay = new Label(oneUpper(day.toString()));
				setBackground(thisDay, Color.SKYBLUE);
				setBorder(thisDay);
				styleNode(thisDay, 90, 30, 15);
				weekdays[weekday%7].getChildren().add(thisDay);
				weekday++;
        }
		
		for(int i = 1; i < 32; i++) {
			try {
				LocalDate date = LocalDate.of(today.getYear(), today.getMonth(), i);
				Button day = new Button(String.format("%s", i));
				styleNode(day, 90, 50, 15);
				setBackground(day, Color.YELLOWGREEN);
				setBorder(day);
				
				if(i == 1) {
					weekday = date.getDayOfWeek().getValue();
					
					if(weekday != 7)
					for(int j = 0; j < weekday; j++) {
						Label empty = new Label();
						styleNode(empty, 90, 80, 0);
						setBackground(empty, Color.GREEN);
						weekdays[j].getChildren().add(empty);
					}
				}
				weekdays[weekday % 7].getChildren().add(day);
				weekday++;
			}
			catch(java.time.DateTimeException e) {
				return;
			}
	}}
}
