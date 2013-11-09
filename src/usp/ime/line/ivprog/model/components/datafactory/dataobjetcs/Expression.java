package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

public abstract class Expression extends DataObject {
		
	public static final short EXPRESSION_VARIABLE = 5;
	public static final short EXPRESSION_OPERATION_ADDITION = 0;
	public static final short EXPRESSION_OPERATION_SUBTRACTION = 1;
	public static final short EXPRESSION_OPERATION_DIVISION = 2;
	public static final short EXPRESSION_OPERATION_MULTIPLICATION = 3;
	public static final short EXPRESSION_OPERATION_AND = 4;
	public static final short EXPRESSION_OPERATION_OR = 5;
	
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