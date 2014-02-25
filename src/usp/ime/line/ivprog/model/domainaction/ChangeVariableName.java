package usp.ime.line.ivprog.model.domainaction;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.model.IVPProgram;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainModel;

public class ChangeVariableName extends DomainAction {
    private String lastName;
    private String newName;
    private String variableID;
    
    public ChangeVariableName(String name, String description) {
        super(name, description);
    }
    
    public void setDomainModel(DomainModel m) {
    }
    
    protected void executeAction() {
        lastName = Services.getService().getController().getProgram().changeVariableName(variableID, newName, _currentState);
    }
    
    protected void undoAction() {
        Services.getService().getController().getProgram().changeVariableName(variableID, lastName, _currentState);
    }
    
    public boolean equals(DomainAction a) {
        return false;
    }
    
    public String getVariableID() {
        return variableID;
    }
    
    public void setVariableID(String variableID) {
        this.variableID = variableID;
    }
    
    public String getNewName() {
        return newName;
    }
    
    public void setNewName(String newName) {
        this.newName = newName;
    }
}
