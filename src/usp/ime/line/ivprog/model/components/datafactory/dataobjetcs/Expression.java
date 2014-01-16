package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

public abstract class Expression extends DataObject {
		
	public static final short EXPRESSION_VARIABLE = 0;
	public static final short EXPRESSION_OPERATION_ADDITION = 1;
	public static final short EXPRESSION_OPERATION_SUBTRACTION = 2;
	public static final short EXPRESSION_OPERATION_DIVISION = 3;
	public static final short EXPRESSION_OPERATION_MULTIPLICATION = 4;
	public static final short EXPRESSION_OPERATION_AND = 5;
	public static final short EXPRESSION_OPERATION_OR = 6;
	public static final short EXPRESSION_OPERATION_LEQ = 7;
	public static final short EXPRESSION_OPERATION_LES = 8;
	public static final short EXPRESSION_OPERATION_EQU = 9;
	public static final short EXPRESSION_OPERATION_NEQ = 10;
	public static final short EXPRESSION_OPERATION_GEQ = 11;
	public static final short EXPRESSION_OPERATION_GRE = 12;
	public static final short EXPRESSION_INTEGER = 13;
	public static final short EXPRESSION_DOUBLE = 14;
	public static final short EXPRESSION_STRING = 15;
	
	protected short expressionType = -1;
	
	public Expression(String name, String description) {
		super(name, description);
	}
	
	public short getExpressionType(){
		return expressionType;
	}
	
	public void setExpressionType(short type){
		expressionType = type;
	}
	
}