package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import java.util.Vector;

public abstract class Collection extends DataObject {

	private String collectionName = "";
	private Vector referenceList = new Vector();
	private short collectionType = -1;

	/**
	 * This methods returns the collection name in the system.
	 * 
	 * @return collectionName
	 */
	public String getCollectionName() {
		return collectionName;
	}

	/**
	 * This method sets the collection name in the system.
	 * 
	 * @param name
	 */
	public void setCollectionName(String name) {
		collectionName = name;
	}

	/**
	 * This method gets the collection type. It might be int, double, String or
	 * boolean.
	 * 
	 * @return collectionType
	 */
	public short getCollectionType() {
		return collectionType;
	}

	/**
	 * This method sets the collection type. It might be int, double, String or
	 * boolean.
	 * 
	 * @return cType
	 */
	public void setCollectionType(short cType) {
		collectionType = cType;
	}

	/**
	 * Return the references list for this collection.
	 * 
	 * @return
	 */
	public Vector getReferenceList() {
		return referenceList;
	}

	/**
	 * Set the references list for this collection.
	 * 
	 * @param referenceList
	 */
	public void setReferenceList(Vector referenceList) {
		this.referenceList = referenceList;
	}

	/**
	 * Append a reference to the end of reference list.
	 * 
	 * @param ref
	 */
	public void addReferenceToTheList(Reference ref) {
		referenceList.add(ref);
	}

	/**
	 * Remove the specified reference from the list and return it.
	 * 
	 * @param ref
	 */
	public DataObject removeReferenceFromTheList(Reference ref) {
		referenceList.remove(ref);
		return ref;
	}
}
