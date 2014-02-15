package usp.ime.line.ivprog.model.domainaction;

import java.util.Vector;

import usp.ime.line.ivprog.model.IVPProgram;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainModel;

public class ChangeVariableType extends DomainAction {
    private IVPProgram model;
    private short      lastType;
    private Vector     returnedVector;
    private short      newType;
    private String     variableID;
    
    public ChangeVariableType(String name, String description) {
        super(name, description);
    }
    
    public void setDomainModel(DomainModel m) {
        model = (IVPProgram) m;
    }
    
    protected void executeAction() {
        System.out.println("executou a troca");
        if(isRedo()){
            model.changeVariableType(variableID, newType, _currentState);
        }else{
            returnedVector = model.changeVariableType(variableID, newType, _currentState);
        }
        
    }
    
    protected void undoAction() {
        model.restoreVariableType(variableID, returnedVector, _currentState);
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
