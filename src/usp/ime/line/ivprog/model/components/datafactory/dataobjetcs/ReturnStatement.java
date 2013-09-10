package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import usp.ime.line.ivprog.controller.Services;

public class ReturnStatement extends CodeComponent {

	private String expressionToBeReturnedID = null;
	private short type = -1;

	/**
	 * Return the expression to be returned by the return statement.
	 * @return
	 */
	public String getExpressionToBeReturned() {
		return expressionToBeReturnedID;
	}

	/**
	 * Set the expression to be returned by the return statement.
	 * @param expressionToBeReturned
	 */
	public void setExpressionToBeReturned(String expressionToBeReturned) {
		this.expressionToBeReturnedID = expressionToBeReturned;
	}

	/**
	 * Get returnStatement type.
	 * @return
	 */
	public short getType() {
		return type;
	}

	/**
	 * Set returnStatement type.
	 * @param type
	 */
	public void setType(short type) {
		this.type = type;
	}

	public String toXML() {
		Expression expToBeReturned = (Expression) Services.getService().mapping().getObject(expressionToBeReturnedID);
		String str = "<dataobject class=\"returnstatement\">" + "<id>"
				+ getUniqueID() + "</id>" + "<expression>"
				+ expToBeReturned.toXML() + "</expression>"
				+ "</dataobject>";
		return str;
	}

	public String toJavaString() {
		return null;
	}

}
