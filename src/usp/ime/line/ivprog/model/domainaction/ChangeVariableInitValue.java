package usp.ime.line.ivprog.model.domainaction;

import usp.ime.line.ivprog.model.IVPProgram;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainModel;

public class ChangeVariableInitValue extends DomainAction {

	private IVPProgram model;
	private Object lastValue;
	private Object newValue;
	
	public ChangeVariableInitValue(String name, String description) {
		super(name, description);
	}

	public void setDomainModel(DomainModel m) {
		
	}

	protected void executeAction() {

	}

	protected void undoAction() {

	}

	public boolean equals(DomainAction a) {
		return false;
	}

}