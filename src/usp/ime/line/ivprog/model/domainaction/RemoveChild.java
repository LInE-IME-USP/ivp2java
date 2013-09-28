package usp.ime.line.ivprog.model.domainaction;

import usp.ime.line.ivprog.model.IVPProgram;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainModel;

public class RemoveChild extends DomainAction {
	
	private IVPProgram model;
	private String containerID;
	private String childID;
	private int index;

	public RemoveChild(String name, String description) {
		super(name, description);
	}

	public void setDomainModel(DomainModel m) {
		model = (IVPProgram) m;
	}

	protected void executeAction() {
		System.out.println("Preparando para remover...");
		index = model.removeChild(containerID, childID);
		System.out.println("Removido do indice "+index);
	}

	protected void undoAction() {
		
	}

	public boolean equals(DomainAction a) {
		return false;
	}

	public String getContainerID() {
		return containerID;
	}

	public void setContainerID(String containerID) {
		this.containerID = containerID;
	}

	public String getChildID() {
		return childID;
	}

	public void setChildID(String childID) {
		this.childID = childID;
	}

}
