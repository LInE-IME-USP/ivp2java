package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

public class VariableReference extends Reference {

	private Variable referencedVariable = null;

	/**
	 * Return the referenced variable.
	 * 
	 * @return
	 */
	public Variable getReferencedVariable() {
		return referencedVariable;
	}

	/**
	 * Set the referenced variable.
	 * 
	 * @param referencedVar
	 */
	public void setReferencedVariable(Variable referencedVar) {
		referencedVariable = referencedVar;
		setReferencedName(referencedVariable.getVariableName());
	}

	public String toXML() {
		String str = "<dataobject class=\"variablereference\">" + "<id>"
				+ getUniqueID() + "</id>" + "<type>" + getReferenceType()
				+ "</type>" + "<referencedname>" + getReferencedName()
				+ "</referencedname>";
		return str;
	}

	public String toJavaString() {
		// TODO Auto-generated method stub
		return null;
	}
}
