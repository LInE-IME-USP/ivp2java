package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

public class ReturnStatement extends CodeComponent {

	private Expression expressionToBeReturned = null;
	private short type = -1;

	/**
	 * Return the expression to be returned by the return statement.
	 * 
	 * @return
	 */
	public Expression getExpressionToBeReturned() {
		return expressionToBeReturned;
	}

	/**
	 * Set the expression to be returned by the return statement.
	 * 
	 * @param expressionToBeReturned
	 */
	public void setExpressionToBeReturned(Expression expressionToBeReturned) {
		this.expressionToBeReturned = expressionToBeReturned;
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
		String str = "<dataobject class=\"returnstatement\">" + "<id>"
				+ getUniqueID() + "</id>" + "<expression>"
				+ expressionToBeReturned.toXML() + "</expression>"
				+ "</dataobject>";
		return str;
	}

	public String toJavaString() {
		return null;
	}


}
