package usp.ime.line.ivprog.model.domainaction;

import usp.ime.line.ivprog.model.IVPProgram;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainModel;

public class UpdateReferencedVariable extends DomainAction {

    private IVPProgram model;
    private String     lastVarID;
    private String     newVarID;
    private String     referenceID;

    public UpdateReferencedVariable(String name, String description) {
        super(name, description);
    }

    public void setDomainModel(DomainModel m) {
        model = (IVPProgram) m;
    }

    protected void executeAction() {
        lastVarID = model.updateReferencedVariable(referenceID, newVarID, _currentState);
    }

    protected void undoAction() {
        newVarID = model.updateReferencedVariable(referenceID, lastVarID, _currentState);
    }

    public boolean equals(DomainAction a) {
        return false;
    }

    public String getLastVarID() {
        return lastVarID;
    }

    public void setLastVarID(String lastVarID) {
        this.lastVarID = lastVarID;
    }

    public String getNewVarID() {
        return newVarID;
    }

    public void setNewVarID(String newVarID) {
        this.newVarID = newVarID;
    }

    public String getReferenceID() {
        return referenceID;
    }

    public void setReferenceID(String referenceID) {
        this.referenceID = referenceID;
    }

}
