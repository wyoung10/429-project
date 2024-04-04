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

/** The class containing the DeleteColorTransaction for the ATM application */
//==============================================================
public class DeleteArticleTypeTransaction extends Transaction {
	private String transactionStatusMessage = "";

	private ArticleTypeCollection articleTypeCollection;
    private ArticleType selectedArticleType;
    private String description;
    private String barcodePrefix;
    private String alphaCode;    

	public DeleteArticleTypeTransaction() throws Exception {
		super();

        try {
            ArticleTypeCollection articleTypes = new ArticleTypeCollection();
            //articleTypes.getColors();
            articleTypeCollection = (ArticleTypeCollection)articleTypes.getState("ArticleTypeCollection");
        }
        catch (Exception exc) {
            System.err.println(exc);
        }
	}

	//----------------------------------------------------------
	protected void setDependencies() {
		dependencies = new Properties();
        dependencies.setProperty("DoDeleteArticleType", "TransactionStatus");
		dependencies.setProperty("CancelArticleTypeCollection", "CancelTransaction");

		myRegistry.setDependencies(dependencies);
	}

	/**
	 * Method creates table by taking in prop from text fields
	 */
	//----------------------------------------------------------
	public void processTransaction(Properties props) {

        description = props.getProperty("description");
        alphaCode = props.getProperty("alphaCode");
		articleTypeCollection = new ArticleTypeCollection();

		if (alphaCode != null && description == null) {
			articleTypeCollection.findArticleTypeAlphaCode(alphaCode);
			alphaCode = null;
		} else if (description != null && alphaCode == null){
			articleTypeCollection.findArticleTypeDesc(description);
			description = null;
		} else if (description != null && alphaCode != null){
            articleTypeCollection.findArticleTypeBoth(alphaCode, description);
            alphaCode = null;
            description = null;
        } else {
			articleTypeCollection.findArticleTypeDesc("");
		}
        
	}

    /*Called when confirm delete view submit button is hit
     * 
     */
    public void processDeleteTransaction() {
        selectedArticleType.delete();
        selectedArticleType.update();
        transactionStatusMessage = (String)selectedArticleType.getState("UpdateStatusMessage");
        try {
            //articleTypeCollection.getArticleTypes();
        }
        catch (Exception exc) {
            System.err.println(exc);
        }
    }

	//-----------------------------------------------------------
	public Object getState(String key) {
        switch (key) {
            case "TransactionStatus":
                return transactionStatusMessage;
            case "ArticleTypeCollection":
                return articleTypeCollection;
            case "SelectedArticleType":
                return selectedArticleType;
            case "id":
            case "description":
            case "barcodePrefix":
            case "alphaCode":
                return selectedArticleType.getState(key);
            case "string":
                return selectedArticleType.toString();
            default:
                System.err.println("DeleteArticleTypeTransaction: invalid key for getState: "+key);
                break;
		}
		return null;
	}

	//-----------------------------------------------------------
	public void stateChangeRequest(String key, Object value) {
		switch(key) {
            case "DoYourJob": //called from doTransaction of Clerk
                doYourJob(); //Extended from Transaction, will create view
                break;
            case "ArticleTypeSearch": //Called from DeleteArticleTypeView
				processTransaction((Properties)value);
				break;
            case "ArticleTypeSelected": //Called from DeleteArticleTypeView
                selectedArticleType = new ArticleType((Properties)value);
                createAndShowDeleteArticleTypeView();
				break;
            case "CancelDeleteArticleType":
                swapToView(createView());
                processTransaction(new Properties());
                break;
            case "ConfirmDeleteArticleType": //called from confirm view
                //actually sets article type to inactive
                processDeleteTransaction();
                break;
        }
		myRegistry.updateSubscribers(key, this);
	}

	protected Scene createView() {
        Scene currentScene = myViews.get("ArticleTypeCollectionView");

		if (currentScene == null) {
			// create our new view
			View newView = ViewFactory.createView("ArticleTypeCollectionView", this);
			currentScene = new Scene(newView);
			myViews.put("ArticleTypeCollectionView", currentScene);

			return currentScene;
		}
		else {
			return currentScene;
		}
	}
    
    /*Method called from local stateChangeRequest
     * - Upon double clicking table entry this should create new view for
     * confirming deletion
     */
    protected void createAndShowDeleteArticleTypeView() {
        View newView = ViewFactory.createView("ConfirmDeleteArticleView", this);
        Scene currentScene = new Scene(newView);
        myViews.put("ConfirmDeleteArticleView", currentScene);
		swapToView(currentScene);
    }
}