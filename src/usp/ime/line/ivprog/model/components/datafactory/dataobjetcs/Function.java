package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import ilm.framework.assignment.model.DomainObject;

import java.util.Vector;

import usp.ime.line.ivprog.model.utils.IVPConstants;
import usp.ime.line.ivprog.model.utils.IVPVariableMap;
import usp.ime.line.ivprog.model.utils.Services;

public class Function extends CodeComposite {
	
	private String functionName = "";
	private String returnType = "-1";
	private IVPVariableMap parameters;
	private IVPVariableMap localVariables;
	private int variableCount = 0;
	private Vector references;
	private boolean isMainFunction = false;
	public static final String STRING_CLASS = "function";

	public Function(String name, String description) {
		super(name, description);
		parameters = new IVPVariableMap(false);
		localVariables = new IVPVariableMap(true);
		references = new Vector();
	}

	/**
	 * Returns the function name.
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
	 * Return the parameters map for this function.
	 * 
	 * @return the parameters map
	 */
	public IVPVariableMap getParameterMap() {
		return parameters;
	}

	/**
	 * Set the parameters list for this function.
	 * 
	 * @param paramMap
	 *            the parameter map to set
	 */
	public void setParameterMap(IVPVariableMap paramMap) {
		parameters = paramMap;
	}

	/**
	 * Return the local variables list.
	 * 
	 * @return the localVariableList
	 */
	public IVPVariableMap getLocalVariableMap() {
		return localVariables;
	}

	/**
	 * Set the local variables list.
	 * 
	 * @param localVarMap
	 *            the localVariableList to set
	 */
	public void setLocalVariableMap(IVPVariableMap localVarMap) {
		localVariables = localVarMap;
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
	 * @see IVPConstants
	 * @return the returnType
	 */
	public String getReturnType() {
		return returnType;
	}

	/**
	 * Set the return type of this function.
	 * 
	 * @see IVPConstants
	 * @param funcReturnVoid
	 *            the returnType to set
	 */
	public void setReturnType(String funcReturnVoid) {
		returnType = funcReturnVoid;
	}

	/**
	 * Add a local variable to the list.
	 * 
	 * @param var
	 */
	public void addLocalVariable(String varID) {
		variableCount++;
		localVariables.put(varID, varID);
	}

	/**
	 * Remove a local variable with the specified name from the list and return
	 * it.
	 * 
	 * @param id
	 */
	public String removeLocalVariable(String varID) {
		String variable = (String) localVariables.remove(varID);
		return variable;
	}

	/**
	 * Add a parameter to the parameters list.
	 * 
	 * @param var
	 */
	public void addParameter(String paramID) {
		parameters.put(paramID, paramID);
	}

	/**
	 * Remove a parameter with the specified id and return it.
	 * 
	 * @param name
	 */
	public String removeParameter(String paramID) {
		String param = (String) parameters.remove(paramID);
		return param;
	}

	/**
	 * Return the list of references to this function.
	 * 
	 * @return
	 */
	public Vector getReferenceList() {
		return references;
	}

	/**
	 * Set the list of references to this function.
	 * 
	 * @param refList
	 */
	public void setReferenceList(Vector refList) {
		references = refList;
	}

	// DÚVIDA SE PRECISAREI DISSO OU NÃO... VOU TENTAR NÃO DEPENDER DISSO...
	// PRECISAREI CRIAR UM OUVIDOR PRA QUAIS FUNÇÕES EXISTEM.
	/**
	 * Append a reference to this function at the end of the list.
	 * 
	 * @param ref
	 */
	public void addReferenceToTheList(String functionReference) {
		references.add(functionReference);
	}

	/**
	 * Remove the specified reference to this function from the list.
	 * 
	 * @param ref
	 */
	public void removeFunctionReference(FunctionReference ref) {
		references.remove(ref);
	}

	public String toXML() {
		String str = "<dataobject class=\"function\">" + "<id>" + getUniqueID() + "</id>" + "<name>" + functionName + "</name>" + "<type>"
		        + returnType + "</type>" + "<isMain>" + isMainFunction + "</isMain>" + "<parameterlist>";
		Vector v1 = parameters.toVector();
		Vector v2 = localVariables.toVector();
		for (int i = 0; i < v1.size(); i++) {
			Variable param = (Variable) Services.getService().getModelMapping().get((String) v1.get(i));
			str += param.toXML();
		}
		str += "</parameterlist><variablelist>";
		for (int i = 0; i < v2.size(); i++) {
			Variable var = (Variable) Services.getService().getModelMapping().get((String) v2.get(i));
			str += var.toXML();
		}
		str += "</variablelist><referencelist>";
		for (int i = 0; i < references.size(); i++) {
			str += ((DataObject) references.get(i)).toXML();
		}
		str += "</referencelist><children>";
		for (int i = 0; i < getChildrenList().size(); i++) {
			CodeComposite child = (CodeComposite) Services.getService().getModelMapping().get((String) getChildrenList().get(i));
			str += child.toXML();
		}
		str += "</children></dataobject>";
		return str;
	}

	public String toJavaString() {
		// ---------------------------------------------------------- adding
		// header
		String str = "public " + getReturnType() + " " + getFunctionName() + " ( ";
		// ---------------------------------------------------------- adding
		// parameters
		Vector paramList = parameters.toVector();
		for (int i = 0; i < paramList.size(); i++) {
			Variable v = ((Variable) Services.getService().getModelMapping().get(paramList.get(i)));
			str += v.toJavaString();
		}
		str += ") {\n";
		// ---------------------------------------------------------- converting
		// local variables
		Vector varList = localVariables.toVector();
		for (int i = 0; i < varList.size(); i++) {
			Variable v = ((Variable) Services.getService().getModelMapping().get(varList.get(i)));
			str += v.toJavaString();
		}
		str += "\n";
		Vector children = getChildrenList();
		for (int i = 0; i < children.size(); i++) {
			str += ((CodeComponent) Services.getService().getModelMapping().get(children.get(i))).toJavaString() + "\n";
		}
		str += " } " + "\n";
		return str;
	}

	// Used when a new variable is generated
	public int getVariableCount() {
		return variableCount;
	}

	public boolean equals(DomainObject o) {
		return false;
	}

	public String getScopeID() {
		return getUniqueID();
	}

	public void updateParent(String lastExp, String newExp, String operationContext) {
	}
}
