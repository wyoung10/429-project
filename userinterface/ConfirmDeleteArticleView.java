package userinterface;

// system imports
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Vector;
import java.util.Enumeration;
import java.util.Properties;

// project imports
import impresario.IModel;
import model.Color;
import model.ColorCollection;

//==============================================================================
public class ConfirmDeleteArticleView extends View {
    protected String description;
	protected Button cancelButton;
	protected Button submitButton;

	protected MessageView statusLog;


	//--------------------------------------------------------------------------
	public ConfirmDeleteArticleView(IModel wsc)
	{
		super(wsc, "ConfirmDeleteArticleView");

		populateFields(); //sets description to selected article type

		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setPadding(new Insets(15, 5, 5, 5));

		// create our GUI components, add them to this panel
		container.getChildren().add(createTitle());
		container.getChildren().add(createFormContent());

		// Error message area
		container.getChildren().add(createStatusLog("                                            "));

		getChildren().add(container);

        myModel.subscribe("TransactionStatus", this);
	}

	//--------------------------------------------------------------------------
	protected void populateFields()
	{
		getArticleData();
	}

	//--------------------------------------------------------------------------
	protected void getArticleData() {
		//Transaction getState will call toString method of selected article type
		description = (String)(myModel.getState("string"));
	}

	// Create the title container
	//-------------------------------------------------------------
	private Node createTitle() {
		HBox container = new HBox();
		container.setAlignment(Pos.CENTER);	

		Text titleText = new Text(" Delete Article Type ");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setWrappingWidth(300);
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(javafx.scene.paint.Color.DARKGREEN);
		container.getChildren().add(titleText);
		
		return container;
	}

	// Create the main form content
	//-------------------------------------------------------------
	private VBox createFormContent() {
		VBox vbox = new VBox(10);

		GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
       	grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        Text prompt = new Text("Are you sure you'd like to delete this Article Type?\n" + description);
        prompt.setWrappingWidth(350);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(javafx.scene.paint.Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

		HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);
        Button cancelButton = new Button("Back");
        cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				clearErrorMessage();
				myModel.stateChangeRequest("CancelDeleteArticleType", null); 
			}
		});
		buttons.getChildren().add(cancelButton);

        Button submitButton = new Button("Submit");
        submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				clearErrorMessage();
				//call DeleteARticlTypeTransaction stateChangeRequest
				myModel.stateChangeRequest("ConfirmDeleteArticleType", null);
				statusLog.displayMessage("Article Type was deleted!");
				//myModel.stateChangeRequest("CancelDeleteArticleType", null);
			}
		});
        buttons.getChildren().add(submitButton);
		
		vbox.getChildren().add(grid);
		vbox.getChildren().add(buttons);
	
		return vbox;
	}

	

	//--------------------------------------------------------------------------
	public void updateState(String key, Object value) {
        switch (key) {
            case "TransactionStatus":
                displayMessage((String)value);
		}
	}

	//--------------------------------------------------------------------------
	protected MessageView createStatusLog(String initialMessage)
	{
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

	/**
	 * Display info message
	 */
	//----------------------------------------------------------
	public void displayMessage(String message)
	{
		statusLog.displayMessage(message);
	}

	/**
	 * Clear error message
	 */
	//----------------------------------------------------------
	public void clearErrorMessage()
	{
		statusLog.clearErrorMessage();
	}
	/*
	//--------------------------------------------------------------------------
	public void mouseClicked(MouseEvent click)
	{
		if(click.getClickCount() >= 2)
		{
			processColorSelected();
		}
	}
   */
	
}