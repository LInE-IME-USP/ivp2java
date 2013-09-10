package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

public abstract class DataObject {

	private String uniqueID;

	/**
	 * This method returns the uniqueID of a DomainObject.
	 * @return uniqueID
	 */
	public String getUniqueID() {
		return uniqueID;
	}

	/**
	 * This method sets the uniqueID of a DomainObject. This id must be set a
	 * single time during object creation.
	 * @param id
	 */
	public void setUniqueID(String id) {
		uniqueID = id;
	}

	/**
	 * This method returns a String containing the XML notation for a Data
	 * Object.
	 * @return
	 */
	public abstract String toXML();

	/**
	 * This method returns a String containing the Java notation for a Data
	 * Object.
	 * @return
	 */
	public abstract String toJavaString();

}
