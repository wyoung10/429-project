package model;

//import packages
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import javax.swing.JFrame;

// project imports
import exception.InvalidPrimaryKeyException;
import database.*;

import impresario.IView;

import userinterface.View;
import userinterface.ViewFactory;

//===================================================================
public class InventoryItem extends EntityBase implements IView {
    
    //Declare string of table name to reference
    private static final String myTableName = "Inventory";

    //Properties Object declared
    protected Properties dependencies;

    //Guii Components
    private String updateStatusMessage = "";

    //Will need already existing flag
    Boolean preexists = false;

    //------------------------------------------------------------
    /*CONSTRUCTOR
     * Constructor to pull existing record from table
     * Takes barcode value
     */
    public InventoryItem(String barcode) throws InvalidPrimaryKeyException{
        super(myTableName);

        setDependencies();

        String query = "SELECT * FROM " + myTableName + " WHERE (barcode = " + barcode + ")";

        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
        
        //If value from table isn't null...
        if (allDataRetrieved != null) //Should also have "&& (allDataRef...size() > 0))"
        {
            int size = allDataRetrieved.size();

            // There should be EXACTLY one record. Throw error otherwise
            if (size != 1)
            {
                throw new InvalidPrimaryKeyException("Multiple items matching barcode : "
                        + barcode + " found.");
            }
            else
            {
                // copy all the retrieved data into persistent state.
                // This properties object holds data that is being passed to be used 
                // within this class locally.
                Properties retrievedInvenItemData = allDataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedInvenItemData.propertyNames();
                while (allKeys.hasMoreElements() == true)
                {
                    String nextKey = (String)allKeys.nextElement();
                    String nextValue = retrievedInvenItemData.getProperty(nextKey);
                    // accountNumber = Integer.parseInt(retrievedAccountData.getProperty("accountNumber"));

                    if (nextValue != null)
                    {
                        persistentState.setProperty(nextKey, nextValue);
                    }
                }

            }
            System.out.println("--NOTICE: INVENTORY ITEM OBJECT CREATED FROM TABLE--"); //Debug
            preexists = true;
        }//Endif taking existing entry

        // If no inventory item exists, throw error
        else
        {
            throw new InvalidPrimaryKeyException("No inventory item with barcode: "
                    + barcode + " found.");
        }
    }//End constructor1-------------------------------------------------------------------------------------------

    /**===================================================================
     * CONSTUCTOR 2
     *
     * Constructor when passed Properties will create new object for insertion
     *
     * @param props passed from view with fields to create item
     */
    public InventoryItem(Properties props) {
        super(myTableName); //Connect object to database

        //persistent state for current object
        //eventually passed to database for insertion
        persistentState = new Properties();

        //Enumeration for the fields passed by props/view
        Enumeration allKeys = props.propertyNames();

        //Go through keys and add to persistent state
        while (allKeys.hasMoreElements() == true){
            String nextKey = (String) allKeys.nextElement();
            String nextValue = props.getProperty(nextKey);

            if (nextValue != null) {
                persistentState.setProperty(nextKey, nextValue);
            }
        }
    }//END CONSTRUCTOR 2=============================================

    //===============================METHODS===================================


    /**================================================================
     * setDependencies
     *
     * sets delegate relationships
     */
    private void setDependencies()
    {
        dependencies = new Properties();

        myRegistry.setDependencies(dependencies);
    }//End setDependencies---------------------------------------

    /**==============================================================
     * update
     *
     * calls updateStateInDatabase. Equivalent to save()
     */
    public void update(){
        updateStateInDatabase();
    }//End update-------------------------------------------

    /**==============================================================
     * updateStateInDatabase
     *
     * If object already existed then update in database so change its values
     *
     * If object didn't exist then object must be inserted into database
     *
     * This based on preexist boolean
     */
    private void updateStateInDatabase(){
        //Try based on id property
        try{
            //update if preexists
            if (preexists == true){
                Properties whereClause = new Properties();
                whereClause.setProperty("id",
                        persistentState.getProperty("id"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Inventory Item Updated!";
            } else { //insert if not preexists
                Integer id = insertAutoIncrementalPersistentState(
                        mySchema, persistentState);
                persistentState.setProperty("id", "" + id);
                updateStatusMessage = "Inventory Item created in database!";
            }
        } catch (SQLException ex) {
            updateStatusMessage = "Error: " + ex.getMessage();
        }
    }//End updateStateInDatabase------------------------------


    /**==========================================================
     * updateState
     *
     * passes key and value to stateChangeRequest
     *
     * @param key action to be done
     * @param value ??
     */
    public void updateState(String key, Object value) {
        stateChangeRequest(key, value);
    }//End updateState------------------------------------------


    /**===========================================================
     * getState
     *
     * returns the corresponding property of the key passed
     * if asking for status message return updateStatusMessage
     *
     * @param key property to be returned
     */
    public Object getState(String key) {
        if (key.equals("UpdateStatusMessage") == true)
            return updateStatusMessage;

        return persistentState.getProperty(key);
    }//End getState------------------------------------------


    /**=============================================================
     * stateChangeRequest
     *
     * updates subscriber (Clerk) based on passed key and value
     *
     * @param key key for Clerk's statechangerequest
     * @param value ??
     */
    public void stateChangeRequest(String key, Object value) {

       myRegistry.updateSubscribers(key, this);

    }//End initializeSchema------------------------------------

    /**===============================================================
     * initializeSchema
     *
     * creates schema of table
     *
     * @param tableName name of the matching table in database
     */
    protected void initializeSchema(String tableName) {
        if (mySchema == null) {
            mySchema = getSchemaInfo(tableName);
        }
    }//End initializeSchema------------------------------------

    /**==============================================================
     * toString
     *
     * returns string with object's fields
     */
    public String toString(){

        return "string of inventory item";

    }//End toString--------------------------------------------

    /**===========================================================
     * getEntryListView
     *
     * creates vector for table display
     *
     * @return v vector of obejct properties
     */
    public Vector<String> getEntryListView() {
        Vector<String> v = new Vector();

        v.addElement(persistentState.getProperty("barcode"));
        v.addElement(persistentState.getProperty("gender"));
        v.addElement(persistentState.getProperty("size"));
        v.addElement(persistentState.getProperty("articleTypeId"));
        v.addElement(persistentState.getProperty("color1Id"));
        v.addElement(persistentState.getProperty("color2Id"));
        v.addElement(persistentState.getProperty("brand"));
        v.addElement(persistentState.getProperty("notes"));
        v.addElement(persistentState.getProperty("donorLastName"));
        v.addElement(persistentState.getProperty("donorFirstName"));
        v.addElement(persistentState.getProperty("donorPhone"));
        v.addElement(persistentState.getProperty("donorEmail"));


        return v;
    }//End getEntryListView-----------------------------------


}//END CLASS==========================================================
