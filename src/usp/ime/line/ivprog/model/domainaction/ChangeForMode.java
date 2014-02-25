package usp.ime.line.ivprog.model.domainaction;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.model.IVPProgram;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainModel;

public class ChangeForMode extends DomainAction {
    private int    lastMode;
    private int    newMode;
    private String forID;
    
    public ChangeForMode(String name, String description) {
        super(name, description);
    }
    
    public void setDomainModel(DomainModel m) {
    }
    
    protected void executeAction() {
        lastMode = Services.getService().getController().getProgram().changeForMode(newMode, forID, _currentState);
    }
    
    protected void undoAction() {
        Services.getService().getController().getProgram().changeForMode(lastMode, forID, _currentState);
    }
    
    public boolean equals(DomainAction a) {
        return false;
    }
    
    public int getNewMode() {
        return newMode;
    }
    
    public void setNewMode(int newMode) {
        this.newMode = newMode;
    }
    
    public String getForID() {
        return forID;
    }
    
    public void setForID(String forID) {
        this.forID = forID;
    }
    
}
