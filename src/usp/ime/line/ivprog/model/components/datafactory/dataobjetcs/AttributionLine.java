package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

public class AttributionLine extends CodeComponent {

	private Reference leftVariable = null;
	private Expression rightExpression = null;
	private short leftVariableType = -1;

	/**
	 * @return the leftVariable
	 */
	public Reference getLeftVariable() {
		return leftVariable;
	}

	/**
	 * @param leftVariable
	 *            the leftVariable to set
	 */
	public void setLeftVariable(Reference leftVariable) {
		this.leftVariable = leftVariable;
	}

	/**
	 * @return the rightExpression
	 */
	public Expression getRightExpression() {
		return rightExpression;
	}

	/**
	 * @param rightExpression
	 *            the rightExpression to set
	 */
	public void setRightExpression(Expression rightExpression) {
		this.rightExpression = rightExpression;
	}

	/**
	 * @return the leftVariableType
	 */
	public short getLeftVariableType() {
		return leftVariableType;
	}

	/**
	 * @param leftVariableType
	 *            the leftVariableType to set
	 */
	public void setLeftVariableType(short leftVariableType) {
		this.leftVariableType = leftVariableType;
	}

	/**
	 * Removes the right side of the attribution line and return it.
	 */
	public DataObject removeRightExpression() {
		DataObject rightExp = rightExpression;
		rightExpression = null;
		return rightExp;
	}

	public String toXML() {
		String str = "<dataobject class=\"attline\">" + "<id>" + getUniqueID()
				+ "</id>" + "<variabletype>" + leftVariableType
				+ "</variabletype>" + "<left>" + leftVariable.toXML()
				+ "</left>" + "<right>" + rightExpression.toXML() + "</right>"
				+ "</dataobject>";
		return str;
	}

	public String toJavaString() {
		return null;
	}

}
