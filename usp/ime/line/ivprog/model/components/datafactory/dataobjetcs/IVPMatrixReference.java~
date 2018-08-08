package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import ilm.framework.assignment.model.DomainObject;
import usp.ime.line.ivprog.model.utils.Services;

public class IVPMatrixReference extends Reference {
	private String referencedMatrixID = "";
	private String lineExpID = "";
	private String columnExpID = "";
	public static final String STRING_CLASS = "matrixreference";

	public IVPMatrixReference(String name, String description) {
		super(name, description);
	}

	/**
	 * Return the referenced matrix.
	 * 
	 * @return the referencedMatrix
	 */
	public String getReferencedMatrix() {
		return referencedMatrixID;
	}

	/**
	 * Set the referencedMatrix.
	 * 
	 * @param referencedMatrix
	 *            the referencedMatrix to set
	 */
	public void setReferencedMatrix(String referencedMatrix) {
		referencedMatrixID = referencedMatrix;
		IVPMatrix m = (IVPMatrix) Services.getService().getModelMapping().get(referencedMatrixID);
		setReferencedName(m.getCollectionName());
	}

	/**
	 * Return the expression that specifies the line in this reference.
	 * 
	 * @return the line
	 */
	public String getLineExpression() {
		return lineExpID;
	}

	/**
	 * Set the expression that specifies the line in this reference.
	 * 
	 * @param line
	 *            the line to set
	 */
	public void setLineExpression(String line) {
		this.lineExpID = line;
	}

	/**
	 * Return the expression that specifies the column in this reference.
	 * 
	 * @return the column
	 */
	public String getColumnExpression() {
		return columnExpID;
	}

	/**
	 * Set the expression that specifies the column in this reference.
	 * 
	 * @param columnExpID
	 *            the column to set
	 */
	public void setColumnExpression(String col) {
		columnExpID = col;
	}

	public String toXML() {
		Expression line = (Expression) Services.getService().getModelMapping().get(lineExpID);
		Expression column = (Expression) Services.getService().getModelMapping().get(columnExpID);
		String str = "<dataobject class\"ivpmatrixreference\">" + "<id>" + getUniqueID() + "</id>" + "<referencedname>" + referencedName
		        + "</referencedname>" + "<type>" + referencedType + "</type>" + "<lineexpression>" + line.toXML() + "</lineexpression>"
		        + "<columnexpression>" + column.toXML() + "</columnexpression>" + "</dataobject>";
		return str;
	}

	public String toJavaString() {
		return null;
	}

	public boolean equals(DomainObject o) {
		// TODO Auto-generated method stub
		return false;
	}
}
