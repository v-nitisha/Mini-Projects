

import java.io.Serializable;

@SuppressWarnings("serial")
public class Message implements Serializable{

    private String control;
    private String associatedData;
    
    public Message(String c,String a)
    {
        control = c;
        associatedData = a;
    }

	public String getControl() {
		return control;
	}

	public String getAssociatedData() {
		return associatedData;
	}
    
}