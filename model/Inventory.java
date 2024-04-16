// specify the package
package model;

// system imports
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

/** The class containing the Account for the ATM application */
//==============================================================
public class Inventory extends EntityBase implements IView
{
	private static final String myTableName = "Inventory";

	protected Properties dependencies;

	// GUI Components

	private String updateStatusMessage = "";

	public Inventory(){
		super(myTableName);
		setDependencies();
	}


	// constructor for this class
	//----------------------------------------------------------
	public Inventory(String barcode)
			throws InvalidPrimaryKeyException
	{
		super(myTableName);

		setDependencies();
		String query = "SELECT * FROM " + myTableName + " WHERE (barcode = " + barcode + ")";

		Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

		// You must get one book at least
		if (allDataRetrieved != null)
		{
			int size = allDataRetrieved.size();

			// There should be EXACTLY one book. More than that is an error
			if (size != 1)
			{
				throw new InvalidPrimaryKeyException("Multiple Inventory matching barcode : "
						+ barcode + " found.");
			}
			else
			{
				// copy all the retrieved data into persistent state
				Properties retrievedAccountData = allDataRetrieved.elementAt(0);
				persistentState = new Properties();

				Enumeration allKeys = retrievedAccountData.propertyNames();
				while (allKeys.hasMoreElements() == true)
				{
					String nextKey = (String)allKeys.nextElement();
					String nextValue = retrievedAccountData.getProperty(nextKey);
					// bookId = Integer.parseInt(retrievedAccountData.getProperty("bookId"));

					if (nextValue != null)
					{
						persistentState.setProperty(nextKey, nextValue);
					}
				}

			}
		}
		// If no Inventory found for this barcode, throw an exception
		else
		{
			throw new InvalidPrimaryKeyException("No inventory matching barcode : "
					+ barcode + " found.");
		}
	}

	// Can also be used to create a NEW Book (if the system it is part of
	// allows for a new account to be set up)
	//----------------------------------------------------------
	public Inventory(Properties props)
	{
		super(myTableName);

		setDependencies();
		persistentState = new Properties();
		Enumeration allKeys = props.propertyNames();
		while (allKeys.hasMoreElements() == true)
		{
			String nextKey = (String)allKeys.nextElement();
			String nextValue = props.getProperty(nextKey);


			if (nextValue != null)
			{
				persistentState.setProperty(nextKey, nextValue);
			}
		}
	}

	public String toString()
	{
		setDependencies();
		return "\nDescription: " + persistentState.getProperty("description") + "\n Barcode Prefix: " +
			persistentState.getProperty("barcodePrefix") + "\n Alpha Code: " +
			persistentState.getProperty("alphaCode");
	}

	//-----------------------------------------------------------------------------------
	private void setDependencies()
	{
		dependencies = new Properties();

		dependencies.setProperty("BookCollection", "BookCollectionMessage");
		myRegistry.setDependencies(dependencies);
	}

	//----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("UpdateStatusMessage") == true) {
			return updateStatusMessage;
		}

		return persistentState.getProperty(key);
	}

	//----------------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{

		myRegistry.updateSubscribers(key, this);
	}

	/** Called via the IView relationship */
	//----------------------------------------------------------
	public void updateState(String key, Object value)
	{
		stateChangeRequest(key, value);
	}

	//-----------------------------------------------------------------------------------
	public void save() // save()
	{
		updateStateInDatabase();
	}

	//-----------------------------------------------------------------------------------
	private void updateStateInDatabase()
	{
		try
		{
			if (persistentState.getProperty("barcode") != null)
			{
				// update
				Properties whereClause = new Properties();
				whereClause.setProperty("barcode",
						persistentState.getProperty("barcode"));
				updatePersistentState(mySchema, persistentState, whereClause);
				updateStatusMessage = "Inventory data for barcode : " + persistentState.getProperty("barcode") + " updated successfully in database!";
			}
			else
			{
				// insert
				Integer id =
						insertAutoIncrementalPersistentState(mySchema, persistentState);
				persistentState.setProperty("barcode", "" + id.intValue());
				updateStatusMessage = "Inventory data for new Inventory : " +  persistentState.getProperty("barcode")
						+ "installed successfully in database!";
			}
		}
		catch (SQLException ex)
		{
			updateStatusMessage = "Error in adding Inventory data in database!";
		}
		//DEBUG System.out.println("updateStateInDatabase " + updateStatusMessage);
	}


	//Displays inventory information
	//--------------------------------------------------------------------------
	public Vector<String> getEntryListView()
	{
		Vector<String> v = new Vector<String>();

		v.addElement(persistentState.getProperty("barcode"));
		v.addElement(persistentState.getProperty("gender"));
		v.addElement(persistentState.getProperty("size"));
		v.addElement(persistentState.getProperty("articleTypeId"));
		v.addElement(persistentState.getProperty("color1Id"));
		v.addElement(persistentState.getProperty("color2Id"));
		v.addElement(persistentState.getProperty("brand"));
		v.addElement(persistentState.getProperty("notes"));
		v.addElement(persistentState.getProperty("status"));
		v.addElement(persistentState.getProperty("donorLastName"));
		v.addElement(persistentState.getProperty("donorFirstName"));
		v.addElement(persistentState.getProperty("donorPhone"));
		v.addElement(persistentState.getProperty("donorEmail"));
		v.addElement(persistentState.getProperty("receiverNetid"));
		v.addElement(persistentState.getProperty("receiverLastname"));
		v.addElement(persistentState.getProperty("receiverFirstname"));
		v.addElement(persistentState.getProperty("dateDonated"));
		v.addElement(persistentState.getProperty("dateTaken"));

		return v;
	}

	//-----------------------------------------------------------------------------------
	protected void initializeSchema(String tableName)
	{
		if (mySchema == null)
		{
			mySchema = getSchemaInfo(tableName);
		}
	}

	//-----------------------------------------------------------------------------------
	public static int compare(Inventory a, Inventory b)
	{
		String aNum = (String)a.getState("barcode");
		String bNum = (String)b.getState("barcode");

		return aNum.compareTo(bNum);
	}

	/*delete----------------------------------------------
	 * Set status to inactive
	 */
	public void delete() {
        persistentState.setProperty("status", "Inactive");
    }//End delete----------------------------------------

	/*modify--------------------------------------------
	 * 
	 */
	public void modify(Properties props) {
        Enumeration allKeys = props.propertyNames();
        while (allKeys.hasMoreElements() == true) {
            String nextKey = (String) allKeys.nextElement();
            String nextValue = props.getProperty(nextKey);

            if (nextValue != null) {
                persistentState.setProperty(nextKey, nextValue);
            }
        }
    }//End modify-----------------------------------------

	/*update---------------------------------------------
	 * calls updateStateInDatabase
	 */
	 //-----------------------------------------------------------------------------------
	 public void update() // save()
	 {
		 updateStateInDatabase();
	 }
}