package gui.fensterUndAussenentueren;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gui.Main;
import gui.kunde.KundeControl;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;

class FensterUndAussentuerenViewTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}
	
	public void start(Stage primaryStage) {
		new KundeControl(primaryStage);
	}	

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		
	}

	@Test
	void test() throws InterruptedException {
		Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                new JFXPanel(); // Initializes the JavaFx Platform
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        new Main().start(new Stage()); // Create and
                                                       // initialize
                                         
                        assertEquals(2,2);
                    }
                });
            }
        });
        thread.start();// Initialize the thread
        Thread.sleep(10000); // Time to use the app, with out this, the thread
                                // will be killed before you can tell.
        
	}

}
