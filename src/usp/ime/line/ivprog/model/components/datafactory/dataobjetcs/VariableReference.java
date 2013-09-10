package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import usp.ime.line.ivprog.controller.Services;

public class VariableReference extends Reference {

	private String referencedVariableID = null;

	/**
	 * Return the referenced variable.
	 * @return
	 */
	public String getReferencedVariable() {
		return referencedVariableID;
	}

	/**
	 * Set the referenced variable.
	 * @param referencedVar
	 */
	public void setReferencedVariable(String referencedVar) {
		referencedVariableID = referencedVar;
		Variable var = (Variable) Services.getService().mapping().getObject(referencedVar);
		setReferencedName(var.getVariableName());
	}

	public String toXML() {
		String str = "<dataobject class=\"variablereference\">" + "<id>"
				+ getUniqueID() + "</id>" + "<type>" + getReferenceType()
				+ "</type>" + "<referencedname>" + getReferencedName()
				+ "</referencedname>";
		return str;
	}

	public String toJavaString() {
		return null;
	}
}
