package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import java.util.Vector;

import usp.ime.line.ivprog.controller.Services;
import usp.ime.line.ivprog.model.ModelConstants;

public class Variable extends Expression {

	private String variableName = "";
	private short variableType = -1;
	private String variableValue = "";
	private Vector variableReferenceList;
	private String escopeID;

	public Variable(){
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
	 * @see ModelConstants
	 * @return type
	 */
	public short getVariableType() {
		return variableType;
	}

	/**
	 * Set the variable type
	 * @see ModelConstants
	 * @param varIntType
	 */
	public void setVariableType(short varIntType) {
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
			VariableReference varRef = (VariableReference) Services.getService().mapping().getObject((String)variableReferenceList.get(i));
			str += varRef.toXML();
		}
		str += "</referencelist></dataobject>";
		return str;
	}

	public String toJavaString() {
		return null;
	}
	
	public void setEscopeID(String uniqueID) {
		escopeID = uniqueID;
	}
	
	public String getEscopeID(){
		return escopeID;
	}

}
