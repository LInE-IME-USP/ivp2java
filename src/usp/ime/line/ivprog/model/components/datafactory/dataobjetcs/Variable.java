package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import ilm.framework.assignment.model.DomainObject;

import java.util.Vector;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.model.utils.IVPConstants;

public class Variable extends Expression {

	public static final String STRING_CLASS = "variable";
	private String variableName = "";
	private String variableType = "-1";
	private String variableValue = "";
	private Vector variableReferenceList;
	public static String TYPE_INTEGER = "int";
	public static String TYPE_DOUBLE = "double";
	public static String TYPE_STRING = "String";
	public static String TYPE_BOOLEAN = "boolean";
	
	public Variable(String name, String description){
		super(name, description);
		variableReferenceList = new Vector();
	}
	
	/**
	 * Return the variable name.
	 * @return name
	 */
	public String getVariableName() {
		return variableName;
	}

	/**
	 * Set the variable name.
	 * @param name
	 */
	public void setVariableName(String name) {
		variableName = name;
	}

	/**
	 * Return the variable type.
	 * @see IVPConstants
	 * @return type
	 */
	public String getVariableType() {
		return variableType;
	}

	/**
	 * Set the variable type
	 * @see IVPConstants
	 * @param varIntType
	 */
	public void setVariableType(String varIntType) {
		variableType = varIntType;
	}

	/**
	 * Return the variable value as a String.
	 * @return value
	 */
	public String getVariableValue() {
		return variableValue;
	}

	/**
	 * Set the variable value. It might be in String format.
	 * @param value
	 */
	public void setVariableValue(String value) {
		variableValue = value;
	}

	/**
	 * Add a reference for this variable at the list. It's needed for variable
	 * deletion.
	 * @param ref
	 */
	public void addVariableReference(String refID) {
		variableReferenceList.add(refID);
	}

	/**
	 * Remove a reference to this variable and return it.
	 * @param ref
	 */
	public String removeVariableReference(String refID) {
		variableReferenceList.remove(refID);
		return refID;
	}

	/**
	 * Return the variable reference list.
	 * @return variableReferenceList
	 */
	public Vector getVariableReferenceList() {
		return variableReferenceList;
	}

	/**
	 * Set the variable reference list.
	 * @param varList
	 */
	public void setVariableReferenceList(Vector varList) {
		variableReferenceList = varList;
	}

	public String toXML() {
		String str = "<dataobject class=\"variable\">" + "<id>" + getUniqueID()
				+ "</id>" + "<name>" + variableName + "</name>" + "<type>"
				+ variableType + "</type>" + "<value>" + variableValue
				+ "</value>" + "<referencelist>";
		for (int i = 0; i < variableReferenceList.size(); i++) {
			VariableReference varRef = (VariableReference) Services.getService().getModelMapping().get((String)variableReferenceList.get(i));
			str += varRef.toXML();
		}
		str += "</referencelist></dataobject>";
		return str;
	}

	public String toJavaString() {
		return null;
	}
	
	public boolean equals(DomainObject o) {
		return false;
	}


}
