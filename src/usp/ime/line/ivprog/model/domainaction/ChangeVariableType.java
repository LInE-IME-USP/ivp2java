package usp.ime.line.ivprog.model.domainaction;

import java.util.Vector;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.model.IVPProgram;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainModel;

public class ChangeVariableType extends DomainAction {
    private short  lastType;
    private Vector returnedVector;
    private short  newType;
    private String variableID;
    
    public ChangeVariableType(String name, String description) {
        super(name, description);
    }
    
    public void setDomainModel(DomainModel m) {
    }
    
    protected void executeAction() {
        if (isRedo()) {
            Services.getService().getController().getProgram().changeVariableType(variableID, newType, _currentState);
        } else {
            returnedVector = Services.getService().getController().getProgram().changeVariableType(variableID, newType, _currentState);
        }
    }
    
    protected void undoAction() {
        Services.getService().getController().getProgram().restoreVariableType(variableID, returnedVector, _currentState);
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
    
    public short getNewType() {
        return newType;
    }
    
    public void setNewType(short expressionInteger) {
        this.newType = expressionInteger;
    }
}
