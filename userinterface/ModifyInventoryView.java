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

//==============================================================================
public class ModifyInventoryView extends View {
    protected Text barcodeText;
    protected TextField gender;
    protected TextField size;

    protected TextField articleTypeBarcodePF;
    protected TextField color1BarcodePF;
    protected TextField color2BarcodePF;

    protected TextField brand;
    protected TextField notes;

    protected TextField donorLastname;
    protected TextField donorFirstname;
    protected TextField donorPhone;

    protected TextField donorEmail;
    protected TextField receiverNetid;
    protected TextField receiverLastname;

    protected TextField receiverFirstname;
    protected TextField dateDonated;
    protected TextField dateTaken;

	protected Button cancelButton;
	protected Button submitButton;

	protected MessageView statusLog;


	//--------------------------------------------------------------------------
	public ModifyInventoryView(IModel wsc)
	{
		super(wsc, "ModifyInventoryView");

		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setPadding(new Insets(15, 5, 5, 5));

		// create our GUI components, add them to this panel
		container.getChildren().add(createTitle());
		container.getChildren().add(createFormContent());

		// Error message area
		container.getChildren().add(createStatusLog("                                            "));

		getChildren().add(container);
		
		populateFields();

        myModel.subscribe("TransactionStatus", this);
	}

	//--------------------------------------------------------------------------
	protected void populateFields()
	{
		getInventoryData();
	}

	//--------------------------------------------------------------------------
	protected void getInventoryData() {
		barcodeText.setText((String)(myModel.getState("barcode")));
        gender.setText((String)myModel.getState("gender"));
        size.setText((String)myModel.getState("size"));

        articleTypeBarcodePF.setText((String)(myModel.getState("articleTypeBarcodePF")));
        color1BarcodePF.setText((String)myModel.getState("color1BarcodePF"));
        color2BarcodePF.setText((String)myModel.getState("color2BarcodePF"));

        brand.setText((String)(myModel.getState("brand")));
        notes.setText((String)myModel.getState("notes"));

        donorLastname.setText((String)(myModel.getState("donorLastName")));
        donorFirstname.setText((String)myModel.getState("donorFirstName"));
        donorPhone.setText((String)myModel.getState("donorPhone"));

        donorEmail.setText((String)(myModel.getState("donorEmail")));
        receiverNetid.setText((String)myModel.getState("receiverNetid"));
        receiverLastname.setText((String)myModel.getState("receiverLastName"));

        receiverFirstname.setText((String)(myModel.getState("receiverFirstName")));
        dateDonated.setText((String)myModel.getState("dateDonated"));
        dateTaken.setText((String)myModel.getState("dateTaken"));

	}

	// Create the title container
	//-------------------------------------------------------------
	private Node createTitle() {
		HBox container = new HBox();
		container.setAlignment(Pos.CENTER);	

		Text titleText = new Text(" Modify Article Type ");
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
        
        Text prompt = new Text("Modify Inventory details:");
        prompt.setWrappingWidth(350);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(javafx.scene.paint.Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

		Text barcodeLabel = styleTextLable("Barcode");
        Text genderLabel = styleTextLable("Gender");
		Text sizeLabel = styleTextLable("Size");

        Text articleTypeBarcodePFLabel = styleTextLable("Article Type Barcode Prefix");
        Text color1BarcodePFLabel = styleTextLable("Color 1 Barcode Prefix");
		Text color2BarcodePFLabel = styleTextLable("Color 2 Barcode Prefix");

        Text brandLabel = styleTextLable("Brand");
        Text notesLabel = styleTextLable("Notes");

        Text donorLastnameLabel = styleTextLable("Donor Last Name");
        Text donorFirstnameLabel = styleTextLable("Donot First Name");
		Text donorPhoneLabel = styleTextLable("Donor Phone Number");

        Text donorEmailLabel = styleTextLable("Donor Email");
        Text receiverNetidLabel = styleTextLable("Receiver Net Id");
		Text receiverLastnameLabel = styleTextLable("Receiver Last Name");

        Text receiverFirstnameLabel = styleTextLable("Donor First Name");
        Text dateDonatedLabel = styleTextLable("Date Donated");
		Text dateTakenLabel = styleTextLable("Date Taken");
        

		grid.add(barcodeLabel, 0, 1);
		grid.add(genderLabel, 0, 2);
		grid.add(sizeLabel, 0, 3);

        grid.add(articleTypeBarcodePFLabel, 0, 4);
		grid.add(color1BarcodePFLabel, 0, 5);
		grid.add(color2BarcodePFLabel, 0, 6);

        grid.add(brandLabel, 0, 7);
		grid.add(notesLabel, 0, 8);

        grid.add(donorFirstnameLabel, 0, 9);
		grid.add(donorLastnameLabel, 0, 10);
		grid.add(donorPhoneLabel, 0, 11);

        grid.add(donorEmailLabel, 0, 12);
		grid.add(receiverNetidLabel, 0, 13);
		grid.add(receiverFirstnameLabel, 0, 14);

        grid.add(receiverLastnameLabel, 0, 15);
		grid.add(dateDonatedLabel, 0, 16);
		grid.add(dateTakenLabel, 0, 17);

        barcodeText = new Text();
        gender = new TextField();
        size = new TextField();

        articleTypeBarcodePF = new TextField();
        color1BarcodePF = new TextField();
        color2BarcodePF = new TextField();

        brand = new TextField();
        notes = new TextField();

        // articleTypeId = new TextField();
        // color1Id = new TextField();
        // color2Id = new TextField();

        donorFirstname = new TextField();
        donorLastname = new TextField();
        donorPhone = new TextField();

        donorEmail = new TextField();
        receiverNetid = new TextField();
        receiverFirstname = new TextField();

        receiverLastname = new TextField();
        dateDonated = new TextField();
        dateTaken = new TextField();

        grid.add(barcodeText, 1, 1);
        grid.add(gender, 1, 2);
        grid.add(size, 1, 3);

        grid.add(articleTypeBarcodePF, 1, 4);
        grid.add(color1BarcodePF, 1, 5);
        grid.add(color2BarcodePF, 1, 6);

        grid.add(brand, 1, 7);
        grid.add(notes, 1, 8);

        grid.add(donorFirstname, 1, 9);
        grid.add(donorLastname, 1, 10);
        grid.add(donorPhone, 1, 11);

        grid.add(donorEmail, 1, 12);
        grid.add(receiverNetid, 1, 13);
        grid.add(receiverFirstname, 1, 14);

        grid.add(receiverLastname, 1, 15);
        grid.add(dateDonated, 1, 16);
        grid.add(dateTaken, 1, 17);


        HBox btnContainer = new HBox(100);
		btnContainer.setAlignment(Pos.CENTER);

		submitButton = new Button("Submit");
 		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				clearErrorMessage();
				processSubmit();
                statusLog.displayMessage((String) myModel.getState("TransactionStatus"));
			}
		});
        btnContainer.getChildren().add(submitButton);

		cancelButton = new Button("Back");
 		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	clearErrorMessage();
       		     	myModel.stateChangeRequest("CancelModifyArticleType", null); 
            	  }
        	});
		btnContainer.getChildren().add(cancelButton);
		

        ScrollPane sp = new ScrollPane(grid);
        sp.setMaxHeight(500);
        sp.setMaxWidth(vbox.getMaxWidth());
        
        statusLog = new MessageView("");

		vbox.getChildren().add(sp);
		vbox.getChildren().add(btnContainer);
        vbox.getChildren().add(statusLog);
	
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

    protected void processSubmit() {
        Properties props = new Properties();
        String genderString = "";
        String sizeString = "";
        String articleTypeBarcodePFString = "";
        String color1BarcodePFString = "";
        String color2BarcodePFString = "";
        String brandString = "";
        String noteString = "";
        String donorFirstNameString = "";
        String donorLastNameString = "";
        String donorPhoneString = "";
        String donorEmailString = "";
        String receiverNetidString = "";
        String receiverFirstNameString = "";
        String receiverLastNameString = "";
        String dateDonatedString = "";
        String dateTakenString = "";


        if (articleTypeBarcodePF.getText().isEmpty()){
            statusLog.displayErrorMessage("ArticleType Barcode Prefix cannot be empty");
        } else if (color1BarcodePF.getText().isEmpty()) {
            statusLog.displayErrorMessage("Color1 Barcode Prefix cannot be empty");
        } else {
            
            try {
                genderString = gender.getText();
                sizeString = size.getText();
                articleTypeBarcodePFString = articleTypeBarcodePF.getText();
                color1BarcodePFString = color1BarcodePF.getText();
                color2BarcodePFString = color2BarcodePF.getText();
                brandString = brand.getText();
                noteString = notes.getText();
                donorFirstNameString = donorFirstname.getText();
                donorLastNameString = donorLastname.getText();
                donorPhoneString = donorPhone.getText();
                donorEmailString = donorEmail.getText();
                receiverNetidString = receiverNetid.getText();
                receiverFirstNameString = receiverFirstname.getText();
                receiverLastNameString = receiverLastname.getText();
                dateDonatedString = dateDonated.getText();
                dateTakenString = dateTaken.getText();

                props.setProperty("gender", genderString);
                props.setProperty("size", sizeString);
                props.setProperty("articleTypeBarcodePF", articleTypeBarcodePFString);
                props.setProperty("color1BarcodePF", color1BarcodePFString);
                props.setProperty("color2BarcodePF", color2BarcodePFString);
                props.setProperty("brand", brandString);
                props.setProperty("notes", noteString);
                props.setProperty("donorFirstName", donorFirstNameString);
                props.setProperty("donorLastName", donorLastNameString);
                props.setProperty("donorPhone", donorPhoneString);
                props.setProperty("donorEmail", donorEmailString);
                props.setProperty("receiverNetId", receiverNetidString);
                props.setProperty("receiverFirstName", receiverFirstNameString);
                props.setProperty("receiverLastname", receiverLastNameString);

                myModel.stateChangeRequest("Modify", props);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        myModel.stateChangeRequest("Modify", props);
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

   public Text styleTextLable(String labelName){

        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
        Text label = new Text(labelName + ": ");
        label.setFont(myFont);
        label.setWrappingWidth(150);
        label.setTextAlignment(TextAlignment.RIGHT);

        return label;
   }
	
}