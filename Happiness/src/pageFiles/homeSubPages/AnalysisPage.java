package pageFiles.homeSubPages;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import databases.InfoDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import pageFiles.Page;

/**
 * THis is the page that allows the user to analyze the data of their past few weeks
 */
public class AnalysisPage extends Page{

	//the axes for the graph
	private CategoryAxis xAxis = new CategoryAxis();
	private NumberAxis yAxis = new NumberAxis();
	private LineChart<String, Number> graph = new LineChart<>(xAxis, yAxis);
	
	//the pages that hold the nodes
	private VBox page = new VBox();
	private HBox optionRow = new HBox();
	private HBox dateRow = new HBox();
	
	//ChoiceBoxes for the options for data analysis
	private ChoiceBox<String> options = new ChoiceBox<String>();
	private ChoiceBox<String> dateOptions = new ChoiceBox<String>();
	
	/**
	 * THe constructor for the class
	 */
	public AnalysisPage(){
		setUpXAxis();
		setUpYAxis();
		setUpOptionRow();
		setUpDateOptions();
		
		Label clarify = new Label("If the graph looks weird, clicking on it can fix it.");
		styleNode(clarify, 400, 20, 10);
		clarify.setAlignment(Pos.BOTTOM_CENTER);
		
		page.setAlignment(Pos.TOP_CENTER);
		page.getChildren().addAll(optionRow, dateRow, clarify, graph);
		this.root = new StackPane(page);
	}
	
	/**
	 * The method that sets up the nodes to see which metric they'd like to see
	 */
	private void setUpOptionRow() {
		Label question = new Label("Which Metric Would you "
				+ "like to look at? ");
		optionRow.getChildren().addAll(styleNode(question, 340, 40, 18), options);
		optionRow.setAlignment(Pos.BOTTOM_CENTER);
		question.setTextFill(new Color(1, 0.4, 0, 1));
		question.setAlignment(Pos.BOTTOM_CENTER);
		
		ObservableList<String> fields = FXCollections.observableArrayList(
				"Exercise", "Sleep", "Socialization", "Meditation/Reflection", "Happiness Rating");
		options.setItems(fields);
		
		options.setValue("Happiness Rating");
	}
	
	/**
	 * This is the method that sets up the duration of dates that the user can use to analyze
	 */
	private void setUpDateOptions() {
		ObservableList<String> fields = FXCollections.observableArrayList(
				"1 week", "3 weeks", "1 month", "3 months", "5 months");
		dateOptions.setItems(fields);
		
		dateOptions.setOnAction(event ->addData());
		
		Label select = new Label("Select Which Time Period You'd Like to View Your History");
		styleNode(select, 420, 30, 15);
		
		dateRow.getChildren().addAll(select, dateOptions);
		dateRow.setAlignment(Pos.BOTTOM_CENTER);
		
		options.setOnAction(event -> addData());
	}
	
	/**
	 * This is the method that adds the data to the graph
	 */
	private void addData() {
		Series<String, Number> series = new XYChart.Series<String, Number>();
        series.setName("My Data");
		
        String option = processOption(options.getValue());
        
		List<String> dates = InfoDatabase.returnHistory(option, processDate(), series);
		
		xAxis.setCategories(FXCollections.<String>observableArrayList
				   (dates));
		
		if(dates.size() > 0) {
			graph.getData().clear();
			graph.getData().add(series);
	}}
	
	/**
	 * This is the method that processes 
	 * @return
	 */
	private Date processDate() {
		LocalDate today = LocalDate.now();
		
		switch(dateOptions.getValue()) {
		case("1 week"):
			return Date.valueOf(today.minusWeeks(1));
		case("3 weeks"):
			return Date.valueOf(today.minusWeeks(3));
		case("1 month"):
			return Date.valueOf(today.minusMonths(1));
		case("3 months"):
			return Date.valueOf(today.minusMonths(3));
		case("5 months"):
			return Date.valueOf(today.minusMonths(5));
		}
		
		return Date.valueOf(today);
	}
	
	/**
	 * This method process the answers of the choicebox before checking the associated field
	 * @param str
	 * @return
	 */
	private String processOption(String str) {
		switch(str){
		case("Happiness Rating"):
			return "happinessRating";
		case("Exercise"):
			return "exerciseInfo";
		case("Sleep"):
			return "sleepInfo";
		case("Socialization"):
			return "socializeInfo";
		case("Meditation/Reflection"):
			return "meditationInfo";
		}
		
		return "";
	}
	
	/**
	 * This is the method that sets up the X axis
	 */
	private void setUpXAxis(){
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		xAxis.setLabel("Date");
	}
	
	/**
	 * This is the mehtod that sets up the Y axis
	 */
	private void setUpYAxis() {
		yAxis.setLabel("Rating");
	}
	
}
