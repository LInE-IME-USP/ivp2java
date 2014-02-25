package usp.ime.line.ivprog.model.domainaction;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.model.IVPProgram;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainModel;

public class CreateChild extends DomainAction {
    private String containerID;
    private String scopeID;
    private String objectID;
    private String context;
    private short  classID;
    private int    index = 0;
    
    public CreateChild(String name, String description) {
        super(name, description);
    }
    
    public void setDomainModel(DomainModel m) {
    }
    
    protected void executeAction() {
        if (isRedo()) {
            Services.getService().getController().getProgram().restoreChild(containerID, objectID, index, context, _currentState);
        } else {
            objectID = Services.getService().getController().getProgram().newChild(containerID, classID, context, _currentState);
        }
    }
    
    protected void undoAction() {
        index = Services.getService().getController().getProgram().removeChild(containerID, objectID, context, _currentState);
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
    
    public String getContext() {
        return context;
    }
    
    public void setContext(String context) {
        this.context = context;
    }
    
    public String getScopeID() {
        return scopeID;
    }
    
    public void setScopeID(String scopeID) {
        this.scopeID = scopeID;
    }
}
