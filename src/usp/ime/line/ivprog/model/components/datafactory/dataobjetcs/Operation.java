package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import ilm.framework.assignment.model.DomainObject;
import usp.ime.line.ivprog.Services;

public class Operation extends Expression {
    private String             expressionAID = null;
    private String             expressionBID = null;
    public static final String STRING_CLASS  = "operation";
    
    public Operation(String name, String description) {
        super(name, description);
    }
    
    public Operation() {
        super(STRING_CLASS, STRING_CLASS);
    }
    
    /**
     * An expression is formed by two other expressions, A and B -> (A "operator" B). Observation: a variable is also an expression (a terminal symbol). This method returns the left side of an
     * expression. In other words, it returns expression A.
     * 
     * @return expressionA
     */
    public String getExpressionA() {
        return expressionAID;
    }
    
    /**
     * An expression is formed by two other expressions, A and B -> (A "operator" B). Observation: a variable is also an expression (a terminal symbol). This method sets the left side of an
     * expression. In other words, it sets expression A.
     * 
     * @param expA
     */
    public void setExpressionA(String expA) {
        expressionAID = expA;
    }
    
    /**
     * An expression is formed by two other expressions, A and B -> (A "operator" B). Observation: a variable is also an expression (a terminal symbol). This method returns the right side of an
     * expression. In other words, it returns expression B.
     * 
     * @return expressionB
     */
    public String getExpressionB() {
        return expressionBID;
    }
    
    /**
     * An expression is formed by two other expressions, A and B -> (A "operator" B). Observation: a variable is also an expression (a terminal symbol). This method sets the right side of an
     * expression. In other words, it sets expression B.
     * 
     * @param expressionA
     */
    public void setExpressionB(String expB) {
        expressionBID = expB;
    }
    
    /**
     * This method get the operation type. An operation might be arithmetical or logical.
     * 
     * @see {@literal} BuilderConstant for more information.
     * @return expressionType
     */
    public short getOperationType() {
        return expressionType;
    }
    
    public String toXML() {
        Expression expA = (Expression) Services.getService().getModelMapping().get(expressionAID);
        Expression expB = (Expression) Services.getService().getModelMapping().get(expressionBID);
        String str = "<dataobject class=\"operation\"><id>" + getUniqueID() + "</id>" + "<operationtype>" + expressionType + "<operationtype>" + "<expressionA>" + expA.toXML() + "</expressionA>"
                + "<expressionB>" + expB.toXML() + "</expressionB>" + "</dataobject>";
        return str;
    }
    
    public String toJavaString() {
        Expression expA = (Expression) Services.getService().getModelMapping().get(expressionAID);
        Expression expB = (Expression) Services.getService().getModelMapping().get(expressionBID);
        String str = "(";
        str += expA.toJavaString() + " " + getOperationTypeAsString() + " ";
        str += expB.toJavaString() + " )";
        return str;
    }
    
    private String getOperationTypeAsString() {
        switch (getOperationType()) {
        case Operation.EXPRESSION_OPERATION_ADDITION:
            return "+";
        case Operation.EXPRESSION_OPERATION_MULTIPLICATION:
            return "*";
        case Operation.EXPRESSION_OPERATION_SUBTRACTION:
            return "-";
        case Operation.EXPRESSION_OPERATION_DIVISION:
            return "/";
        case Operation.EXPRESSION_OPERATION_LES:
            return "<";
        case Operation.EXPRESSION_OPERATION_LEQ:
            return "<=";
        case Operation.EXPRESSION_OPERATION_EQU:
            return "==";
        case Operation.EXPRESSION_OPERATION_GEQ:
            return ">=";
        case Operation.EXPRESSION_OPERATION_GRE:
            return ">";
        case Operation.EXPRESSION_OPERATION_NEQ:
            return "!=";
        case Operation.EXPRESSION_OPERATION_AND:
            return "&&";
        case Operation.EXPRESSION_OPERATION_OR:
            return "||";
        case Operation.EXPRESSION_OPERATION_CONCAT:
            return "+";
        case Operation.EXPRESSION_OPERATION_INTDIV:
            return "%";
        }
        return "";
    }
    
    public boolean equals(DomainObject o) {
        return false;
    }
    
    public void removeExpression(String expression) {
        if (expressionAID != null && expressionAID.equals(expression) && "".equals(expressionAID)) {
            expressionAID = "";
        } else {
            expressionBID = "";
        }
    }
    
    public void updateParent(String lastExp, String newExp, String operationContext) {
        if (operationContext.equals("right")) {
            if (expressionBID.equals(lastExp) || expressionBID == "") {
                expressionBID = newExp;
            }
        } else {
            if (expressionAID.equals(lastExp) || expressionAID == "") {
                expressionAID = newExp;
            }
        }
    }
}
