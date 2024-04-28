// specify the package
package model;

// system imports
import javafx.stage.Stage;
import model.ArticleType;
import model.Transaction;
import javafx.scene.Scene;

import java.util.Properties;
import java.util.Vector;

// project imports
import event.Event;
import exception.InvalidPrimaryKeyException;

import userinterface.View;
import userinterface.ViewFactory;

public class ModifyInventoryTransaction extends Transaction{
    private String transactionErrorMessage = "";
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
		dependencies.setProperty("DoModifyInventory", "TransactionError");
		dependencies.setProperty("CancelModifyInventory", "CancelTransaction");

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
            case "TransactionError":
                return transactionErrorMessage;
            case "articleTypeBarcodePF":
                return articleType.getState("barcodePrefix");
            case "color1BarcodePF":
                return color1.getState("barcodePrefix");
            case "color2BarcodePF":
                if (color2 != null) {
                    return color2.getState("barcodePrefix");
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
                    transactionErrorMessage = "";
                    createAndShowModifyInventoryView();
                } else {
                    System.out.println("barcode: " + barcode + " not found");
                    transactionErrorMessage = "No Inventory Item found";
                }
                break;
            case "Modify":
                modify((Properties) value);
                Properties barcodeProp = new Properties();
                barcodeProp.setProperty("barcode", barcode);
                scanBarcode(barcodeProp);
                createAndShowModifyInventoryView();
                transactionStatusMessage = "Successfully Modified";
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
        } catch (InvalidPrimaryKeyException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public void createAndShowModifyInventoryView() {
		View newView = ViewFactory.createView("ModifyInventoryView", this);
        Scene currentScene = new Scene(newView);
        myViews.put("ModifyInventoryView", currentScene);
		swapToView(currentScene);
	}
    
    protected void modify(Properties prop) {
        String articleTypeBarcodePFString = prop.getProperty("articleTypeBarcodePF");
        prop.remove("articleTypeBarcodePF");
        try {
            articleType = new ArticleType(articleTypeBarcodePFString);
            prop.setProperty("articleTypeId", (String)articleType.getState("id"));
        } catch (InvalidPrimaryKeyException e) {
            transactionErrorMessage = e.getMessage();
        }

        String color1BarcodePFString = prop.getProperty("color1BarcodePF");
        prop.remove("color1BarcodePF");

        String color2BarcodePFString = prop.getProperty("color2BarcodePF");
        prop.remove("color2BarcodePF");

        try {
            color1 = color1.findColorByBarcodePrefix(color1BarcodePFString);
            prop.setProperty("color1Id", (String)color1.getState("id"));

            if (color2BarcodePFString.length() != 0) {
                color2 = new Color();
                color2 = color2.findColorByBarcodePrefix(color2BarcodePFString);
                prop.setProperty("color2Id", (String)color2.getState("id"));
            } else {
                prop.remove("color2Id");
                
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

		item.modify(prop);
		item.save();
	}
}
