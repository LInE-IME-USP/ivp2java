package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

public class IVPMatrixReference extends Reference {

	private IVPMatrix referencedMatrix = null;
	private Expression line = null;
	private Expression column = null;

	/**
	 * Return the referenced matrix.
	 * 
	 * @return the referencedMatrix
	 */
	public IVPMatrix getReferencedMatrix() {
		return referencedMatrix;
	}

	/**
	 * Set the referencedMatrix.
	 * 
	 * @param referencedMatrix
	 *            the referencedMatrix to set
	 */
	public void setReferencedMatrix(IVPMatrix referencedMatrix) {
		this.referencedMatrix = referencedMatrix;
		setReferencedName(referencedMatrix.getCollectionName());
	}

	/**
	 * Return the expression that specifies the line in this reference.
	 * 
	 * @return the line
	 */
	public Expression getLineExpression() {
		return line;
	}

	/**
	 * Set the expression that specifies the line in this reference.
	 * 
	 * @param line
	 *            the line to set
	 */
	public void setLineExpression(Expression line) {
		this.line = line;
	}

	/**
	 * Return the expression that specifies the column in this reference.
	 * 
	 * @return the column
	 */
	public Expression getColumnExpression() {
		return column;
	}

	/**
	 * Set the expression that specifies the column in this reference.
	 * 
	 * @param column
	 *            the column to set
	 */
	public void setColumnExpression(Expression col) {
		column = col;
	}

	public String toXML() {
		String str = "<dataobject class\"ivpmatrixreference\">" + "<id>"
				+ getUniqueID() + "</id>" + "<referencedname>" + referencedName
				+ "</referencedname>" + "<type>" + referenceType + "</type>"
				+ "<lineexpression>" + line.toXML() + "</lineexpression>"
				+ "<columnexpression>" + column.toXML() + "</columnexpression>"
				+ "</dataobject>";
		return str;
	}

	public String toJavaString() {
		return null;
	}
}
