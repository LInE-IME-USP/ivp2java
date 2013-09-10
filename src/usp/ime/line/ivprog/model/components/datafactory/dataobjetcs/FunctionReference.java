package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import java.util.Vector;

import usp.ime.line.ivprog.controller.Services;

public class FunctionReference extends Reference {

	private String referencedFunctionID = null;
	private Vector parameterList = new Vector();

	/**
	 * Set the referenced function.
	 * @param f
	 */
	public void setReferencedFunction(String functionID) {
		if (functionID != null) {
			referencedFunctionID = functionID;
			Function f = (Function) Services.getService().getModelMapping().get(functionID);
			setReferencedName(f.getFunctionName());
			setReferenceType(f.getReturnType());
		}
	}

	/**
	 * Return the referenced function.
	 * @return
	 */
	public String getReferencedFunction() {
		return referencedFunctionID;
	}

	/**
	 * Set the entire parameter list.
	 * 
	 * @param paramList
	 */
	public void setParameterList(Vector paramList) {
		parameterList = paramList;
	}

	/**
	 * Append the specified parameter to the end of the list.
	 * 
	 * @param ref
	 */
	public void addParameterToTheListAtIndex(int index, String refID) {
		parameterList.add(index, refID);
	}

	/**
	 * Remove the specified parameter from the list and return it.
	 * @param ref
	 */
	public String removeParameterFromTheIndex(int index) {
		String paramRefID = (String) parameterList.get(index);
		parameterList.remove(index);
		return paramRefID;
	}

	/**
	 * Remove the parameter with the specified name from the list and return it.
	 * @param name
	 */
	public String removeParameterWithName(String name) {
		String paramID = "";
		for (int i = 0; i < parameterList.size(); i++) {
			paramID = (String) parameterList.get(i);
			VariableReference param = (VariableReference) Services.getService().getModelMapping().get(paramID);
			if (param.getReferencedName().equals(name)) {
				paramID = (String) parameterList.get(i);
				parameterList.remove(i);
				return paramID;
			}
		}
		return paramID;
	}

	public String toXML() {
		String str = "<dataobject class=\"functionreference\">" + "<id>"
				+ getUniqueID() + "</id>" + "<type>" + getReferenceType()
				+ "</type>" + "<referencedname>" + getReferencedName()
				+ "</referencedname>" + "<parameterlist>";
		for (int i = 0; i < parameterList.size(); i++) {
			str += ((DataObject) parameterList.get(i)).toXML();
		}
		str += "</parameterlist></dataobject>";
		return str;
	}

	public String toJavaString() {
		return null;
	}
}
