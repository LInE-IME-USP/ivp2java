package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import java.util.Vector;

import usp.ime.line.ivprog.model.ModelConstants;
import usp.ime.line.ivprog.model.utils.IVPObservableMap;

public class Function extends CodeComposite {

	private String functionName = "";
	private short returnType = -1;
	private IVPObservableMap parameters;
	private IVPObservableMap localVariables;
	private int variableID = 0;
	private Vector references;
	private boolean isMainFunction = false;

	public Function(){
		parameters = new IVPObservableMap();
		localVariables = new IVPObservableMap();
		references = new Vector();
	}
	
	/**
	 * Returns the function name.
	 * @return the name
	 */
	public String getFunctionName() {
		return functionName;
	}

	/**
	 * Set the function's name.
	 * @param name the name to set
	 */
	public void setFunctionName(String name) {
		functionName = name;
	}

	/**
	 * Return the parameters map for this function.
	 * @return the parameters map
	 */
	public IVPObservableMap getParameterMap() {
		return parameters;
	}

	/**
	 * Set the parameters list for this function.
	 * @param paramMap the parameter map to set
	 */
	public void setParameterMap(IVPObservableMap paramMap) {
		parameters = paramMap;
	}

	/**
	 * Return the local variables list.
	 * @return the localVariableList
	 */
	public IVPObservableMap getLocalVariableMap() {
		return localVariables;
	}

	/**
	 * Set the local variables list.
	 * @param localVarMap the localVariableList to set
	 */
	public void setLocalVariableMap(IVPObservableMap localVarMap) {
		localVariables = localVarMap;
	}

	/**
	 * @return the isMainFunction
	 */
	public boolean isMainFunction() {
		return isMainFunction;
	}

	/**
	 * @param isMainFunction the isMainFunction to set
	 */
	public void setMainFunction(boolean isMain) {
		isMainFunction = isMain;
	}

	/**
	 * Return the return type of this function.
	 * @see ModelConstants
	 * @return the returnType
	 */
	public short getReturnType() {
		return returnType;
	}

	/**
	 * Set the return type of this function.
	 * @see ModelConstants
	 * @param rType the returnType to set
	 */
	public void setReturnType(short rType) {
		returnType = rType;
	}

	/**
	 * Add a local variable to the list.
	 * @param var
	 */
	public void addLocalVariable(Variable var) {
		variableID++;
		localVariables.put(var.getVariableName(), var);
	}

	/**
	 * Remove a local variable with the specified name from the list and return
	 * it.
	 * @param name
	 */
	public Variable removeLocalVariable(String name) {
		Variable variable = (Variable) localVariables.remove(name);
		return variable;
	}

	/**
	 * Add a parameter to the parameters list.
	 * @param var
	 */
	public void addParameter(Variable var) {
		parameters.put(var.getVariableName(), var);
	}

	/**
	 * Remove a parameter with the specified name and return it.
	 * @param name
	 */
	public Variable removeParameter(String name) {
		Variable variable = (Variable) parameters.remove(name);
		return variable;
	}

	/**
	 * Return the list of references to this function.
	 * @return
	 */
	public Vector getReferenceList() {
		return references;
	}

	/**
	 * Set the list of references to this function.
	 * @param refList
	 */
	public void setReferenceList(Vector refList) {
		references = refList;
	}

	/**
	 * Append a reference to this function at the end of the list.
	 * @param ref
	 */
	public void addReferenceToTheList(FunctionReference ref) {
		references.add(ref);
	}

	/**
	 * Remove the specified reference to this function from the list.
	 * @param ref
	 */
	public void removeFunctionReference(FunctionReference ref) {
		references.remove(ref);
	}

	public String toXML() {
		String str = "<dataobject class=\"function\">" + "<id>" + getUniqueID()
				+ "</id>" + "<name>" + functionName + "</name>" + "<type>"
				+ returnType + "</type>" + "<isMain>" + isMainFunction
				+ "</isMain>" + "<parameterlist>";
		Vector v1 = parameters.toVector();
		Vector v2 = localVariables.toVector();
		for (int i = 0; i < v1.size(); i++) {
			str += ((DataObject) v1.get(i)).toXML();
		}
		str += "</parameterlist><variablelist>";
		for (int i = 0; i < v2.size(); i++) {
			str += ((DataObject) v2.get(i)).toXML();
		}
		str += "</variablelist><referencelist>";
		for (int i = 0; i < references.size(); i++) {
			str += ((DataObject) references.get(i)).toXML();
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

	//Used when a new variable is generated
	public int getVariableID() {
		return variableID;
	}

}
