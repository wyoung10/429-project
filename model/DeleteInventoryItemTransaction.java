package model;

import javafx.scene.Scene;
import java.util.Properties;

import exception.InvalidPrimaryKeyException;
import userinterface.View;
import userinterface.ViewFactory;

public class DeleteInventoryItemTransaction extends Transaction {
    private String transactionStatusMessage = "";
    private String transactionErrorMessage = "";

    private InventoryItem item;

    public DeleteInventoryItemTransaction() throws Exception {
        super();
    }

    protected void setDependencies() {
        dependencies = new Properties();
        dependencies.setProperty("DeleteInventoryItem", "TransactionStatus");
        dependencies.setProperty("DoScanBarcode", "TransactionError");
        dependencies.setProperty("CancelScanBarcode", "CancelTransaction");
        myRegistry.setDependencies(dependencies);
    }

    public void processTransaction() {
        item.delete();
        item.update();
        transactionStatusMessage = (String)item.getState("UpdateStatusMessage");
    }

    public Object getState(String key) {
        switch (key) {
            case "TransactionStatus":
                return transactionStatusMessage;
            case "TransactionError":
                return transactionErrorMessage;
            case "InventoryItem":
                return item;
            default:
                System.err.println("DeleteInventoryItemTransaction: invalid key for getState: "+key);
                break;
        }
        return null;
    }

    public void stateChangeRequest(String key, Object value) {
        switch(key) {
            case "DoYourJob":
                doYourJob();
                break;
            case "DeleteInventoryItem":
                processTransaction();
                break;
            case "DoScanBarcode":
                try
                {
                    item = new InventoryItem(((Properties)value).getProperty("barcode"));
                    createAndShowDeleteInventoryItemView();
                }
                catch (InvalidPrimaryKeyException ex)
                {
                    transactionErrorMessage = "ERROR: No inventory item found with barcode: " + ((Properties)value).getProperty("barcode");
                }
                break;
            case "CancelDeleteInventoryItem":
                swapToView(createView());
                break;
        }
        myRegistry.updateSubscribers(key, this);
    }

    protected Scene createView() {
        View newView = ViewFactory.createView("ScanBarcodeView", this);
        Scene currentScene = new Scene(newView);
        myViews.put("ScanBarcodeView", currentScene);
        return currentScene;
    }

    protected void createAndShowDeleteInventoryItemView() {
        View newView = ViewFactory.createView("DeleteInventoryItemView", this);
        Scene currentScene = new Scene(newView);
        myViews.put("DeleteInventoryItemView", currentScene);
        swapToView(currentScene);
    }
}