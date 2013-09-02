package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

public class Operation extends Expression {

	private Expression expressionA = null;
	private Expression expressionB = null;
	private short operationType = -1;

	/**
	 * An expression is formed by two other expressions, A and B -> (A
	 * "operator" B). Observation: a variable is also an expression (a terminal
	 * symbol). This method returns the left side of an expression. In other
	 * words, it returns expression A.
	 * 
	 * @return expressionA
	 */
	public Expression getExpressionA() {
		return expressionA;
	}

	/**
	 * An expression is formed by two other expressions, A and B -> (A
	 * "operator" B). Observation: a variable is also an expression (a terminal
	 * symbol). This method sets the left side of an expression. In other words,
	 * it sets expression A.
	 * 
	 * @param expA
	 */
	public void setExpressionA(Expression expA) {
		expressionA = expA;
	}

	/**
	 * An expression is formed by two other expressions, A and B -> (A
	 * "operator" B). Observation: a variable is also an expression (a terminal
	 * symbol). This method returns the right side of an expression. In other
	 * words, it returns expression B.
	 * @return expressionB
	 */
	public Expression getExpressionB() {
		return expressionB;
	}

	/**
	 * An expression is formed by two other expressions, A and B -> (A
	 * "operator" B). Observation: a variable is also an expression (a terminal
	 * symbol). This method sets the right side of an expression. In other
	 * words, it sets expression B.
	 * @param expressionA
	 */
	public void setExpressionB(Expression expB) {
		expressionB = expB;
	}

	/**
	 * This method get the operation type. An operation might be arithmetical or
	 * logical.
	 * @see {@literal} BuilderConstant for more information.
	 * @return expressionType
	 */
	public short getOperationType() {
		return operationType;
	}

	/**
	 * This method sets the operation type. An operation might be arithmetical
	 * or logical.
	 * @see {@literal} BuilderConstant for more information.
	 * @param oType
	 */
	public void setOperationType(short oType) {
		operationType = oType;
	}

	public String toXML() {
		String str = "<dataobject class=\"operation\"><id>" + getUniqueID()
				+ "</id>" + "<operationtype>" + operationType
				+ "<operationtype>" + "<expressionA>" + expressionA.toXML()
				+ "</expressionA>" + "<expressionB>" + expressionB.toXML()
				+ "</expressionB>" + "</dataobject>";
		return str;
	}

	public String toJavaString() {
		return null;
	}

}
