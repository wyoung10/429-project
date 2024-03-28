package userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

//==============================================================================
public class ColorTableModel
{
    private final SimpleStringProperty id;
    private final SimpleStringProperty description;
	private final SimpleStringProperty alphaCode;

	//----------------------------------------------------------------------------
	public ColorTableModel(Vector<String> colorData)
	{
        id = new SimpleStringProperty(colorData.elementAt(0));
		description =  new SimpleStringProperty(colorData.elementAt(1));
		alphaCode =  new SimpleStringProperty(colorData.elementAt(2));
	}

    //----------------------------------------------------------------------------
	public String getId() {
        return id.get();
    }

	//----------------------------------------------------------------------------
    public void setId(String id) {
        description.set(id);
    }

	//----------------------------------------------------------------------------
	public String getDescription() {
        return description.get();
    }

	//----------------------------------------------------------------------------
    public void setDescription(String id) {
        description.set(id);
    }

    //----------------------------------------------------------------------------
    public String getAlphaCode() {
        return alphaCode.get();
    }

    //----------------------------------------------------------------------------
    public void setAlphaCode(String athr) {
        alphaCode.set(athr);
    }
}
