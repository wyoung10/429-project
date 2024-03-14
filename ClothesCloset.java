
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;

// project imports
import event.Event;

import model.Clerk;
import userinterface.MainStageContainer;
import userinterface.WindowPosition;


/** The class containing the main program  for the Project application */
//==============================================================
public class ClothesCloset extends Application
{

	private Clerk myClerk;		// the main behavior for the application

	/** Main frame of the application */
	private Stage mainStage;


	// start method for this class, the main application object
	//----------------------------------------------------------
	public void start(Stage primaryStage) {
        // Create the top-level container (main frame) and add contents to it.
        MainStageContainer.setStage(primaryStage, "Professional Clothes Closet");
        mainStage = MainStageContainer.getInstance();

        // Finish setting up the stage (ENABLE THE GUI TO BE CLOSED USING THE TOP RIGHT
        // 'X' IN THE WINDOW), and show it.
        mainStage.setOnCloseRequest(new EventHandler <javafx.stage.WindowEvent>() {
            @Override
            public void handle(javafx.stage.WindowEvent event) {
                System.exit(0);
            }
        });

        try {
            myClerk = new Clerk();
        }
        catch(Exception exc) {
            System.err.println("Project.Project - could not create Clerk!");
            new Event(Event.getLeafLevelClassName(this), "Clerk.<init>", "Unable to create Clerk object", Event.ERROR);
            exc.printStackTrace();
        }

  	    WindowPosition.placeCenter(mainStage);

        mainStage.show();
	}


	/** 
	 * The "main" entry point for the application. Carries out actions to
	 * set up the application
	 */
	//----------------------------------------------------------
    public static void main(String[] args) {
		launch(args);
	}
}