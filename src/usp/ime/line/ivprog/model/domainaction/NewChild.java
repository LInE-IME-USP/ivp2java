package usp.ime.line.ivprog.model.domainaction;

import usp.ime.line.ivprog.model.IVPProgram;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainModel;

public class NewChild extends DomainAction {
	
	private IVPProgram model;
	private String containerID;
	private String objectID;
	private short classID;

	public NewChild(String name, String description) {
		super(name, description);
	}

	public void setDomainModel(DomainModel m) {
		model = (IVPProgram) m;
	}

	protected void executeAction() {
		objectID = model.newChild(containerID, classID);
	}

	protected void undoAction() {
		
	}

	public boolean equals(DomainAction a) {
		return false;
	}

	public String getObjectID() {
		return objectID;
	}

	public void setObjectID(String objectID) {
		this.objectID = objectID;
	}

	public short getClassID() {
		return classID;
	}

	public void setClassID(short classID) {
		this.classID = classID;
	}

	public String getContainerID() {
		return containerID;
	}

	public void setContainerID(String containerID) {
		this.containerID = containerID;
	}

}
