package pageFiles;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import pageFiles.homeSubPages.AnalysisPage;
import pageFiles.homeSubPages.CalendarPage;
import pageFiles.homeSubPages.JournalingPage;
import pageFiles.homeSubPages.MediationPage;
import pageFiles.homeSubPages.SurveyPage;

public class HomePage extends Page{
	
	private TabPane tabpane = new TabPane();
	
	private VBox page = new VBox();

	private Tab meditationTab = new Tab("Meditation");
	private Tab journalTab = new Tab("Journaling");
	private Tab historyTab = new Tab("History");
	private Tab anaTab = new Tab("Analysis");
	private Tab survTab = new Tab("Survey");
	
//	private Button exit = new Button("Logout");
	
	public HomePage() {
		this.setUpTabs();
		page.getChildren().addAll(logout, tabpane);
		this.root = new StackPane(page);
	}
	
	private void setUpTabs() {
		this.setUpMediTab();
		this.setUpJourTab();
		//this.setUpHisTab();
		this.setUpAnaTab();
		this.setUpSurveyTab();
		tabpane.getTabs().addAll(survTab, meditationTab, journalTab, anaTab);
	}
	
	
	private void setUpAnaTab() {
		anaTab.setClosable(false);
		anaTab.setContent(new AnalysisPage().getRoot());
		
	}
	
	private void setUpMediTab() {
		meditationTab.setClosable(false);
		meditationTab.setContent(new MediationPage().getRoot());
	}
	
	private void setUpJourTab() {
		journalTab.setClosable(false);
		journalTab.setContent(new JournalingPage().getRoot());
	}
	
	private void setUpHisTab() {
		historyTab.setClosable(false);
		historyTab.setContent(new CalendarPage().getRoot());
	}
	
	private void setUpSurveyTab() {
		survTab.setClosable(false);
		survTab.setContent(new SurveyPage().getRoot());
	}
	
}
