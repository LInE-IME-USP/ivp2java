package usp.ime.line.ivprog.model.domainaction;

import usp.ime.line.ivprog.model.IVPProgram;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainModel;

public class ChangeVariableInitValue extends DomainAction {
    private IVPProgram model;
    private String     variableID;
    private String     lastValue;
    private String     newValue;
    
    public ChangeVariableInitValue(String name, String description) {
        super(name, description);
    }
    
    public void setDomainModel(DomainModel m) {
        model = (IVPProgram) m;
    }
    
    protected void executeAction() {
        lastValue = model.changeVariableInitialValue(variableID, newValue, _currentState);
    }
    
    protected void undoAction() {
        model.changeVariableInitialValue(variableID, lastValue, _currentState);
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
    
    public String getLastValue() {
        return lastValue;
    }
    
    public void setLastValue(String lastValue) {
        this.lastValue = lastValue;
    }
    
    public String toString() {
        String str = "";
        str += "<changevariableinitvalue>\n" + "   <variableid>" + variableID + "</variableid>\n" + "   <lastvalue>" + lastValue + "</lastvalue>\n" + "   <newvalue>" + newValue + "</newvalue>\n"
                + "</changevariableinitvalue>\n";
        return str;
    }
}
