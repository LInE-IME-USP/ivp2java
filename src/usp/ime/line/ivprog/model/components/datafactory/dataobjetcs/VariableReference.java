package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import usp.ime.line.ivprog.Services;

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
		Variable var = (Variable) Services.getService().getModelMapping().get(referencedVar);
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
