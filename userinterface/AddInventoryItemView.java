//Specify package
package userinterface;

import java.sql.SQLException;
import java.util.Observable;
// system imports
import java.util.Properties;
import java.util.Vector;
import java.time.*;

//git practice commit amend
import java.time.format.DateTimeFormatter;

import exception.InvalidPrimaryKeyException;
import javafx.event.Event;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.*;
import model.*;
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
    protected ArticleTypeCollection articleTypes;
    protected ColorCollection colors;
    protected ComboBox<ArticleType> articleType;
    protected ComboBox<Color> color1;
    protected ComboBox<Color> color2;
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

    protected Button cancelButton;
    protected Button submitButton;

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
        String css = getClass().getResource("Styles.css").toExternalForm();
        getStylesheets().add(css);

        getComboBoxData();

        //Create container for view
        VBox container = new VBox(10);
        container.setAlignment(Pos.CENTER);
        container.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.LIGHTYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));

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

    protected void getComboBoxData() {
        try {
            articleTypes = new ArticleTypeCollection();
            articleTypes.getArticleTypes();
            colors = new ColorCollection();
            colors.getColors();
        }
        catch (Exception exc) {
            exc.printStackTrace();
            displayErrorMessage(exc.getMessage());
        }
    }

    /*createTitle-------------------------------------------------------
     * create Title text
     */
    private Node createTitle() {
		Text titleText = new Text("ADD INVENTORY ITEM \n (* = Required)");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(javafx.scene.paint.Color.DARKGREEN);
		
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
        Font myFont = Font.font("Arial", FontWeight.BOLD, 16);
        barcodeLabel.setFont(myFont);
        barcodeLabel.setWrappingWidth(150);
        barcodeLabel.setTextAlignment(TextAlignment.RIGHT);
        barcodeLabel.setFill(javafx.scene.paint.Color.BLACK);
        grid.add(barcodeLabel, 0, 0);

        barcodeField = new TextField();
        barcodeField.setEditable(true);
        grid.add(barcodeField, 1, 0);

        //-----Gender Label and Field-----
        Text genderLabel = new Text("Gender (M/W) *: ");
        genderLabel.setFont(myFont);
        genderLabel.setWrappingWidth(150);
        genderLabel.setTextAlignment(TextAlignment.RIGHT);
        genderLabel.setFill(javafx.scene.paint.Color.BLACK);
        grid.add(genderLabel, 0, 1);
        
        genderField = new TextField();
        genderField.setEditable(false);
        grid.add(genderField, 1, 1);

        //-----Article Label and Field-----
        Text articleLabel = new Text("Article Type *: ");
        articleLabel.setFont(myFont);
        articleLabel.setWrappingWidth(150);
        articleLabel.setTextAlignment(TextAlignment.RIGHT);
        articleLabel.setFill(javafx.scene.paint.Color.BLACK);
        grid.add(articleLabel, 0, 2);

        ObservableList<ArticleType> articleTypeList = FXCollections.observableArrayList(
            (Vector<ArticleType>)articleTypes.getState("ArticleTypes")
        );
        articleType = new ComboBox<ArticleType>();
        articleType.setCellFactory(listView -> makeArticleTypeCell());
        articleType.setButtonCell(makeArticleTypeCell());
        articleType.setItems(articleTypeList);
        articleType.setDisable(true);
        grid.add(articleType, 1, 2);

        //-----Primary Color Label and Field-----
        Text colorLabel = new Text("Primary Color *: ");
        colorLabel.setFont(myFont);
        colorLabel.setWrappingWidth(150);
        colorLabel.setTextAlignment(TextAlignment.RIGHT);
        colorLabel.setFill(javafx.scene.paint.Color.BLACK);
        grid.add(colorLabel, 0, 3);

        
        ObservableList<Color> colorList = FXCollections.observableArrayList(
            (Vector<Color>)colors.getState("Colors")
        );
        color1 = new ComboBox<Color>();
        color1.setCellFactory(listView -> makeColorCell());
        color1.setButtonCell(makeColorCell());
        color1.setItems(colorList);
        color1.setDisable(true);
        grid.add(color1, 1, 3);

        //------Color2 Label and Field-----
        Text color2Label = new Text("Secondary Color: ");
        color2Label.setFont(myFont);
        color2Label.setWrappingWidth(150);
        color2Label.setTextAlignment(TextAlignment.RIGHT);
        color2Label.setFill(javafx.scene.paint.Color.BLACK);
        grid.add(color2Label, 0, 4);

        color2 = new ComboBox<Color>();
        color2.setCellFactory(listView -> makeColorCell());
        color2.setButtonCell(makeColorCell());
        color2.setItems(colorList);
        color2.setDisable(false);
        grid.add(color2, 1, 4);

        Button clearColor2 = new Button("Clear");
        clearColor2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
                color2.setValue(null);
			}
		});
        grid.add(clearColor2, 2, 4);

        //-----Size Label and Field----
        Text sizeLabel = new Text("Size *: ");
        sizeLabel.setFont(myFont);
        sizeLabel.setWrappingWidth(150);
        sizeLabel.setTextAlignment(TextAlignment.RIGHT);
        sizeLabel.setFill(javafx.scene.paint.Color.BLACK);
        grid.add(sizeLabel, 0, 5);

        sizeField = new TextField();
        sizeField.setEditable(true);
        grid.add(sizeField, 1, 5);

        //-----Brand Name Label and Field-----
        Text brandLabel = new Text("Brand: ");
        brandLabel.setFont(myFont);
        brandLabel.setWrappingWidth(150);
        brandLabel.setTextAlignment(TextAlignment.RIGHT);
        brandLabel.setFill(javafx.scene.paint.Color.BLACK);
        grid.add(brandLabel, 0, 6);

        brandField = new TextField();
        brandField.setEditable(true);
        grid.add(brandField, 1, 6);

        //-----Notes Label and Field-----
        Text notesLabel = new Text("Notes: ");
        notesLabel.setFont(myFont);
        notesLabel.setWrappingWidth(150);
        notesLabel.setTextAlignment(TextAlignment.RIGHT);
        notesLabel.setFill(javafx.scene.paint.Color.BLACK);
        grid.add(notesLabel, 0, 7);

        notesField = new TextField();
        notesField.setEditable(true);
        grid.add(notesField, 1, 7);

        //-----Donor First Name Label and Field-----
        Text fnameLabel = new Text("Donor First name: ");
        fnameLabel.setFont(myFont);
        fnameLabel.setWrappingWidth(150);
        fnameLabel.setTextAlignment(TextAlignment.RIGHT);
        fnameLabel.setFill(javafx.scene.paint.Color.BLACK);
        grid.add(fnameLabel, 0, 8);
        
        fnameField = new TextField();
        fnameField.setEditable(true);
        grid.add(fnameField, 1, 8);

        //-----Donor Last Name Label and Field------
        Text lnameLabel = new Text("Donor Last Name: ");
        lnameLabel.setFont(myFont);
        lnameLabel.setWrappingWidth(150);
        lnameLabel.setTextAlignment(TextAlignment.RIGHT);
        lnameLabel.setFill(javafx.scene.paint.Color.BLACK);
        grid.add(lnameLabel, 0, 9);
        
        lnameField = new TextField();
        lnameField.setEditable(true);
        grid.add(lnameField, 1, 9);

        //-----Donor Phone Number Label and Field------
        Text phoneLabel = new Text("Phone Number: ");
        phoneLabel.setFont(myFont);
        phoneLabel.setWrappingWidth(150);
        phoneLabel.setTextAlignment(TextAlignment.RIGHT);
        phoneLabel.setFill(javafx.scene.paint.Color.BLACK);
        grid.add(phoneLabel, 0, 10);

        phoneField = new TextField();
        phoneField.setEditable(true);  
        grid.add(phoneField, 1, 10);

        //-----Donor Email Label and Field-----
        Text emailLabel = new Text("Email: ");
        emailLabel.setFont(myFont);
        emailLabel.setWrappingWidth(150);
        emailLabel.setTextAlignment(TextAlignment.RIGHT);
        emailLabel.setFill(javafx.scene.paint.Color.BLACK);
        grid.add(emailLabel, 0, 11);

        emailField = new TextField();
        emailField.setEditable(true);
        grid.add(emailField, 1, 11);

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
                        // articleType.clear();
                        // color1.clear();

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
                            for (int i = 0; i < articleType.getItems().size(); i++) {
                                ArticleType next = articleType.getItems().get(i);
                                if (next.getState("id").equals(article.getState("id"))) {
                                    articleType.setValue(next);
                                    break;
                                }
                            }
                            articleTypeId = article.getState("id").toString();
                            articleType.setDisable(false);
                        } catch (InvalidPrimaryKeyException exc) {
                            displayErrorMessage("Could not find ArticleType from barcode!");
                            articleType.setDisable(false);
                        }
                        //End Article Type----------------------------------------

                        //Determine color1 from next two digits
                        try {
                            String colorBarPrefix = barcodeTempString.substring(3, 5);
                            colorBarPrefix.toCharArray();
                            Color color = new Color(colorBarPrefix);
                            for (int i = 0; i < color1.getItems().size(); i++) {
                                Color next = color1.getItems().get(i);
                                if (next.getState("id").equals(color.getState("id"))) {
                                    color1.setValue(next);
                                    break;
                                }
                            }
                            color1Id = color.getState("id").toString();
                            color1.setDisable(false);
                        } catch (InvalidPrimaryKeyException exc) {
                            displayErrorMessage("Could not find Color1 from barcode!");
                            color1.setDisable(false);
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
        buttons.setAlignment(Pos.CENTER);
        cancelButton = new Button("Back");
        cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e){
                clearErrorMessage();
                myModel.stateChangeRequest("CancelAddInventoryItem", null);
            }
        });
        buttons.getChildren().add(cancelButton);

        //-----Submit Button------
        submitButton = new Button("Submit");
        submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            
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
        buttons.getChildren().add(submitButton);

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
        articleType.setStyle("");
        color1.setStyle("");
        color2.setStyle("");
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
            insertProperties.setProperty("articleTypeId", (String) articleType.getValue().getState("id"));
            insertProperties.setProperty("color1Id", (String) color1.getValue().getState("id"));
            insertProperties.setProperty("color2Id", (color2.getValue() == null) ? "" : (String) color2.getValue().getState("id"));
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

    private ListCell<ArticleType> makeArticleTypeCell() {
        return new ListCell<ArticleType>() {
        
            @Override protected void updateItem(ArticleType item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText((String)item.getState("description"));
                }
                else {
                    setText("");
                }
            }
        };
    }

    private ListCell<Color> makeColorCell() {
        return new ListCell<Color>() {
        
            @Override protected void updateItem(Color item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText((String)item.getState("description"));
                }
                else {
                    setText("");
                }
            }
        };
    }

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
        articleType.setStyle("");
        color1.setStyle("");
        color2.setStyle("");
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
