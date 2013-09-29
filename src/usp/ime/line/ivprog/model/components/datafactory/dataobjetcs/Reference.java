package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import usp.ime.line.ivprog.model.IVPConstants;

public abstract class Reference extends CodeComponent {

	protected String referencedName = "";
	protected short referenceType = -1;

	public Reference(String name, String description) {
		super(name, description);
	}
	/**
	 * Get the object referenced.
	 * 
	 * @return
	 */
	public String getReferencedName() {
		return referencedName;
	}

	/**
	 * Set the referenced object name.
	 * 
	 * @param refName
	 */
	public void setReferencedName(String refName) {
		referencedName = refName;
	}

	/**
	 * Return the reference type. It might be int, double, string or boolean.
	 * 
	 * @see IVPConstants
	 * @return the referenceType
	 */
	public short getReferenceType() {
		return referenceType;
	}

	/**
	 * Set the reference type.
	 * 
	 * @param referencedType
	 *            the referenceType to set
	 */
	public void setReferenceType(short refType) {
		referenceType = refType;
	}

	public abstract String toXML();

	public abstract String toJavaString();
}
