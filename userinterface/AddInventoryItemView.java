//Specify package
package userinterface;

import java.sql.SQLException;
import java.util.Observable;
// system imports
import java.util.Properties;
// nothing
//git practice commit amend

import exception.InvalidPrimaryKeyException;
import javafx.event.Event;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.*;
import model.ArticleType;
import model.*;
import model.InventoryItem;
import javafx.scene.Scene;
import javafx.scene.input.*;

// project imports
import impresario.IModel;

/*ADDINVENTORYITEMVIEW CLASS
 * Class contains view for adding InventoryItem Objects
 */
//======================================================================
public class AddInventoryItemView extends View {

    //error message
    private MessageView statusLog;

    //GUI Textfield components
    protected TextField barcodeField;
    protected TextField genderField;
    protected TextField articleField;
    protected TextField colorField;
    protected TextField color2Field;
    protected TextField sizeField;
    protected TextField brandField;
    protected TextField notesField;
    protected TextField fnameField;
    protected TextField lnameField;
    protected TextField phoneField;
    protected TextField emailField;

    protected String articleTypeId;

    protected String color1Id;

    /*CONSTRUCTOR
     * Takes model Object
     */
    public AddInventoryItemView(IModel invenItem){

        super(invenItem, "AddInventoryItemView");

        //Create container for view
        VBox container = new VBox(10);
        container.setAlignment(Pos.CENTER);

		container.setPadding(new Insets(15, 5, 5, 5));

		// create a Node (Text) for showing the title
		container.getChildren().add(createTitle());

		// create a Node (GridPane) for showing data entry fields
		container.getChildren().add(createFormContents());

		// Error message area
		container.getChildren().add(createStatusLog("                          "));

		getChildren().add(container);

        //Subscription will facilitate functionality
        myModel.subscribe("TransactionStatus", this);

	}//END CONSTRUCTOR================================================


    /*createTitle-------------------------------------------------------
     * create Title text
     */
    private Node createTitle() {
		Text titleText = new Text("Inventory Item Info:");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKGREEN);
		
		return titleText;
	}//End createTitle--------------------------------------------------

    /*createFormContents-------------------------------------------------
     * creates actual form for data entry
     * 
     */
    private VBox createFormContents() {
		
        VBox form = new VBox();

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

        //-----Barcode Label and Field-----
        Text barcodeLabel = new Text("Barcode: ");
        barcodeLabel.setWrappingWidth(150);
        barcodeLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(barcodeLabel, 0, 0);

        barcodeField = new TextField();
        barcodeField.setEditable(true);
        grid.add(barcodeField, 1, 0);

        //-----Gender Label and Field-----
        Text genderLabel = new Text("Gender: ");
        genderLabel.setWrappingWidth(150);
        genderLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(genderLabel, 0, 1);
        
        genderField = new TextField();
        genderField.setEditable(false);
        grid.add(genderField, 1, 1);

        //-----Article Label and Field-----
        Text articleLabel = new Text("Article Type: ");
        articleLabel.setWrappingWidth(150);
        articleLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(articleLabel, 0, 2);

        articleField = new TextField();
        articleField.setEditable(false);
        grid.add(articleField, 1, 2);

        //-----Primary Color Label and Field-----
        Text colorLabel = new Text("Primary Color: ");
        colorLabel.setWrappingWidth(150);
        colorLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(colorLabel, 0, 3);

        colorField = new TextField();
        colorField.setEditable(false);
        grid.add(colorField, 1, 3);

        //------Color2 Label and Field-----
        Text color2Label = new Text("Secondary Color: ");
        color2Label.setWrappingWidth(150);
        color2Label.setTextAlignment(TextAlignment.RIGHT);
        grid.add(color2Label, 0, 4);

        color2Field = new TextField();
        color2Field.setEditable(true);
        grid.add(color2Field, 1, 4);

        //-----Size Label and Field----
        Text sizeLabel = new Text("Size: ");
        sizeLabel.setWrappingWidth(150);
        sizeLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(sizeLabel, 0, 5);

        sizeField = new TextField();
        sizeField.setEditable(true);
        grid.add(sizeField, 1, 5);

        //-----Brand Name Label and Field-----
        Text brandLabel = new Text("Brand: ");
        brandLabel.setWrappingWidth(150);
        brandLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(brandLabel, 0, 6);

        brandField = new TextField();
        brandField.setEditable(true);
        grid.add(brandField, 1, 6);

        //-----Notes Label and Field-----
        Text notesLabel = new Text("Notes: ");
        notesLabel.setWrappingWidth(150);
        notesLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(notesLabel, 0, 7);

        notesField = new TextField();
        notesField.setEditable(true);
        grid.add(notesField, 1, 7);

        //-----Donor First Name Label and Field-----
        Text fnameLabel = new Text("Donor First name: ");
        fnameLabel.setWrappingWidth(150);
        fnameLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(fnameLabel, 0, 8);
        
        fnameField = new TextField();
        fnameField.setEditable(true);
        grid.add(fnameField, 1, 8);

        //-----Donor Last Name Label and Field------
        Text lnameLabel = new Text("Donor Last Name: ");
        lnameLabel.setWrappingWidth(150);
        lnameLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(lnameLabel, 0, 9);
        
        lnameField = new TextField();
        lnameField.setEditable(true);
        grid.add(lnameField, 1, 9);

        //-----Donor Phone Number Label and Field------
        Text phoneLabel = new Text("Phone Number: ");
        phoneLabel.setWrappingWidth(150);
        phoneLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(phoneLabel, 0, 10);

        phoneField = new TextField();
        phoneField.setEditable(true);  
        grid.add(phoneField, 1, 10);

        //-----Donor Email Label and Field-----
        Text emailLabel = new Text("Email: ");
        emailLabel.setWrappingWidth(150);
        emailLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(emailLabel, 0, 11);

        emailField = new TextField();
        emailField.setEditable(true);
        grid.add(emailField, 1, 11);

        /** barcodeField upon losing focus:
         * -verify user input
         * -parse gender, article id, and primary color
         * -all other textfields will become editable
         *
         * 4/22/2024
         * - Doesn't parse strings correctly
         */
        barcodeField.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                //String from barcode field
                String barcodeTempString = barcodeField.getText();
                
                /*Try block will check if barcode was numeric. If so:
                 * -Verify input is 8 digits
                 * -Parse input for gender, article, color
                 */
                try {
                    int barcodeInt = Integer.parseInt(barcodeTempString);

                    //Check that input is 8 digits
                    if (barcodeTempString.length() != 8){
                        barcodeField.setText("Please Enter Barcode");
                    } else {
                        //parse barcode for info
                        //char[] barcodeArray = barcodeTempString.toCharArray();

                        //Determine gender from first digit
                        //char genderChar = barcodeArray[0];
                        String genderString = barcodeTempString.substring(0, 1);
                        if (genderString.equals("0")){
                            genderField.setText("M");
                            genderField.setEditable(true);
                        } else
                        if (genderString.equals("1")){
                            genderField.setText("W");
                            genderField.setEditable(true);
                        } else {
                            genderField.setText("Barcode was incorrect");
                            genderField.setEditable(false);
                        };//End gender verification

                        //Determine article type from next two digits
                        try {
                            String articleBarPrefix = barcodeTempString.substring(1, 3);
                            ArticleType article = new ArticleType(articleBarPrefix);
                            articleField.setText(article.getState("barcodePrefix").toString() +
                                    ", (" + article.getState("description").toString() + ")");
                            articleTypeId = article.getState("id").toString();
                            articleField.setEditable(true);
                        } catch (InvalidPrimaryKeyException exc) {
                            articleField.setText("Article was not found. Please enter Article ID");
                        }
                        //End Article Type----------------------------------------

                        //Determine color1 from next two digits
                        try {
                            String colorBarPrefix = barcodeTempString.substring(3, 5);
                            colorBarPrefix.toCharArray();
                            model.Color color = new model.Color(colorBarPrefix);
                            colorField.setText(color.getState("barcodePrefix").toString() +
                                    ", (" + color.getState("description").toString() + ")");
                            color1Id = color.getState("id").toString();
                            colorField.setEditable(true);
                        } catch (InvalidPrimaryKeyException exc) {
                            colorField.setText("Color was not found. Please enter Color ID");
                        }
                        //End Color---------------------------------------------

                    }//End if else for barcode parse
                } catch (NumberFormatException exc){
                    barcodeField.setText("Please Enter Barcode");
                }//End trycatch block                
            }//End changed
        });//End focusProperty

        //====BUTTONS=====
        //Setup separate Hbox for submit and cancel button
        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.BOTTOM_CENTER);

        //-----Submit Button------
        Button subButton = new Button("Submit");
        subButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent e){
                processSubAction(e);
            }
        });
        buttons.getChildren().add(subButton);

        //-----Cancel Button-----
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent e){
                clearErrorMessage();
                myModel.stateChangeRequest("CancelAddInventoryItem", null);
            }
        });
        buttons.getChildren().add(cancelButton);

        //Add form and buttons
        form.getChildren().add(grid);
        form.getChildren().add(buttons);        

        return form;
    
    }//End createFormContents------------------------------------------

    /**==========================================================================
     * processSubAction
     *
     * on submit button press, validate required fields and then pass properties
     * object to constructor via transaction.
     *
     * @param e submit button press event.
     *
     */
    public void processSubAction(Event e){
        //Validate user input
        if (barcodeField.getText().isEmpty()){
            displayMessage("Barcode Field Cannot Be Empty!");
        } else if (sizeField.getText().isEmpty()) {
            displayMessage("Size Field Cannot Be Empty!");
        } else if (genderField.getText().isEmpty()) {
            displayMessage("Gender Field Cannot Be Empty!");
        } else if (articleField.getText().isEmpty()) {
            displayMessage("");
        } else if (colorField.getText().isEmpty()) {
            displayMessage("Color Field Cannot Be Empty!");
        } else {

            //Create properties and keys
            Properties insertProperties = new Properties();
            insertProperties.setProperty("barcode", barcodeField.getText());
            insertProperties.setProperty("gender", genderField.getText());
            insertProperties.setProperty("size", sizeField.getText());
            insertProperties.setProperty("articleTypeId", articleTypeId);
            insertProperties.setProperty("color1Id", color1Id);
            insertProperties.setProperty("color2Id", color2Field.getText());
            insertProperties.setProperty("brand", brandField.getText());
            insertProperties.setProperty("notes", notesField.getText());
            insertProperties.setProperty("donorLastName", lnameField.getText());
            insertProperties.setProperty("donorFirstName", fnameField.getText());
            insertProperties.setProperty("donorPhone", phoneField.getText());
            insertProperties.setProperty("donorEmail", emailField.getText());

            //Try to add inventory item
            try {
                //Tell transaction to DoAddIventoryItem
                myModel.stateChangeRequest("DoAddInventoryItem", insertProperties);

                //Message
                displayMessage("InventoryItem was successfully added!");
            } catch (Exception ex) {
                displayMessage("InventoryItem was not added!");
            }//End try catch block
        }//End if else block

//        //Convert input to strings
//        String barcodeString = barcodeField.getText();
//        String genderString = genderField.getText();
//        String articleString = articleField.getText();
//        String colorString = colorField.getText();
//        String color2String = colorField.getText();
//        String sizeString = sizeField.getText();
//        String brandString = brandField.getText();
//        String notesString = notesField.getText();
//        String fnameString = fnameField.getText();
//        String lnameString = lnameField.getText();
//        String phoneString = phoneField.getText();
//        String emailString = emailField.getText();
    }//End processSubAction-----------------------------------------------------------------

    // Create the status log field
	//-------------------------------------------------------------
	private MessageView createStatusLog(String initialMessage) {
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

    //updateState-----------------------------------------------
    public void updateState(String key, Object value) {
        clearErrorMessage();

        switch (key) {
            case "TransactionStatus":
                displayMessage((String)value);
        }
    }

    //displayMessage()------------------------------------------
    public void displayMessage(String message){
        statusLog.displayMessage(message);
    }

    //clearErrorMessage()----------------------------------------
    public void clearErrorMessage() {
        statusLog.clearErrorMessage();
    }//---------------------------------------------------------
}//END CLASS=============================================================
