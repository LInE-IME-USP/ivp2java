package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import java.util.Vector;

import usp.ime.line.ivprog.model.ModelConstants;

public class Variable extends Expression {
	
	private String variableName = "";
	private short variableType = -1;
	private String variableValue = "";
	private Vector variableReferenceList = new Vector();

	/**
	 * Return the variable name.
	 * 
	 * @return name
	 */
	public String getVariableName() {
		return variableName;
	}

	/**
	 * Set the variable name.
	 * 
	 * @param name
	 */
	public void setVariableName(String name) {
		variableName = name;
	}

	/**
	 * Return the variable type.
	 * 
	 * @see ModelConstants
	 * @return type
	 */
	public short getVariableType() {
		return variableType;
	}

	/**
	 * Set the variable type
	 * 
	 * @see ModelConstants
	 * @param varIntType
	 */
	public void setVariableType(short varIntType) {
		variableType = varIntType;
	}

	/**
	 * Return the variable value as a String.
	 * 
	 * @return value
	 */
	public String getVariableValue() {
		return variableValue;
	}

	/**
	 * Set the variable value. It might be in String format.
	 * 
	 * @param value
	 */
	public void setVariableValue(String value) {
		variableValue = value;
	}

	/**
	 * Add a reference for this variable at the list. It's needed for variable
	 * deletion.
	 * 
	 * @param ref
	 */
	public void addVariableReference(VariableReference ref) {
		variableReferenceList.add(ref);
	}

	/**
	 * Remove a reference to this variable and return it.
	 * 
	 * @param ref
	 */
	public DataObject removeVariableReference(VariableReference ref) {
		variableReferenceList.remove(ref);
		return ref;
	}

	/**
	 * Return the variable reference list.
	 * 
	 * @return variableReferenceList
	 */
	public Vector getVariableReferenceList() {
		return variableReferenceList;
	}

	/**
	 * Set the variable reference list.
	 * 
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
			str += ((DataObject) variableReferenceList.get(i)).toXML();
		}
		str += "</referencelist></dataobject>";
		return str;
	}

	public String toJavaString() {
		// TODO Auto-generated method stub
		return null;
	}

}
