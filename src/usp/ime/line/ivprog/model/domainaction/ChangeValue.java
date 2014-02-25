package usp.ime.line.ivprog.model.domainaction;

import usp.ime.line.ivprog.Services;
import usp.ime.line.ivprog.model.IVPProgram;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.domain.DomainModel;

public class ChangeValue extends DomainAction {
    private String id;
    private String lastValue;
    private String newValue;
    private String context;
    private String holder;
    
    public ChangeValue(String name, String description) {
        super(name, description);
    }
    
    public void setDomainModel(DomainModel m) {
    }
    
    protected void executeAction() {
        lastValue = Services.getService().getController().getProgram().changeValue(id, newValue, _currentState);
    }
    
    protected void undoAction() {
        Services.getService().getController().getProgram().changeValue(id, lastValue, _currentState);
    }
    
    public boolean equals(DomainAction a) {
        return false;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getLastValue() {
        return lastValue;
    }
    
    public void setLastValue(String lastValue) {
        this.lastValue = lastValue;
    }
    
    public String getNewValue() {
        return newValue;
    }
    
    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
    
    public String getContext() {
        return context;
    }
    
    public void setContext(String context) {
        this.context = context;
    }
    
    public String getHolder() {
        return holder;
    }
    
    public void setHolder(String holder) {
        this.holder = holder;
    }
    
}
