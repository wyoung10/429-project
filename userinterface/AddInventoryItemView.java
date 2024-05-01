//Specify package
package userinterface;

import java.sql.SQLException;
import java.util.Observable;
// system imports
import java.util.Properties;
import java.time.*;

//git practice commit amend
import java.time.format.DateTimeFormatter;

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
    protected TextField articleDescField;
    protected TextField colorDescField;
    protected TextField color2DesField;

    protected String articleTypeId;

    protected String color1Id;

    protected String color2Id = "";

    protected int sizeNumericTest;

    private boolean errorFlag = false;

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

        // Error message area
		container.getChildren().add(createStatusLog("                          "));

		// create a Node (GridPane) for showing data entry fields
		container.getChildren().add(createFormContents());		

		getChildren().add(container);

        //Subscription will facilitate functionality
        myModel.subscribe("TransactionStatus", this);

	}//END CONSTRUCTOR================================================


    /*createTitle-------------------------------------------------------
     * create Title text
     */
    private Node createTitle() {
		Text titleText = new Text("ADD INVENTORY ITEM \n (* = Required)");
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
        Text barcodeLabel = new Text("Barcode *: ");
        barcodeLabel.setWrappingWidth(150);
        barcodeLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(barcodeLabel, 0, 0);

        barcodeField = new TextField();
        barcodeField.setEditable(true);
        grid.add(barcodeField, 1, 0);

        //-----Gender Label and Field-----
        Text genderLabel = new Text("Gender (M/W) *: ");
        genderLabel.setWrappingWidth(150);
        genderLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(genderLabel, 0, 1);
        
        genderField = new TextField();
        genderField.setEditable(false);
        grid.add(genderField, 1, 1);

        //-----Article Label and Field-----
        Text articleLabel = new Text("Article Type (Barcode Prefix) *: ");
        articleLabel.setWrappingWidth(150);
        articleLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(articleLabel, 0, 2);

        articleField = new TextField();
        articleField.setEditable(false);
        grid.add(articleField, 1, 2);

        //-----Article Desc Label and Field-----
        Text articleDescLabel = new Text("Article Type (Description): ");
        articleDescLabel.setWrappingWidth(150);
        articleDescLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(articleDescLabel, 0, 3);

        articleDescField = new TextField();
        articleDescField.setEditable(false);
        grid.add(articleDescField, 1, 3);

        //-----Primary Color Label and Field-----
        Text colorLabel = new Text("Primary Color (Barcode Prefix) *: ");
        colorLabel.setWrappingWidth(150);
        colorLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(colorLabel, 0, 4);

        colorField = new TextField();
        colorField.setEditable(false);
        grid.add(colorField, 1, 4);

        //-----Primary Color Desc Label and Field-----
        Text colorDescLabel = new Text("Primary Color (Description): ");
        colorDescLabel.setWrappingWidth(150);
        colorDescLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(colorDescLabel, 0, 5);
        
        colorDescField = new TextField();
        colorDescField.setEditable(false);
        grid.add(colorDescField, 1, 5);

        //------Color2 Label and Field-----
        Text color2Label = new Text("Secondary Color (Barcode Prefix): ");
        color2Label.setWrappingWidth(150);
        color2Label.setTextAlignment(TextAlignment.RIGHT);
        grid.add(color2Label, 0, 6);

        color2Field = new TextField();
        color2Field.setEditable(true);
        grid.add(color2Field, 1, 6);

        //-----Color2 Desc Label and Field-----
        Text color2DescLabel = new Text("Secondary Color (Desc): ");
        color2DescLabel.setWrappingWidth(150);
        color2DescLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(color2DescLabel, 0, 7);

        color2DesField = new TextField();
        color2DesField.setEditable(false);
        grid.add(color2DesField, 1, 7);

        //-----Size Label and Field----
        Text sizeLabel = new Text("Size *: ");
        sizeLabel.setWrappingWidth(150);
        sizeLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(sizeLabel, 0, 8);

        sizeField = new TextField();
        sizeField.setEditable(true);
        grid.add(sizeField, 1, 8);

        //-----Brand Name Label and Field-----
        Text brandLabel = new Text("Brand: ");
        brandLabel.setWrappingWidth(150);
        brandLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(brandLabel, 0, 9);

        brandField = new TextField();
        brandField.setEditable(true);
        grid.add(brandField, 1, 9);

        //-----Notes Label and Field-----
        Text notesLabel = new Text("Notes: ");
        notesLabel.setWrappingWidth(150);
        notesLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(notesLabel, 0, 10);

        notesField = new TextField();
        notesField.setEditable(true);
        grid.add(notesField, 1, 10);

        //-----Donor First Name Label and Field-----
        Text fnameLabel = new Text("Donor First name: ");
        fnameLabel.setWrappingWidth(150);
        fnameLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(fnameLabel, 0, 11);
        
        fnameField = new TextField();
        fnameField.setEditable(true);
        grid.add(fnameField, 1, 11);

        //-----Donor Last Name Label and Field------
        Text lnameLabel = new Text("Donor Last Name: ");
        lnameLabel.setWrappingWidth(150);
        lnameLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(lnameLabel, 0, 12);
        
        lnameField = new TextField();
        lnameField.setEditable(true);
        grid.add(lnameField, 1, 12);

        //-----Donor Phone Number Label and Field------
        Text phoneLabel = new Text("Phone Number: ");
        phoneLabel.setWrappingWidth(150);
        phoneLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(phoneLabel, 0, 13);

        phoneField = new TextField();
        phoneField.setEditable(true);  
        grid.add(phoneField, 1, 13);

        //-----Donor Email Label and Field-----
        Text emailLabel = new Text("Email: ");
        emailLabel.setWrappingWidth(150);
        emailLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(emailLabel, 0, 14);

        emailField = new TextField();
        emailField.setEditable(true);
        grid.add(emailField, 1, 14);

        /** ---BARCODE LOSS OF FOCUS---
         * barcodeField upon losing focus:
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
                        barcodeField.setText("Barcode is incorrect!");
                    } else {
                        //parse barcode for info
                        //char[] barcodeArray = barcodeTempString.toCharArray();
                        
                        //reset parse fields
                        //genderField.clear();
                        articleField.clear();
                        colorField.clear();

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
                            articleField.setText(article.getState("barcodePrefix").toString());
                            articleDescField.setText(article.getState("description").toString());
                            articleTypeId = article.getState("id").toString();
                            articleField.setEditable(true);
                        } catch (InvalidPrimaryKeyException exc) {
                            articleField.setText("Article was not found!");
                            articleField.setEditable(true);
                        }
                        //End Article Type----------------------------------------

                        //Determine color1 from next two digits
                        try {
                            String colorBarPrefix = barcodeTempString.substring(3, 5);
                            colorBarPrefix.toCharArray();
                            model.Color color = new model.Color(colorBarPrefix);
                            colorField.setText(color.getState("barcodePrefix").toString());
                            colorDescField.setText(color.getState("description").toString());
                            color1Id = color.getState("id").toString();
                            colorField.setEditable(true);
                        } catch (InvalidPrimaryKeyException exc) {
                            colorField.setText("Color was not found!");
                            colorField.setEditable(true);
                        }
                        //End Color---------------------------------------------

                    }//End if else for barcode parse
                } catch (NumberFormatException exc){
                    barcodeField.setText("Please Enter Barcode");
                }//End trycatch block                
            }//End changed
        });//End focusProperty

        /**---ARTICLETYPE LOSS OF FOCUS---
         * Upon article field losing focus,
         * search for article by barcode prefix.
         * 
         * This is intended to run only if user has edited article type after initial barcode parse
         */
        articleField.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                
                if (articleField.getText().length() == 2){

                    String articleBarPrefix = articleField.getText();
                    try {
                        ArticleType article = new ArticleType(articleBarPrefix);
                                articleField.setText(article.getState("barcodePrefix").toString());
                                articleDescField.setText(article.getState("description").toString());
                                articleTypeId = article.getState("id").toString();
                                articleField.setEditable(true);
                    } catch (InvalidPrimaryKeyException exc) {
                        articleField.setText("Article Type could not be found");
                        articleField.setEditable(true);
                        articleTypeId = "";
                    }//End try catch 
                    
                } else {
                    displayErrorMessage("Article Type Barcode Prefix was incorrect!");
                    articleField.setText("Please Reenter");
                    articleField.setEditable(true);
                    articleTypeId = "";
                    articleDescField.clear();
                }//End if article field was changed

            }//End changed

        });//End loss of focus for article type

        /**---COLOR LOSS OF FOCUS---
         * Upon loss of focus on primary color field,
         * Search and create new Color object and 
         */
        colorField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {

                if (colorField.getText().length() == 2){
                    
                    String colorBarPrefix = colorField.getText();
                    try {
                        colorBarPrefix.toCharArray();
                        model.Color color = new model.Color(colorBarPrefix);
                        colorField.setText(color.getState("barcodePrefix").toString());
                        colorDescField.setText(color.getState("description").toString());
                        color1Id = color.getState("id").toString();
                        colorField.setEditable(true);
                    } catch (InvalidPrimaryKeyException exc) {
                        colorField.setText("Color was not found. Please enter Color Barcode Prefix");
                        displayErrorMessage("Color was not found. Please enter Color Barcode Prefix");
                        color1Id = "";
                        colorField.setEditable(true);
                    }//End try catch block
                } else {
                    displayErrorMessage("Primary Color Barcode Prefix was incorrect!");
                    colorField.setText("Please reenter");
                    colorDescField.clear();
                    color1Id = "";
                    colorField.setEditable(true);
                }//End if color field was changed
            }//End color1 changed
        });//End color1 loss of focus

        /**---COLOR2 LOSS OF FOCUS---
         * Upon loss of focus on secondary color field
         * search for color and create object.
         * take id.
         */
        color2Field.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {

                if (color2Field.getText().length() == 2 && !color2Field.getText().isEmpty()){
                    
                    String color2BarPrefix = color2Field.getText();
                    try {
                        color2BarPrefix.toCharArray();
                        model.Color color2 = new model.Color(color2BarPrefix);
                        color2Field.setText(color2.getState("barcodePrefix").toString());
                        color2DesField.setText(color2.getState("description").toString());
                        color2Id = color2.getState("id").toString();
                        color2Field.setEditable(true);
                    } catch (InvalidPrimaryKeyException exc) {
                        color2Field.setText("Color was not found. Please enter Barcode Prefix");
                        color2Id = "";
                    }//End try catch block
                } else if (!color2Field.getText().isEmpty()){
                    displayErrorMessage("Barcode Prefix was incorrect!");
                    color2Field.setText("Please reenter");
                    color2DesField.clear();
                    color2Id = "";
                }//End if color2 field was changed
            }//End color2 changed
        });//End color2 loss of focus


        //====BUTTONS=====
        //Setup separate Hbox for submit and cancel button
        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.BOTTOM_CENTER);

        //-----Submit Button------
        Button subButton = new Button("Submit");
        subButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent e){

                //First check for errors and change flag if errors found
                errorCheck();

                //If no errors found then submit
                if (errorFlag == false){
                    processSubAction(e);
                }
                
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

        //Reset border colors
        barcodeField.setStyle("");
        genderField.setStyle("");
        articleField.setStyle("");
        colorField.setStyle("");
        color2Field.setStyle("");
        sizeField.setStyle("");
        notesField.setStyle("");
        brandField.setStyle("");
        fnameField.setStyle("");
        lnameField.setStyle("");
        phoneField.setStyle("");
        emailField.setStyle("");                      
            
            //create properties object
            Properties insertProperties = new Properties();

            //Create properties and keys
            insertProperties.setProperty("barcode", barcodeField.getText());
            insertProperties.setProperty("gender", genderField.getText());
            insertProperties.setProperty("size", sizeField.getText());
            insertProperties.setProperty("articleTypeId", articleTypeId);
            insertProperties.setProperty("color1Id", color1Id);
            insertProperties.setProperty("color2Id", color2Id);
            insertProperties.setProperty("brand", brandField.getText());
            insertProperties.setProperty("notes", notesField.getText());
            insertProperties.setProperty("donorLastName", lnameField.getText());
            insertProperties.setProperty("donorFirstName", fnameField.getText());
            insertProperties.setProperty("donorPhone", phoneField.getText());
            insertProperties.setProperty("donorEmail", emailField.getText());
            
            //TEST get date
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String currentDateString = currentDate.format(myFormat);
            insertProperties.setProperty("dateDonated", currentDateString);
            


            //Try to add inventory item
            try {
                //Tell transaction to DoAddIventoryItem
                myModel.stateChangeRequest("DoAddInventoryItem", insertProperties);
            } catch (Exception ex) {
                System.err.println(ex);
                displayMessage("Inventory Item was not added!");
            }//End try catch block
        //}//End if else block
       
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

        if (key.equals("TransactionStatus")) {
            String val = (String) value;
            if ((val.startsWith("ERR")) || (val.startsWith("Err"))) {
                displayErrorMessage(val);
            } else {
                displayMessage(val);
            }
        }
    }

    //displayMessage()------------------------------------------
    public void displayMessage(String message){
        statusLog.displayMessage(message);
    }

    public void displayErrorMessage(String message)
    {
        statusLog.displayErrorMessage(message);
    }

    //clearErrorMessage()----------------------------------------
    public void clearErrorMessage() {
        statusLog.clearErrorMessage();
    }//---------------------------------------------------------

    /**=======================================================
     * errorCheck
     * 
     * Method will run through form and check for errors. Will change error flag 
     * which determines if submit button will submit or not. 
     * 
     */
    private void errorCheck(){

        //Reset border colors
        barcodeField.setStyle("");
        genderField.setStyle("");
        articleField.setStyle("");
        colorField.setStyle("");
        color2Field.setStyle("");
        sizeField.setStyle("");
        notesField.setStyle("");
        brandField.setStyle("");
        fnameField.setStyle("");
        lnameField.setStyle("");
        phoneField.setStyle("");
        emailField.setStyle("");

        //Check if size was numeric
        try {
            sizeNumericTest = Integer.parseInt(sizeField.getText());

        } catch (NumberFormatException ec) {
            sizeNumericTest = 9999;
        }

        //reset errorFlag
        errorFlag = false;

        //Validate barcode
        if (barcodeField.getText().isEmpty() || barcodeField.getText().length() != 8){

            displayErrorMessage("Submit Failed! Error in highlighted fields!");
            barcodeField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            errorFlag = true;

        }
        
        //Validate size field length
        if (sizeField.getText().isEmpty()) {

            displayErrorMessage("Submit Failed! Error in highlighted fields!");
            sizeField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            sizeField.setText("Please enter a size");
            errorFlag = true;

        }
        
        //Validate if size is a numeric field
        if (sizeNumericTest == 9999){

            displayErrorMessage("Submit Failed! Error in highlighted fields!");
            sizeField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            sizeField.setText("Size must be numeric");
            errorFlag = true;
        }
        
        //Validate gender field
        if (genderField.getText().isEmpty() || (!genderField.getText().equals("M") && !genderField.getText().equals("W"))) {

            displayErrorMessage("Submit Failed! Error in highlighted fields!");
            genderField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            genderField.setText("Please enter only M or W");
            errorFlag = true;

        }
        
        //Validate article field
        if (articleField.getText().isEmpty() || articleTypeId.isEmpty() || articleTypeId == null) {

            displayErrorMessage("Submit Failed! Error in highlighted fields!");
            articleField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            articleField.setText("Please reenter");
            articleTypeId = "";
            errorFlag = true;

        }
        
        //Validate primary color
        if (colorField.getText().isEmpty() || color1Id.isEmpty() || color1Id == null) {

            displayErrorMessage("Submit Failed! Error in highlighted fields!");
            colorField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            colorField.setText("Please reenter");
            color1Id = "";
            errorFlag = true;

        }
        
        //Validate secondary color
        if ( !color2Field.getText().isEmpty() && color2Id.isEmpty()) {

            displayErrorMessage("Submit Failed! Error in highlighted fields!");
            color2Field.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            color2Field.setText("Please reenter");
            color2Id = "";
            errorFlag = true;
            
        }
        
        //Validate phone field if it was not empty
        if (!phoneField.getText().isEmpty() && phoneField.getText().length() != 12) {

            displayErrorMessage("Submit Failed! Error in highlighted fields!");
            phoneField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            phoneField.setText("Must be in XXX-XXX-XXXX format!");   
            errorFlag = true;   

        }
        
        //Validate email field if it was not empty. Must contain "@"
        if (!emailField.getText().isEmpty() && !emailField.getText().contains("@")){

            displayErrorMessage("Submit Failed! Error in highlighted fields!");
            emailField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            errorFlag = true;

        }
        
        //Validate email field if it was not empty. Must contain "."
        if (!emailField.getText().isEmpty() && !emailField.getText().contains(".")) {

            displayErrorMessage("Submit Failed! Error in highlighted fields!");
            emailField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            errorFlag = true;

        }
    }//End errorCheck-------------------------------------------------------------------

}//END CLASS=============================================================
