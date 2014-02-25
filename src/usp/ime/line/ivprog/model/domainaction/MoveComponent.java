package usp.ime.line.ivprog.model.domainaction;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.model.IVPProgram;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainModel;

public class MoveComponent extends DomainAction {
    private String component;
    private String destiny;
    private String destinyContext;
    private String originContext;
    private String origin;
    private int    originIndex;
    private int    dropIndex = 0;
    
    public MoveComponent(String name, String description) {
        super(name, description);
    }
    
    public void setDomainModel(DomainModel m) {
    }
    
    protected void executeAction() {
        originIndex = Services.getService().getController().getProgram().moveChild(component, origin, destiny, originContext, destinyContext, dropIndex, _currentState);
        System.out.println("do move: dropAT" + dropIndex + " originIndex" + originIndex);
    }
    
    protected void undoAction() {
        Services.getService().getController().getProgram().moveChild(component, destiny, origin, destinyContext, originContext, originIndex, _currentState);
        System.out.println("undo move: originIndex" + originIndex);
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
    
    public String getDestinyContext() {
        return destinyContext;
    }
    
    public void setDestinyContext(String destinyContext) {
        this.destinyContext = destinyContext;
    }
    
    public String getOriginContext() {
        return originContext;
    }
    
    public void setOriginContext(String originContext) {
        this.originContext = originContext;
    }
}
