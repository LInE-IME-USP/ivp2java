package usp.ime.line.ivprog.model.components.datafactory.dataobjetcs;

import ilm.framework.assignment.model.DomainObject;
import usp.ime.line.ivprog.Services;

public class VariableReference extends Reference {

	private String referencedVariableID = null;
	public static final String STRING_CLASS = "variablereference";

	public VariableReference(String name, String description) {
		super(name, description);
	}
	
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
		if(referencedVar != null){
			Variable var = (Variable) Services.getService().getModelMapping().get(referencedVar);
			setReferencedName(var.getVariableName());
			setReferenceType(var.getVariableType());
		}else{
			setReferencedName(null);
			setReferenceType((short) -1);
		}
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

	public boolean equals(DomainObject o) {
		// TODO Auto-generated method stub
		return false;
	}
}
