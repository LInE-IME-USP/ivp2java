package usp.ime.line.ivprog.model.domainaction;

import usp.ime.line.ivprog.model.IVPProgram;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainModel;

public class CreateChild extends DomainAction {
    private IVPProgram model;
    private String     containerID;
    private String     scopeID;
    private String     objectID;
    private String     context;
    private short      classID;
    private int        index = 0;
    
    public CreateChild(String name, String description) {
        super(name, description);
    }
    
    public void setDomainModel(DomainModel m) {
        model = (IVPProgram) m;
    }
    
    protected void executeAction() {
        if (isRedo()) {
            System.out.println("CreateChild.REDO tentando colocar no index " + index);
            model.restoreChild(containerID, objectID, index, context, _currentState);
        } else {
            System.out.println("CreateChild.DO ");
            objectID = model.newChild(containerID, classID, context, _currentState);
        }
    }
    
    protected void undoAction() {
        index = model.removeChild(containerID, objectID, context, _currentState);
        System.out.println("CreateChild.UNDO > index de onde saiu " + index);
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
    
    public int getIndex() {
        return index;
    }
    
    public void setIndex(int index) {
        this.index = index;
    }
    
    public String toString() {
        String str = "";
        str += "<createchild>\n" + "   <containerid>" + containerID + "</containerid>\n" + "   <scopeid>" + scopeID + "</scopeid>\n" + "   <objectid>" + objectID + "</objectid>\n" + "   <context>"
                + context + "</context>\n" + "   <classid>" + classID + "</classid>\n" + "   <index>" + index + "</index>\n" + "</createchild>\n";
        return str;
    }
}
