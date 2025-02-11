package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * This is the timer class that is going to be used for the mediation section.
 * It allows the user to set up a time and start counting down from it. 
 * It is an abstract class; so, don't expect anything to be instantiated from it. 
 */
public class Timer {

	private static int minutes = 0;
	private static int seconds = 0;
	private static boolean countDown = false;
	private static boolean finish = true;
	
	public Timer() {}
	
	/**
	 * This method sets up the time 
	 * @param min
	 * @param sec
	 * @return
	 */
	public static String setTime(int min, int sec){
		minutes = min;
		seconds = sec;
		countDown = false;
		finish = false;
		return min + ":"+String.format("%02d", sec);
	}
	
	/**
	 * THis is the method that initates the countdown of the timer.
	 */
	public static void beginCountDown(Label l) throws InterruptedException{
		if(!getCurrentTime().equals("00:00")) {
			countDown = true;
			finish = false;
			
			countDown(l);
	}}
	
	/**
	 * THis is the method that returns the current time that returns the current time in a correct
	 * format. 
	 * @return
	 */
	public static String getCurrentTime() {
		return minutes+":"+String.format("%02d", seconds);
	}
	
	/**
	 * This is the method that sees if it is counting down or not. 
	 * @return
	 */
	public static boolean getCountDown() {
		return countDown;
	}
	
	/**
	 * This is the method that sees if the timer is finished or not. 
	 * @return
	 */
	public static boolean getFinish() {
		return finish;
	}
	
	/**
	 * This is the method that counts down the timer and delays the method
	 * by 1000 milliseconds (i.e., a second). 
	 * @return
	 * @throws InterruptedException
	 */
	public static void countDown(Label l) throws InterruptedException{
		
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
	        if (countDown) {
	            seconds--;
	        }

	        if (seconds < 0) {
	            seconds = 59;
	            minutes--;
	        }

	        if (minutes == 0 && seconds == 0) {
	            countDown = false;
	            finish = true;
	        }

	        // Update the label on the JavaFX Application Thread
	        Platform.runLater(() -> l.setText(getCurrentTime()));
	    }));

	    timeline.setCycleCount(Timeline.INDEFINITE);
	    timeline.play();
		
		/*
		while(!finish) {
			Thread.sleep(1000);
		
			if(countDown = true) 
				seconds--;
		
			if(seconds < 0) {
				seconds = 59;
				minutes--;
			}
		
			if(minutes == 0 && seconds == 0) {
				countDown = false;
				finish = true;
			}
		
		Platform.runLater(() -> l.setText(getCurrentTime()));
		}*/
	}
	
	public void setCountDown(boolean f) {
		countDown = f;
	}

}
