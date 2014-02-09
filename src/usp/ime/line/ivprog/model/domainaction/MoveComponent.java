package usp.ime.line.ivprog.model.domainaction;

import usp.ime.line.ivprog.model.IVPProgram;
import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainModel;

public class MoveComponent extends DomainAction {

    private IVPProgram model;
    private String     component;
    private String     destiny;
    private String     origin;
    private int        originIndex;
    private int        dropIndex = 0;

    public MoveComponent(String name, String description) {
        super(name, description);
    }

    public void setDomainModel(DomainModel m) {
        model = (IVPProgram) m;
    }

    protected void executeAction() {
        originIndex = model.moveChild(component, origin, destiny, dropIndex, _currentState);
    }

    protected void undoAction() {
        model.moveChild(component, destiny, origin, originIndex, _currentState);
    }

    public boolean equals(DomainAction a) {
        return false;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getDestiny() {
        return destiny;
    }

    public void setDestiny(String destiny) {
        this.destiny = destiny;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public int getDropY() {
        return dropIndex;
    }

    public void setDropY(int dropY) {
        this.dropIndex = dropY;
    }

}
