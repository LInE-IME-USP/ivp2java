package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import ilm.framework.assignment.model.DomainObject;
import usp.ime.line.ivprog.Services;

public class AttributionLine extends CodeComponent {

	private String leftVariable = null;
	private String rightExpression = null;
	private short leftVariableType = -1;
	public static final String STRING_CLASS = "attline";

	public AttributionLine(String name, String description) {
		super(name, description);
	}
	
	/**
	 * @return the leftVariable
	 */
	public String getLeftVariableID() {
		return leftVariable;
	}

	/**
	 * @param leftVariable
	 *            the leftVariable to set
	 */
	public void setLeftVariableID(String leftVariable) {
		this.leftVariable = leftVariable;
	}

	/**
	 * @return the rightExpression
	 */
	public String getRightExpressionID() {
		return rightExpression;
	}

	/**
	 * @param rightExpression the rightExpression to set
	 */
	public void setRightExpression(String rightExpression) {
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
	public String removeRightExpression() {
		String rightExpID = rightExpression;
		rightExpression = "";
		return rightExpID;
	}

	public String toXML() {
		Variable varLeft = (Variable) Services.getService().getModelMapping().get(leftVariable);
		Expression rightExp = (Expression) Services.getService().getModelMapping().get(rightExpression);
		String str = "<dataobject class=\"attline\">" + "<id>" + getUniqueID()
				+ "</id>" + "<variabletype>" + leftVariableType
				+ "</variabletype>" + "<left>" + varLeft.toXML()
				+ "</left>" + "<right>" + rightExp.toXML() + "</right>"
				+ "</dataobject>";
		return str;
	}

	public String toJavaString() {
		return null;
	}

	@Override
	public boolean equals(DomainObject o) {
		// TODO Auto-generated method stub
		return false;
	}

}
