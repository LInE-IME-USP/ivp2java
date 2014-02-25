package usp.ime.line.ivprog.model.domainaction;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.model.IVPProgram;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainModel;

public class RemoveChild extends DomainAction {
    private String containerID;
    private String childID;
    private int    index;
    private String context;
    
    public RemoveChild(String name, String description) {
        super(name, description);
    }
    
    public void setDomainModel(DomainModel m) {
    }
    
    protected void executeAction() {
        index = Services.getService().getController().getProgram().removeChild(containerID, childID, context, _currentState);
    }
    
    protected void undoAction() {
        Services.getService().getController().getProgram().restoreChild(containerID, childID, index, context, _currentState);
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
    
    public String getContext() {
        return context;
    }
    
    public void setContext(String context) {
        this.context = context;
    }
}
