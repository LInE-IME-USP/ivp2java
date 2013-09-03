package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import java.util.Vector;

public class FunctionReference extends Reference {

	private Function referencedFunction = null;
	private Vector parameterList = new Vector();

	/**
	 * Set the referenced function.
	 * 
	 * @param f
	 */
	public void setReferencedFunction(Function f) {
		if(f != null){
		referencedFunction = f;
		setReferencedName(f.getFunctionName());
		setReferenceType(f.getReturnType());
		}
	}

	/**
	 * Return the referenced function.
	 * 
	 * @return
	 */
	public Function getReferencedFunction() {
		return referencedFunction;
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
	public void addParameterToTheListAtIndex(int index, Reference ref) {
		parameterList.add(index, ref);
	}

	/**
	 * Remove the specified parameter from the list and return it.
	 * 
	 * @param ref
	 */
	public Reference removeParameterFromTheIndex(int index) {
		Reference reference = (Reference) parameterList.get(index);
		parameterList.remove(index);
		return reference;
	}

	/**
	 * Remove the parameter with the specified name from the list and return it.
	 * 
	 * @param name
	 */
	public DataObject removeParameterWithName(String name) {
		DataObject variable = null;
		for (int i = 0; i < parameterList.size(); i++) {
			if (((Reference) parameterList.get(i)).getReferencedName().equals(
					name)) {
				variable = (DataObject) parameterList.get(i);
				parameterList.remove(i);
			}
		}
		return variable;
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
