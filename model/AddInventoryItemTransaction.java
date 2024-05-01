package model;

// system imports
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

// project imports
import event.Event;
import exception.InvalidPrimaryKeyException;

import userinterface.View;
import userinterface.ViewFactory;

public class AddInventoryItemTransaction extends Transaction {
    private String transactionStatusMessage = "";

    private InventoryItem inventoryItem;

    //Objects for inc fields
    String barcode = new String();
    String gender = new String();
    String article = new String();
    String color = new String();
    String color2 = new String();
    String size = new String();
    String brand = new String();
    String notes = new String();
    String fname = new String();
    String lname = new String();
    String phone = new String();
    String email = new String();

    /**=========================================================
     * CONSTRUCTOR
     * @throws Exception
     */
    public AddInventoryItemTransaction() throws Exception {
        super();
    }//END CONSTRUCTOR===========================================

    /**==========================================================
     * setDependencies
     *
     * Set dependency for delegate relationship
     */
    protected void setDependencies() {
        dependencies = new Properties();

        dependencies.setProperty("DoAddInventoryItem", "TransactionError");
        dependencies.setProperty("CancelAddInventoryItem", "CancelTransaction");
        dependencies.setProperty("OK", "CancelTransaction");

        myRegistry.setDependencies(dependencies);
    }//End setDependencies----------------------------------------

    /**===========================================================
     * processTransaction
     *
     * Takes properties object and creates new InventoryItem object
     * calls its update method to insert into database
     *
     * @param props Properties object passed from view
     * @throws InvalidPrimaryKeyException
     */
    public void processTransaction(Properties props){
        //Do getProperty for all fields

        Enumeration allKeys = props.propertyNames();
        while (allKeys.hasMoreElements()) {
            String nextKey = (String) allKeys.nextElement();
            String nextValue = props.getProperty(nextKey);

            if (nextValue.equals("")) {
                props.remove(nextKey);
            }
        }

        barcode = props.getProperty("barcode");
        gender = props.getProperty("gender");
        size = props.getProperty("size");
        article = props.getProperty("articleTypeId");
        color = props.getProperty("color1Id");
        color2 = props.getProperty("color2Id");
        brand = props.getProperty("brand");
        notes = props.getProperty("notes");
        fname = props.getProperty("donorFirstName");
        lname = props.getProperty("donorLastName");
        phone = props.getProperty("donorPhone");
        email = props.getProperty("donorEmail");

        //create new InventoryItem
        InventoryItem inventoryItem = new InventoryItem(props);

        //Call its update method to insert
        inventoryItem.update();

        //update transactionStatusMessage
        transactionStatusMessage = (String)inventoryItem.getState("UpdateStatusMessage");
    }

    /**===============================================
     * getState
     *
     * returns field based on passed key
     *
     * @param key What the user wants to see
     *
     */
    public Object getState(String key){

        switch(key){
            case "TransactionStatus":
                return transactionStatusMessage;
            case "barcode":
                return barcode;
            case "gender":
                return gender;
            case "article":
                return article;
            case "color":
                return color;
            case "color2":
                return color2;
            case "size":
                return size;
            case "brand":
                return brand;
            case "notes":
                return notes;
            case "fname":
                return fname;
            case "lname":
                return lname;
            case "phone":
                return phone;
            case "email":
                return email;
            default:
                System.err.println("AddInventoryItemTransaction: Invalid key for getState!!" + key);
                break;
            }//End switch

        return null;
    }//End getState------------------------------------------

    /**==================================================
     * stateChangeRequest
     *
     * Does action based on passed key.
     * Takes object for possible transactions
     *
     * @param key String for what needs to be done
     * @param value Properties object used for various actions
     *              Should be passed from view
     */
    public void stateChangeRequest(String key, Object value){
        //Switch case for key
        switch(key){
            case "DoYourJob": //called from doTransaction of Clerk
                doYourJob(); //Extended from Transaction, will create view
                break;
            case "DoAddInventoryItem": //Called from view
                processTransaction((Properties)value);
                break;
            case "CancelAddInventoryItem": //Called from view
                swapToView(createView());
                break;
        }//End switch case

        //send message to subscriber
        myRegistry.updateSubscribers(key, this);
    }//End stateChangeRequest---------------------------------------------

    /**=======================================================
     * createView
     *
     * creates view and swaps with current scene
     */
    protected Scene createView(){
        Scene currentScene = myViews.get("AddInventoryItemView");

        if (currentScene == null) {
            //create new view if scene is somehow empty
            View newView = ViewFactory.createView("AddInventoryItemView", this);
            currentScene = new Scene(newView);

            return currentScene;
        } else {
            return currentScene;
        }
    }//End createView-------------------------------------------



}//END ADDINVENTORYITEMTRANSACTION=================================
