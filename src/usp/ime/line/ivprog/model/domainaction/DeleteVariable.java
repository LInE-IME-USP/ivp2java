package usp.ime.line.ivprog.model.domainaction;

import usp.ime.line.ivprog.model.IVPProgram;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainModel;

public class DeleteVariable extends DomainAction {
	
	private IVPProgram model;
	private String scopeID;
	private String variableID;

	public DeleteVariable(String name, String description) {
		super(name, description);
	}

	public void setDomainModel(DomainModel m) {
		model = (IVPProgram) m;
	}

	protected void executeAction() {
		model.removeVariable(scopeID, variableID);
	}

	protected void undoAction() {
		model.restoreVariable(scopeID, variableID);
	}

	public boolean equals(DomainAction a) {
		return false;
	}

	public String getScopeID() {
		return scopeID;
	}

	public void setScopeID(String scopeID) {
		this.scopeID = scopeID;
	}

	public String getVariableID() {
		return variableID;
	}

	public void setVariableID(String variableID) {
		this.variableID = variableID;
	}

}
