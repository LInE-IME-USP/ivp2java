package usp.ime.line.ivprog.model.domainaction;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.model.IVPProgram;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainModel;

public class DeleteVariable extends DomainAction {
    private String scopeID;
    private String variableID;
    private int    index;
    
    public DeleteVariable(String name, String description) {
        super(name, description);
    }
    
    public void setDomainModel(DomainModel m) {
    }
    
    protected void executeAction() {
        Services.getService().getController().getProgram().removeVariable(scopeID, variableID, _currentState);
    }
    
    protected void undoAction() {
        Services.getService().getController().getProgram().restoreVariable(scopeID, variableID, _currentState);
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
