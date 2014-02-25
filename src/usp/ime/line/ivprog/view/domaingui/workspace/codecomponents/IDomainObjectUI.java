package usp.ime.line.ivprog.view.domaingui.workspace.codecomponents;

import java.io.Serializable;

public interface IDomainObjectUI extends Serializable {
    public String getModelID();
    
    public String getModelParent();
    
    public String getModelScope();
    
    public void setModelID(String id);
    
    public void setModelParent(String id);
    
    public void setModelScope(String id);
    
    public void setContext(String context);
    
    public String getContext();
    
    public boolean isContentSet();
    
    public void lockDownCode();
}
