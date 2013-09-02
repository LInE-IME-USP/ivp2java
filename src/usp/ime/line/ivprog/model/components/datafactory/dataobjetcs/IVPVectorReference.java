package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

public class IVPVectorReference extends Reference {

	private IVPVector referencedVector = null;
	private Expression position = null;

	/**
	 * Return the referenced vector.
	 * 
	 * @return the referencedVector
	 */
	public IVPVector getReferencedVector() {
		return referencedVector;
	}

	/**
	 * Set the referenced vector.
	 * 
	 * @param referencedVec
	 *            the referencedVector to set
	 */
	public void setReferencedVector(IVPVector referencedVec) {
		referencedVector = referencedVec;
		setReferencedName(referencedVec.getCollectionName());
	}

	/**
	 * Return the expression that specifies the positions of this reference.
	 * 
	 * @return the position
	 */
	public Expression getPosition() {
		return position;
	}

	/**
	 * Set the expression that specifies the positions of this reference.
	 * 
	 * @param position
	 *            the position to set
	 */
	public void setPosition(Expression pos) {
		position = pos;
	}

	public String toXML() {
		String str = "<dataobject class=\"vectorreference\">" + "<id>"
				+ getUniqueID() + "</id>" + "<referencedname>" + referencedName
				+ "<referencedname>" + "<referencedtype>" + referenceType
				+ "</referencedtype>" + "<index>" + position.toXML()
				+ "</index>" + "</dataobject>";
		return str;
	}

	public String toJavaString() {
		return null;
	}
}
