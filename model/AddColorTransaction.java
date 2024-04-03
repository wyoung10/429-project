// specify the package
package model;

// system imports
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.util.Properties;
import java.util.Vector;

// project imports
import event.Event;
import exception.InvalidPrimaryKeyException;

import userinterface.View;
import userinterface.ViewFactory;

/** The class containing the AddColorTransaction for the ATM application */
//==============================================================
public class AddColorTransaction extends Transaction {
	private String transactionErrorMessage = "";
	private String transactionStatusMessage = "";
	
	


    private String id;
    private String description;
    private String barcodePrefix;
    private String alphaCode;

	public AddColorTransaction() throws Exception {
		super();
	}

	//----------------------------------------------------------
	protected void setDependencies() {
		dependencies = new Properties();
		dependencies.setProperty("DoAddColor", "TransactionStatus");
		dependencies.setProperty("CancelAddColor", "CancelTransaction");
		

		myRegistry.setDependencies(dependencies);
	}

	/**
	 * This method encapsulates all the logic of creating the account,
	 * verifying ownership, crediting, etc. etc.
	 */
	//----------------------------------------------------------
	public void processTransaction(Properties props) {
		id = props.getProperty("id");
        description = props.getProperty("description");
        barcodePrefix = props.getProperty("barcodePrefix");
        alphaCode = props.getProperty("alphaCode");

		Color color = new Color(props);
		color.update();
		transactionStatusMessage = (String)color.getState("UpdateStatusMessage");
	}

	//-----------------------------------------------------------
	public Object getState(String key) {
        switch (key) {
            case "TransactionStatus":
                return transactionStatusMessage;
            case "Id":
                return id;
            case "Description":
                return description;
            case "BarcodePrefix":
                return barcodePrefix;
            case "AlphaCode":
                return alphaCode;
            default:
                System.err.println("AddColorTransaction: invalid key for getState: "+key);
                break;
		}
		return null;
	}

	//-----------------------------------------------------------
	public void stateChangeRequest(String key, Object value) {
		switch(key) {
            case "DoYourJob":
                doYourJob();
                break;
            case "DoAddColor":   // gets called from AddColorTransactionView
                processTransaction((Properties)value);
                break;
			case "CancelAddColor":
                swapToView(createView());
                break;
            default:
                System.err.println("AddColorTransaction: invalid key for stateChangeRequest");
        }
		myRegistry.updateSubscribers(key, this);
	}

	/**
	 * Create the view of this class. And then the super-class calls
	 * swapToView() to display the view in the stage
	 */
	//------------------------------------------------------
	protected Scene createView() {
		Scene currentScene = myViews.get("AddColorView");

		if (currentScene == null) {
			// create our new view
			View newView = ViewFactory.createView("AddColorView", this);
			currentScene = new Scene(newView);
			myViews.put("AddColorView", currentScene);

			return currentScene;
		}
		else {
			return currentScene;
		}
	}
}