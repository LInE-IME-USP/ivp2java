package usp.ime.line.ivprog.model.domainaction;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.model.IVPProgram;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainModel;

public class ChangeVariableInitValue extends DomainAction {
    private String variableID;
    private String lastValue;
    private String newValue;
    
    public ChangeVariableInitValue(String name, String description) {
        super(name, description);
    }
    
    public void setDomainModel(DomainModel m) {
    }
    
    protected void executeAction() {
        lastValue = Services.getService().getController().getProgram().changeVariableInitialValue(variableID, newValue, _currentState);
    }
    
    protected void undoAction() {
        Services.getService().getController().getProgram().changeVariableInitialValue(variableID, lastValue, _currentState);
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
    
    public String getNewValue() {
        return newValue;
    }
    
    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
}
