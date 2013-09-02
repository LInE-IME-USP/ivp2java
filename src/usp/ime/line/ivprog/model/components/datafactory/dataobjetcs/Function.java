package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import java.util.Vector;

import usp.ime.line.ivprog.model.ModelConstants;

public class Function extends CodeComposite {

	private String functionName = "";
	private short returnType = -1;
	private Vector parameterList = new Vector();
	private Vector localVariableList = new Vector();
	private Vector referenceList = new Vector();
	private boolean isMainFunction = false;

	/**
	 * Return the function name.
	 * 
	 * @return the name
	 */
	public String getFunctionName() {
		return functionName;
	}

	/**
	 * Set the function's name.
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setFunctionName(String name) {
		functionName = name;
	}

	/**
	 * Return the parameters list for this function.
	 * 
	 * @return the parameterList
	 */
	public Vector getParameterList() {
		return parameterList;
	}

	/**
	 * Set the parameters list for this function.
	 * 
	 * @param paramList
	 *            the parameterList to set
	 */
	public void setParameterList(Vector paramList) {
		parameterList = paramList;
	}

	/**
	 * Return the local variables list.
	 * 
	 * @return the localVariableList
	 */
	public Vector getLocalVariableList() {
		return localVariableList;
	}

	/**
	 * Set the local variables list.
	 * 
	 * @param localVarList
	 *            the localVariableList to set
	 */
	public void setLocalVariableList(Vector localVarList) {
		localVariableList = localVarList;
	}

	/**
	 * @return the isMainFunction
	 */
	public boolean isMainFunction() {
		return isMainFunction;
	}

	/**
	 * @param isMainFunction
	 *            the isMainFunction to set
	 */
	public void setMainFunction(boolean isMain) {
		isMainFunction = isMain;
	}

	/**
	 * Return the return type of this function.
	 * 
	 * @see ModelConstants
	 * @return the returnType
	 */
	public short getReturnType() {
		return returnType;
	}

	/**
	 * Set the return type of this function.
	 * 
	 * @see ModelConstants
	 * @param rType
	 *            the returnType to set
	 */
	public void setReturnType(short rType) {
		returnType = rType;
	}

	/**
	 * Add a local variable to the list.
	 * 
	 * @param var
	 */
	public void addLocalVariable(Variable var) {
		localVariableList.add(var);
	}

	/**
	 * Remove a local variable with the specified name from the list and return
	 * it.
	 * 
	 * @param name
	 */
	public Variable removeLocalVariableFromIndex(int index) {
		Variable variable = null;
		variable = (Variable) localVariableList.get(index);
		return variable;
	}

	/**
	 * Get the index of the variable with the specified name.
	 * 
	 * @param name
	 * @return
	 */
	public int getIndexForVariableWithName(String name){
		Variable variable = null;
		for (int i = 0; i < localVariableList.size(); i++) {
			if (((Variable) localVariableList.get(i)).getVariableName()
					.equals(name)) {
				variable = (Variable) localVariableList.get(i);
			}
		}
		return localVariableList.indexOf(variable);
	}
	
	/**
	 * Add a parameter to the parameters list.
	 * 
	 * @param var
	 */
	public void addParameter(Variable var) {
		parameterList.add(var);
	}

	/**
	 * Get the index of the parameter with the specified name.
	 * 
	 * @param name
	 * @return
	 */
	public int getIndexForParameterWithName(String name) {
		Variable variable = null;
		for (int i = 0; i < parameterList.size(); i++) {
			if (((Variable) parameterList.get(i)).getVariableName()
					.equals(name)) {
				variable = (Variable) parameterList.get(i);
			}
		}
		return parameterList.indexOf(variable);
	}

	/**
	 * Remove a parameter with the specified name and return it.
	 * 
	 * @param name
	 */
	public Variable removeParameterAtIndex(int index) {
		Variable variable = null;
		variable = (Variable) parameterList.get(index);
		return variable;
	}

	/**
	 * Return the list of references to this function.
	 * 
	 * @return
	 */
	public Vector getReferenceList() {
		return referenceList;
	}

	/**
	 * Set the list of references to this function.
	 * 
	 * @param refList
	 */
	public void setReferenceList(Vector refList) {
		referenceList = refList;
	}

	/**
	 * Append a reference to this function at the end of the list.
	 * 
	 * @param ref
	 */
	public void addReferenceToTheList(FunctionReference ref) {
		referenceList.add(ref);
	}

	/**
	 * Remove the specified reference to this function from the list.
	 * 
	 * @param ref
	 */
	public void removeFunctionReference(FunctionReference ref) {
		referenceList.remove(ref);
	}

	public String toXML() {
		String str = "<dataobject class=\"function\">" + "<id>" + getUniqueID()
				+ "</id>" + "<name>" + functionName + "</name>" + "<type>"
				+ returnType + "</type>" + "<isMain>" + isMainFunction
				+ "</isMain>" + "<parameterlist>";
		for (int i = 0; i < parameterList.size(); i++) {
			str += ((DataObject) parameterList.get(i)).toXML();
		}
		str += "</parameterlist><variablelist>";
		for (int i = 0; i < localVariableList.size(); i++) {
			str += ((DataObject) localVariableList.get(i)).toXML();
		}
		str += "</variablelist><referencelist>";
		for (int i = 0; i < referenceList.size(); i++) {
			str += ((DataObject) referenceList.get(i)).toXML();
		}
		str += "</referencelist><children>";
		for (int i = 0; i < getChildrenList().size(); i++) {
			str += ((DataObject) getChildrenList().get(i)).toXML();
		}
		str += "</children></dataobject>";
		return str;
	}

	public String toJavaString() {
		return null;
	}

}
