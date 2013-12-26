package usp.ime.line.ivprog.model.domainaction;

import usp.ime.line.ivprog.model.IVPProgram;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainModel;

public class CreateChild extends DomainAction {
	
	private IVPProgram model;
	private String containerID;
	private String scopeID;
	private String objectID;
	private short classID;
	private int index = 0;

	public CreateChild(String name, String description) {
		super(name, description);
	}

	public void setDomainModel(DomainModel m) {
		model = (IVPProgram) m;
	}

	protected void executeAction() {
		if(isRedo()){
			model.restoreChild(containerID, objectID, index, _currentState);
		}else{
			objectID = model.newChild(containerID, classID, _currentState);
		}
	}

	protected void undoAction() {
		index = model.removeChild(containerID, objectID, _currentState);
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
