package usp.ime.line.ivprog.model.domainaction;

import usp.ime.line.ivprog.model.IVPProgram;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainModel;

public class CreateReference extends DomainAction {

    private IVPProgram model;
    private String     referenceID;

    public CreateReference(String name, String description) {
        super(name, description);
    }

    public void setDomainModel(DomainModel m) {
        model = (IVPProgram) m;
    }

    protected void executeAction() {

    }

    protected void undoAction() {

    }

    public boolean equals(DomainAction a) {
        return false;
    }

}
