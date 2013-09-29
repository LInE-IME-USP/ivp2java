package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import ilm.framework.assignment.model.DomainObject;

public abstract class DataObject extends DomainObject {

	public DataObject(String name, String description) {
		super(name, description);
	}

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
