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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.ArticleType;
import model.ArticleTypeCollection;
import model.Color;
import model.ColorCollection;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

// project imports
import impresario.IModel;

//==============================================================================
public class ModifyInventoryView extends View {
    protected Text barcodeText;
    protected TextField gender;
    protected TextField size;

    protected ArticleTypeCollection articleTypes;
    protected ColorCollection colors;

    protected ComboBox<ArticleType> articleType;
    protected ComboBox<Color> color1;
    protected ComboBox<Color> color2;

    protected TextField brand;
    protected TextField notes;

    protected TextField donorLastName;
    protected TextField donorFirstName;
    protected TextField donorPhone;

    protected TextField donorEmail;

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

        getComboBoxData();

		// create our GUI components, add them to this panel
		container.getChildren().add(createTitle());
		container.getChildren().add(createFormContent());

		// Error message area
		container.getChildren().add(createStatusLog("                                            "));

		getChildren().add(container);
		
		populateFields();

        myModel.subscribe("TransactionStatus", this);
	}

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

        ArticleType currAT = (ArticleType)myModel.getState("articleType");
        for (int i = 0; i < articleType.getItems().size(); i++) {
            ArticleType next = articleType.getItems().get(i);
            if (next.getState("id").equals(currAT.getState("id"))) {
                articleType.setValue(next);
                break;
            }
        }
        Color currColor1 = (Color)myModel.getState("color1");
        for (int i = 0; i < color1.getItems().size(); i++) {
            Color next = color1.getItems().get(i);
            if (next.getState("id").equals(currColor1.getState("id"))) {
                color1.setValue(next);
                break;
            }
        }
        Color currColor2 = (Color)myModel.getState("color2");
        if (currColor2 != null) {
            for (int i = 0; i < color2.getItems().size(); i++) {
                Color next = color2.getItems().get(i);
                if (next.getState("id").equals(currColor2.getState("id"))) {
                    color2.setValue(next);
                    break;
                }
            }
        }

        brand.setText((String)(myModel.getState("brand")));
        notes.setText((String)myModel.getState("notes"));

        donorLastName.setText((String)(myModel.getState("donorLastName")));
        donorFirstName.setText((String)myModel.getState("donorFirstName"));
        donorPhone.setText((String)myModel.getState("donorPhone"));

        donorEmail.setText((String)(myModel.getState("donorEmail")));
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

        Text articleTypeDescLabel = styleTextLable("Article Type");
        Text color1DescLabel = styleTextLable("Color 1");
		Text color2DescLabel = styleTextLable("Color 2");

        Text brandLabel = styleTextLable("Brand");
        Text notesLabel = styleTextLable("Notes");

        Text donorLastNameLabel = styleTextLable("Donor Last Name");
        Text donorFirstNameLabel = styleTextLable("Donor First Name");
		Text donorPhoneLabel = styleTextLable("Donor Phone Number");

        Text donorEmailLabel = styleTextLable("Donor Email");
        

		grid.add(barcodeLabel, 0, 1);
		grid.add(genderLabel, 0, 2);
		grid.add(sizeLabel, 0, 3);

        grid.add(articleTypeDescLabel, 0, 4);
		grid.add(color1DescLabel, 0, 5);
		grid.add(color2DescLabel, 0, 6);

        grid.add(brandLabel, 0, 7);
		grid.add(notesLabel, 0, 8);

        grid.add(donorFirstNameLabel, 0, 9);
		grid.add(donorLastNameLabel, 0, 10);
		grid.add(donorPhoneLabel, 0, 11);

        grid.add(donorEmailLabel, 0, 12);

        barcodeText = new Text("");
        gender = new TextField("");
        size = new TextField("");

        barcodeText = new Text();
        gender = new TextField();
        size = new TextField();
        
        ObservableList<ArticleType> articleTypeList = FXCollections.observableArrayList(
            (Vector<ArticleType>)articleTypes.getState("ArticleTypes")
        );
        ObservableList<Color> colorList = FXCollections.observableArrayList(
            (Vector<Color>)colors.getState("Colors")
        );

        articleType = new ComboBox<ArticleType>();
        articleType.setCellFactory(listView -> makeArticleTypeCell());
        articleType.setButtonCell(makeArticleTypeCell());
        articleType.setItems(articleTypeList);

        color1 = new ComboBox<Color>();
        color1.setCellFactory(listView -> makeColorCell());
        color1.setButtonCell(makeColorCell());
        color1.setItems(colorList);

        color2 = new ComboBox<>();
        color2.setCellFactory(listView -> makeColorCell());
        color2.setButtonCell(makeColorCell());
        color2.setItems(colorList);

        Button clearColor2 = new Button("Clear");
        clearColor2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
                color2.setValue(null);
			}
		});
        
        brand = new TextField();
        notes = new TextField();

        donorFirstName = new TextField("");
        donorLastName = new TextField("");
        donorPhone = new TextField("");

        donorEmail = new TextField("");

        grid.add(barcodeText, 1, 1);
        grid.add(gender, 1, 2);
        grid.add(size, 1, 3);

        grid.add(articleType, 1, 4);
        grid.add(color1, 1, 5);
        grid.add(color2, 1, 6);
        grid.add(clearColor2, 2, 6);

        grid.add(brand, 1, 7);
        grid.add(notes, 1, 8);

        grid.add(donorFirstName, 1, 9);
        grid.add(donorLastName, 1, 10);
        grid.add(donorPhone, 1, 11);

        grid.add(donorEmail, 1, 12);

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);
        Button cancelButton = new Button("Back");
        cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                myModel.stateChangeRequest("CancelModifyInventory", null); 
            }
        });
		buttons.getChildren().add(cancelButton);

        submitButton = new Button("Submit");
        submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				clearErrorMessage();
				processSubmit();
			}
		});
        buttons.getChildren().add(submitButton);
		
        ScrollPane sp = new ScrollPane(grid);
        sp.setMaxHeight(500);
        sp.setMaxWidth(vbox.getMaxWidth());
        
        statusLog = new MessageView("");

		vbox.getChildren().add(sp);
		vbox.getChildren().add(buttons);
        vbox.getChildren().add(statusLog);
	
		return vbox;
	}

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

    protected boolean processSubmit() {
        Properties props = new Properties();
        String genderString = "";
        String sizeString = "";
        String articleTypeId = "";
        String color1Id = "";
        String color2Id = "";
        String brandString = "";
        String noteString = "";
        String donorFirstNameString = "";
        String donorLastNameString = "";
        String donorPhoneString = "";
        String donorEmailString = "";
       
        genderString = gender.getText();
        sizeString = size.getText();
        articleTypeId = (String)articleType.getValue().getState("id");
        color1Id = (String)color1.getValue().getState("id");
        if (color2.getValue() != null) {
            color2Id = (String)color2.getValue().getState("id");
        }
        brandString = brand.getText();
        noteString = notes.getText();
        donorFirstNameString = donorFirstName.getText();
        donorLastNameString = donorLastName.getText();
        donorPhoneString = donorPhone.getText();
        donorEmailString = donorEmail.getText();

        if ((size.getText().isEmpty())) {
            statusLog.displayErrorMessage("Size cannot be empty");
            return false;
        } else {
            props.setProperty("size", size.getText());
        }

        if (!(gender.getText().isEmpty())) {
            genderString = gender.getText();
            if (genderString.toUpperCase().equals("M") || genderString.toUpperCase().equals("W")) {
                props.setProperty("gender", genderString);
            } else {
                statusLog.displayErrorMessage("Gender can only be M or W (One letter)");
                return false;
            }
        } else {
            statusLog.displayErrorMessage("Gender cannot be empty");
            return false;
        }

        if (donorPhoneString != null) {
            if (!donorPhoneString.isEmpty()) {
                if (isValidPhoneNumber(donorPhoneString)) {
                    props.setProperty("donorPhone", donorPhoneString);
                } else {
                    statusLog.displayErrorMessage("Phone number must follow XXX-XXXX");
                    return false;
                }
            }
        }

        if (donorEmailString != null) {
            if (!donorEmailString.isEmpty()) {
                if (isValidEmail(donorEmailString)) {
                    props.setProperty("donorEmail", donorEmailString);
                } else {
                    statusLog.displayErrorMessage("Email must follow format: example@mail.com");
                    return false;
                }
            }
        }

        if (brandString != null) {
            if (!brandString.isEmpty()) {
                props.setProperty("brand", brandString);
            }
        }

        if (noteString != null) {
            if (!noteString.isEmpty()) {
                props.setProperty("notes", noteString);
            }
        }

        if (donorFirstNameString != null) {
            if (!donorFirstNameString.isEmpty()) {
                props.setProperty("donorFirstName", donorFirstNameString);
            }
        }

        if (donorLastNameString != null) {
            if (!donorLastNameString.isEmpty()) {
                props.setProperty("donorLastName", donorLastNameString);
            }
        }

        if (noteString != null) {
            if (!noteString.isEmpty()) {
                props.setProperty("notes", noteString);
            }
        }

        if (articleTypeId != null) {
            if (!articleTypeId.isEmpty()) {
                props.setProperty("articleTypeId", articleTypeId);
            }
        }
        else {
            statusLog.displayErrorMessage("Article Type cannot be empty");
            return false;
        }

        if (color1Id != null) {
            if (!color1Id.isEmpty()) {
                props.setProperty("color1Id", color1Id);
            }
        }
        else {
            statusLog.displayErrorMessage("Color1 cannot be empty");
            return false;
        }

        if (color2Id != null) {
            if (!color2Id.isEmpty()) {
                props.setProperty("color2Id", color2Id);
            }
        }

        statusLog.displayMessage("Success");
        myModel.stateChangeRequest("DoModifyInventory", props);

        return true;
    }

    private boolean isValidDate(String date){
        String regex = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher((CharSequence)date);
        return matcher.matches();
    }

    private boolean isValidEmail(String email) {
        String regex = "^[^@]+@[^@]+\\.[^@]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher((CharSequence)email);
        return matcher.matches();
    }

    private boolean isValidPhoneNumber(String phone){
        String regex = "[0-9]{3}-[0-9]{3}-[0-9]{4}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher((CharSequence)phone);
        return matcher.matches();
    }


	/**
	 * Display info message
	 */
	//----------------------------------------------------------
	public void displayMessage(String message)
	{
		statusLog.displayMessage(message);
	}

    public void displayErrorMessage(String message)
    {
        statusLog.displayErrorMessage(message);
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