package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

public class AskUser extends CodeComponent {

	private String message = "";
	private short dataType = -1;

	/**
	 * Return the message that will be shown to user.
	 * 
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Set the message that will be shown to user.
	 * 
	 * @return
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Return the type of data that the question is looking for.
	 * 
	 * @return
	 */
	public short getDataType() {
		return dataType;
	}

	/**
	 * Set the type of data that the question is looking for.
	 * 
	 * @return
	 */
	public void setDataType(short dataType) {
		this.dataType = dataType;
	}

	public String toXML() {
		String str = "<dataobject class=\"askuser\">" + "<id>" + getUniqueID()
				+ "</id>" + "<datatype>" + dataType + "</datatype>"
				+ "<message>" + message + "</message>" + "</dataobject>";
		return str;
	}

	public String toJavaString() {
		return null;
	}
}
