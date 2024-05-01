// specify the package
package model;

// system imports
import javafx.stage.Stage;
import model.ArticleType;
import model.Transaction;
import javafx.scene.Scene;

import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

// project imports
import event.Event;
import exception.InvalidPrimaryKeyException;

import userinterface.View;
import userinterface.ViewFactory;

public class ModifyInventoryTransaction extends Transaction{
    private String transactionStatusMessage = "";

    private String barcode;
    private InventoryItem item = new InventoryItem();
    private ArticleType articleType = new ArticleType();
    private Color color1;
    private Color color2;


    public ModifyInventoryTransaction() throws Exception {
        super();
    }

    @Override
    protected void setDependencies() {
        dependencies = new Properties();
        dependencies.setProperty("DoScanBarcode", "TransactionStatus");
		dependencies.setProperty("DoModifyInventory", "TransactionStatus");
        dependencies.setProperty("CancelModifyInventory", "TransactionStatus");
        dependencies.setProperty("CancelScanBarcode", "CancelTransaction");

		myRegistry.setDependencies(dependencies);
    }

    @Override
    protected Scene createView() {
        Scene currentScene = myViews.get("ScanBarcodeView");

		if (currentScene == null) {
			// create our new view
			View newView = ViewFactory.createView("ScanBarcodeView", this);
			currentScene = new Scene(newView);
			myViews.put("ScanBarcodeView", currentScene);

			return currentScene;
		}
		else {
			return currentScene;
		}
    }

    @Override
    public Object getState(String key) {
        switch (key) {
            case "TransactionStatus":
                return transactionStatusMessage;
            case "articleTypeBarcodePF":
                return articleType.getState("barcodePrefix");
            case "color1BarcodePF":
                return color1.getState("barcodePrefix");
            case "articleType":
                return articleType;
            case "color1":
                return color1;
            case "color2":
                return color2;
            case "color2BarcodePF":
                if (color2 != null) {
                    return color2.getState("description");
                }
            case "barcode":
            case "size":
            case "gender":
            case "brand":
            case "notes":
            case "status":
            case "donorLastName":
            case "donorFirstName":
            case "donorPhone":
            case "donorEmail":
            case "receiverNetid":
            case "receiverLastName":
            case "receiverFirstName":
            case "dateDonated":
            case "dateTaken":
                return item.getState(key);
            default:
                System.err.println("ModifyInventoryTransaction: invalid key for getState: "+key);
                break;
		}
		return null;
	}

    @Override
    public void stateChangeRequest(String key, Object value) {
        switch(key) {
            case "DoYourJob":
                doYourJob();
                break;
            case "CancelModifyInventory":
                swapToView(createView());
                break;
            case "DoScanBarcode":
                boolean isFound = false;
                isFound = scanBarcode((Properties) value);
                if (isFound) {
                    transactionStatusMessage = "";
                    createAndShowModifyInventoryView();
                } else {
                    System.out.println("barcode: " + barcode + " not found");
                    transactionStatusMessage = "No Inventory Item found";
                }
                break;
            case "DoModifyInventory":
                modify((Properties) value);
                Properties barcodeProp = new Properties();
                barcodeProp.setProperty("barcode", barcode);
                scanBarcode(barcodeProp);
                break;
            case "CancelModifyInventoryItem":
                swapToView(createView());
                break;
            default:
                System.err.println("ModifyInventoryTransaction: invalid key for stateChangeRequest " + key);
        }
		myRegistry.updateSubscribers(key, this);
    }

    public boolean scanBarcode(Properties prop){
        barcode = prop.getProperty("barcode");

        try {
            item = new InventoryItem(barcode);
            item.update();

            articleType = articleType.findArticleTypeById((String)item.getState("articleTypeId"));

            color1 = new Color((String)item.getState("color1Id"));

            if ( !(((String) item.getState("color2Id")) == null) ) {
                color2 = new Color((String)item.getState("color2Id"));
            }
            transactionStatusMessage = (String)item.getState("UpdateStatusMessage");
            return true;
        } catch (Exception e) {
            transactionStatusMessage = e.getMessage();
        }

        return false;
    }

    public void createAndShowModifyInventoryView() {
		View newView = ViewFactory.createView("ModifyInventoryView", this);
        Scene currentScene = new Scene(newView);
        myViews.put("ModifyInventoryView", currentScene);
		swapToView(currentScene);
	}

    public void createAndShowClerkView() {
		View newView = ViewFactory.createView("ClerkView", this);
        Scene currentScene = new Scene(newView);
        myViews.put("ClerkView", currentScene);
		swapToView(currentScene);
	}
    
    protected void modify(Properties props) {
        // preserve props that arent on modify screen
        String barcode = (String) item.getState("barcode");
        String dateDonated = (String) item.getState("dateDonated");
        props.setProperty("barcode", barcode);
        props.setProperty("dateDonated", dateDonated);

        item.removeItem();
        InventoryItem tempItem = new InventoryItem(props);
        tempItem.save();
        item = tempItem;
        barcode = (String) item.getState("barcode");
	}
}
