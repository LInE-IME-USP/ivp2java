package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import usp.ime.line.ivprog.model.utils.IVPConstants;

public abstract class Reference extends Expression {

	protected String referencedName = "";
	protected String referenceType = "-1";

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
	public String getReferenceType() {
		return referenceType;
	}

	/**
	 * Set the reference type.
	 * 
	 * @param referencedType
	 *            the referenceType to set
	 */
	public void setReferenceType(String refType) {
		referenceType = refType;
	}

	public abstract String toXML();

	public abstract String toJavaString();
}
